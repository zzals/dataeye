package kr.co.penta.dataeye.tsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.client.*;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.penta.dataeye.common.entities.meta.PenRelTypeM;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.common.util.TreeBuilder;
import kr.co.penta.dataeye.spring.config.properties.SearchSettings;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.tsearch.service.TSearchService;

import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion.Entry.Option;

@Service
public class TSearchServiceImpl implements TSearchService {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SearchSettings searchSettings;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private MetaDao metaDao;

	private RestClient getRestClient() {
		RestClient restClient = RestClient
				.builder(new HttpHost(searchSettings.getHost(), searchSettings.getPort(), searchSettings.getSchema()))
				.build();
		return restClient;
	}

	@Override
	public Map<String, Object> findDashboardObjTypes() {
		return es_query_dashboasd_objTypes();
	}

	private Map<String, Object> es_query_dashboasd_objTypes() {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("\n");
		sb.append("    \"size\":100,").append("\n");
		sb.append("    \"query\" : {").append("\n");
		sb.append("        \"bool\": {").append("\n");
		sb.append("            \"should\": [").append("\n");
		int boost = searchSettings.getConfig().getDashboardObjTypes().length;
		for (String objTypeId : searchSettings.getConfig().getDashboardObjTypes()) {
			sb.append("                {\"match\" : {\"obj_type_id2\" : {\"query\":\"").append(objTypeId)
					.append("\", \"boost\":").append(boost).append("}}}");
			if (boost > 1) {
				sb.append(",");
			}
			boost--;
		}
		sb.append("            ]").append("\n");
		sb.append("        }").append("\n");
		sb.append("    },").append("\n");
		sb.append("    \"sort\": [").append("\n");
		sb.append("        \"_score\"").append("\n");
		sb.append("     ]").append("\n");
		sb.append("}").append("\n");
		LOG.debug("findDashboardObjTypes request body : {}", sb.toString());

		HttpEntity entity = new NStringEntity(sb.toString(), ContentType.APPLICATION_JSON);

		RestClient restClient = getRestClient();
		Response response;
		Map<String, Object> jsonMap = null;
		String endpoint = searchSettings.getConfig().getIndexName() + "/_search";
		try {
			Request request = new Request("GET", searchSettings.getConfig().getIndexName() + "/_search");
			request.addParameter("pretty", "true");
			request.setEntity(entity);
			response = restClient.performRequest(request);
			String str = EntityUtils.toString(response.getEntity());

			jsonMap = JSONUtils.getInstance().jsonToMap(str);
		} catch (IOException e) {
			LOG.error("es_query_dashboasd_objTypes error : {}", e);
		} finally {
			try {
				restClient.close();
			} catch (IOException e) {
			}
		}

		return jsonMap;
	}

	@Override
	public List<Map<String, Object>> findTreeMapByPath(String objTypeId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("objTypeId", objTypeId).put("metaRelCd", "PT");
		PenRelTypeM penRelTypeM = metaDao.getRelTypeMOne(daoParam);

		daoParam.clear();
		daoParam.put("objTypeId", penRelTypeM.getRelObjTypeId());
		List<Map<String, Object>> pathObjArea = commonDao.selectList("tsearch.findPathObjArea", daoParam);
		TreeBuilder treeBuilder = new TreeBuilder("objId", "pathObjId", pathObjArea);

		List<Map<String, Object>> treeList = treeBuilder.build();
		Map<String, Object> subjectCntMap = es_search_countByPath(objTypeId);
		List<Map<String, Object>> buckets = (List<Map<String, Object>>) ((Map<String, Object>) ((Map<String, Object>) subjectCntMap
				.get("aggregations")).get("doc_aggs")).get("buckets");
		treemapCountMapping(treeList, buckets);

		return treeList;
	}

	private void treemapCountMapping(List<Map<String, Object>> treeList, List<Map<String, Object>> buckets) {
		for (Map<String, Object> bucket : buckets) {
			String key = (String) bucket.get("key");
			Integer doc_count = (Integer) bucket.get("doc_count");

			for (Map<String, Object> map : treeList) {
				String objId = (String) map.get("objId");
				if (key.equals(objId)) {
					map.put("doc_count", doc_count);
					break;
				} else {
					List<Map<String, Object>> children = (List<Map<String, Object>>) map.get("children");
					if (null != children) {
						treemapCountMapping(children, buckets);
					}
				}
			}
		}
	}

