package kr.co.penta.dataeye.tsearch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion.Entry.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import kr.co.penta.dataeye.spring.config.properties.SearchSettings;

@Component
public class TSearchMethod {
	
	private static SearchSettings searchSettings;
	@Autowired(required = true)
	public void setSearchSettings (SearchSettings service){
		searchSettings = service;
	}
	
	public static List<Option> getSuggest(String keyword, int size) {
		CompletionSuggestion entries = null;
		try (RestHighLevelClient restClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost(searchSettings.getHost(), searchSettings.getPort(), "http")));) {
			SearchRequest searchReq = new SearchRequest("dataeye_suggest");
			SearchTemplateRequest request = new SearchTemplateRequest();
			request.setRequest(searchReq);
			request.setScriptType(ScriptType.INLINE);
			StringBuilder qstr = new StringBuilder();
			qstr.append("{");
			qstr.append("\"suggest\":{	");
			qstr.append("	\"dataeye_suggestion\": { ");
			qstr.append("    	\"text\": \"").append(keyword).append("\",");
			qstr.append("    	\"completion\": {  ");
			qstr.append("     		\"field\": \"autokeyword\",");
			qstr.append("     		\"skip_duplicates\": true, ");
			qstr.append("     		\"size\":" + size);
			qstr.append("			} ");
			qstr.append("   	} ");
			qstr.append("  	} ");
			qstr.append("}");
			request.setScript(qstr.toString());
			Map<String, Object> scriptParams = new HashMap<>();
			request.setScriptParams(scriptParams);
			SearchTemplateResponse response = restClient.searchTemplate(request, RequestOptions.DEFAULT);
			SearchResponse searchResponse = response.getResponse();
			Suggest suggest = searchResponse.getSuggest();
			entries = suggest.getSuggestion("dataeye_suggestion");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entries.getOptions();
	}
	
	@Async("threadPoolTaskExecutor")
	public static void updateKeyword(String keyword) throws Exception {
		List<Option> entries = getSuggest(keyword, 1);
		int score = 1;
		// 검색어 자동완성
		try (RestHighLevelClient restClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost(searchSettings.getHost(), searchSettings.getPort(), "http")));) {
			if (entries.size() > 0) {
				String sid = "";
				for (Option option : entries) {
					String suggestText = option.getText().string();
					sid = option.getHit().getId();
					score = (int)option.getScore();
				}
				score++;
				UpdateRequest request = new UpdateRequest("dataeye_suggest", sid);
				String eqstr = "{"+
						"\"autokeyword\":{"+
							"\"input\":\""+keyword+"\","+
							"\"weight\":"+score+
						"}"+
					"}";
				request.doc(eqstr, XContentType.JSON);
				UpdateResponse updateResponse = restClient.update(request, RequestOptions.DEFAULT);
				
			
			} else {
				IndexRequest request = new IndexRequest("dataeye_suggest");
				String eqstr = "{"+
						"\"autokeyword\":{"+
							"\"input\":\""+keyword+"\","+
							"\"weight\":1"+
						"}"+
					"}";
				request.source(eqstr, XContentType.JSON);
				IndexResponse indexResponse = restClient.index(request, RequestOptions.DEFAULT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