	private Map<String, Object> es_search_countByPath(String objTypeId) {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("\n");
		sb.append("	\"size\":0,").append("\n");
		sb.append("	\"aggs\": {").append("\n");
		sb.append("    	\"doc_aggs\": {").append("\n");
		sb.append("    		\"terms\": {").append("\n");
		sb.append("    			\"field\": \"path_obj_id\"").append("\n");
		sb.append("    		}").append("\n");
		sb.append("    	}").append("\n");
		sb.append("	}").append("\n");
		sb.append("}").append("\n");
		LOG.debug("findReportCountBySubject request body : {}", sb.toString());

		HttpEntity entity = new NStringEntity(sb.toString(), ContentType.APPLICATION_JSON);

		RestClient restClient = getRestClient();
		Response response;
		Map<String, Object> jsonMap = null;
		String endpoint = searchSettings.getConfig().getIndexName() + "/" + objTypeId + "/_search";
		try {
			Request request = new Request("GET", searchSettings.getConfig().getIndexName() + "/_search");
			request.addParameter("pretty", "true");
			request.setEntity(entity);
			response = restClient.performRequest(request);
			String str = EntityUtils.toString(response.getEntity());

			jsonMap = JSONUtils.getInstance().jsonToMap(str);
		} catch (IOException e) {
			LOG.error("es_search_countByPath error : {}", e);
		} finally {
			try {
				restClient.close();
			} catch (IOException e) {
			}
		}
		return jsonMap;
	}

	@Override
	public List<Map<String, Object>> getTabCategory() {
		String[] objTypeIds = searchSettings.getConfig().getIndexesObjTypeIds();
		String[] objTypeNms = searchSettings.getConfig().getIndexesObjTypeNms();

		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < objTypeIds.length; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("objTypeId", objTypeIds[i]);
			map.put("objTypeNm", objTypeNms[i]);

			list.add(map);
		}
		return list;
	}

	@Override
	public Map<String, Object> suggest(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> msearch(String type, String keyword, Integer page) {
		return null;
	}

	@Override
	public Map<String, Object> elsearch(String type, String keyword, Integer page) {
		System.out.println("Elastic Search Keyword :: " + keyword);
		int tot_cnt = 0;
		int size = searchSettings.getConfig().getDefaultSearchSize();
		int from = page * size - size;
		if ("ALL".equals(type)) {
			size = searchSettings.getConfig().getAllSearchSize();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		try (RestHighLevelClient restClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost(searchSettings.getHost(), searchSettings.getPort(), "http")));) {
			
			SearchRequest searchReq = new SearchRequest("dataeye_dataset");
			SearchTemplateRequest request = new SearchTemplateRequest();
			String[] objTypeIds = searchSettings.getConfig().getIndexesObjTypeIds();
			for(String objTypeId : objTypeIds) {
				List<HashMap> resList = new ArrayList<>();
				if(type.equals("ALL") || objTypeId.equals(type)) {
					String filterStr = ",\"filter\": [";
					filterStr += "{ \"terms\" : {\"obj_type_id\": [\"" + objTypeId + "\"]}}";
					filterStr += "]";
					
					request.setRequest(searchReq);
					request.setScriptType(ScriptType.INLINE);
					StringBuilder qstr = new StringBuilder();
					qstr.append("{");
					qstr.append("\"query\":{ ");
					qstr.append("	\"bool\":{ ");
					if (keyword != null && !keyword.trim().equals("") && !keyword.toLowerCase().equals("null")) {
						qstr.append("   	\"must\":{  ");
						qstr.append("        	\"multi_match\":{   ");
						qstr.append("           	\"query\":\"").append(keyword).append("\",   ");
						qstr.append("           \"operator\": \"or\"   ");
						qstr.append("        }  ");
						qstr.append("       }");
					}
					/*
					 * ,"filter": [ { "terms": { "domain_l": ["M01","M17","M12","111M12" ]}}, {
					 * "terms": { "domain_m": ["FI","AC" ]}} ]
					 */
					qstr.append(filterStr);
					qstr.append(" 		} ");
					qstr.append("  } ");
					qstr.append(" ,\"highlight\": { ");
					qstr.append("   \"pre_tags\":[\"<em>\"], ");
					qstr.append("      \"post_tags\":[\"</em>\"], ");
					qstr.append("     \"fields\": [ ");
					qstr.append("       {\"obj_desc\": {}} ");
					qstr.append("      ] " + "   } ");
					qstr.append("   ,\"from\":").append(from);
					qstr.append("   ,\"size\":").append(size);
					qstr.append("}");
					
					request.setScript(qstr.toString());
					Map<String, Object> scriptParams = new HashMap<>();
					request.setScriptParams(scriptParams);
					SearchTemplateResponse response = restClient.searchTemplate(request, RequestOptions.DEFAULT);
					SearchResponse searchResponse = response.getResponse();
					LOG.debug(searchResponse.toString());
		
					// 결과 리스트만 뽑을 경우에..
					TotalHits totalHit = searchResponse.getHits().getTotalHits();
					SearchHit[] searchHits = searchResponse.getHits().getHits();
					if (true) {
						for (int i = 0; i < searchHits.length; i++) {
							// Map<String, Object> map =
							// objMapper.readValue(searchHits[i].getSourceAsString(), Map.class);
							Map<String, Object> map = searchHits[i].getSourceAsMap();
							Map<String, HighlightField> hmap = searchHits[i].getHighlightFields();
							if (hmap.get("obj_desc") != null) {
								Text[] dtxt = hmap.get("obj_desc").fragments();
								map.put("obj_desc", dtxt[0].toString());
							}
							resList.add((HashMap) map);
						}
					}
		
					// 검색 결과 메타 정보까지 전부
					ObjectMapper objMapper = new ObjectMapper();
					// result = objMapper.readValue(searchResponse.toString(), Map.class);
		
					tot_cnt += totalHit.value;
					result.put(objTypeId+"_tot_cnt", totalHit.value);
					result.put(objTypeId, resList);
				}
			}
			result.put("obj_ids", objTypeIds);
			
			TSearchMethod.updateKeyword(keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		result.put("tot_cnt", tot_cnt);
		
		Map<String, Object> pagingInfo = new HashMap<>();
		pagingInfo.put("allSize", size);
		pagingInfo.put("defaultSize", size);
		pagingInfo.put("type", type);
		pagingInfo.put("keyword", keyword);
		pagingInfo.put("page", page);

		result.put("pagination", pagingInfo);

		return result;
	}
	
	@Override
	public List<String> elsuggest(String keyword) {
		System.out.println("elsearch completion start : " + keyword);
		List<String> resList = new ArrayList<>();
		List<Option> entries = TSearchMethod.getSuggest(keyword, 10);
		// 검색어 자동완성
		if (entries.size() > 0) {
			for (CompletionSuggestion.Entry.Option option : entries) {
				String suggestText = option.getText().string();
				resList.add(suggestText);
			}
		} else {
			// 자동완성 결과가 없을 경우 오타 검색 실행.
			/*
			String qstr_trem = "{" + "    \"suggest\": { " + "   \"dataset_suggestion\": { " + "    \"text\": \""
					+ keyword + "\", " + "    \"term\": { " + "      \"field\": \"autokeyword.spell\", "
					+ "     \"string_distance\" : \"jaro_winkler\" " + "    } " + "   } " + "  } " + " }";

			request.setScript(qstr_trem);
			request.setScriptParams(scriptParams);
			response = restClient.searchTemplate(request, RequestOptions.DEFAULT);
			searchResponse = response.getResponse();
			Suggest suggest_term = searchResponse.getSuggest();
			TermSuggestion entries_term = suggest_term.getSuggestion("dataset_suggestion");
			for (TermSuggestion.Entry entry : entries_term) {
				for (TermSuggestion.Entry.Option option : entry.getOptions()) {
					String suggestText = option.getText().string();
					resList.add(suggestText);
				}
			}
			*/
		}

		return resList;
	}
	
}