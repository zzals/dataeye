package kr.co.penta.dataeye.spring.web.aprv.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import kr.co.lgup.bdp.common.model.UserVo;
import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.security.core.User;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvCodeType;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvResultType;
import kr.co.penta.dataeye.spring.web.aprv.service.AprvService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/aprv", method={RequestMethod.POST, RequestMethod.GET})
public class AprvController {
    private static final Logger LOG = LoggerFactory.getLogger(AprvController.class);

    private static final String ACT00_0 = AprvCodeType.ACT00_0.getId();
    private static final String ACT01_0 = AprvCodeType.ACT01_0.getId();
    private static final String ACT01_1 = AprvCodeType.ACT01_1.getId();
    private static final String ACT04_0 = AprvCodeType.ACT04_0.getId();
    private static final String ACT04_1 = AprvCodeType.ACT04_1.getId();
    private static final String ACT04_2 = AprvCodeType.ACT04_2.getId();
    private static final String ACT05_0 = AprvCodeType.ACT05_0.getId();

    @Value("${dataeye.attach-file.upload-temp-path}")
    private String uploadTempPath;

    @Value("${dataeye.attach-file.upload-read-path}")
    private String uploadRealPath;

    @Autowired
    private AprvService aprvService;

    @RequestMapping("/reqCommonPop")
    public String reqCommonPop(final Model model, @RequestParam Map<String, Object> paramMap) throws Exception {
        //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = userVo.getUserId();
        final String userNm = userVo.getUserNm();
        final String dpUserNm = userVo.getDpUserNm();

        final String aprvId = StringUtils.defaultString((String) paramMap.get("aprvId"));
        final String menuId = StringUtils.defaultString((String) paramMap.get("menuId"));

        CamelMap aprvData = new CamelMap();
        aprvData.put("aprvId", aprvId);
        aprvData.put("userId", userId);

        CamelMap aprvInfo = aprvService.getAprvMInfo(aprvData);

        String aprvTypeCd = "";
        if (aprvInfo != null) {
            aprvTypeCd = aprvInfo.getString("aprvTypeCd");
        }

        String lastStusCd = aprvInfo.getString("lastStusCd");

        aprvData.put("aprvTypeCd", aprvTypeCd);

        List<CamelMap> aprvLineInfo = aprvService.getAprvLineInfo(aprvData);

        String retUrl = "/aprv/reqCommonPop";

        if (ACT04_1.equals(lastStusCd)) {
            retUrl = "/aprv/rtyCommonPop";

            CamelMap aprvStsInfo = aprvService.getAprvStsInfo(aprvData);

            String aprvStsInfoJsonString = "";

            List<CamelMap> aprvDataInfo = (List<CamelMap>) aprvStsInfo.get("aprvData");
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                aprvStsInfoJsonString = gson.toJson(aprvDataInfo);
            }
            catch (Exception e) {
                LOG.info(e.getMessage());
            }

            model.addAttribute("aprvData", aprvStsInfoJsonString);
        }

        model.addAttribute("userId", userId);
        model.addAttribute("userNm", userNm);
        model.addAttribute("dpUserNm", dpUserNm);

        model.addAttribute("menuId", menuId);

        String aprvInfoJsonString = "";
        String aprvLineInfoJsonString = "";

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            aprvInfoJsonString = gson.toJson(aprvInfo);
            aprvLineInfoJsonString = gson.toJson(aprvLineInfo);
        }
        catch (Exception e) {
            LOG.info(e.getMessage());
        }

        model.addAttribute("aprvInfo", aprvInfoJsonString);
        model.addAttribute("aprvLineInfo", aprvLineInfoJsonString);

        return retUrl;
    }

    @RequestMapping("/doCommonPop")
    public String doCommonPop(Model model, @RequestParam Map<String, Object> paramMap) throws Exception {
        //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = userVo.getUserId();
        final String userNm = userVo.getUserNm();
        final String dpUserNm = userVo.getDpUserNm();

        final String aprvId = StringUtils.defaultString((String) paramMap.get("aprvId"));
        final String aprvDetlId = StringUtils.defaultString((String) paramMap.get("aprvDetlId"));
        final String menuId = StringUtils.defaultString((String) paramMap.get("menuId"));

        CamelMap aprvData = new CamelMap();
        aprvData.put("aprvId", aprvId);
        aprvData.put("aprvDetlId", aprvDetlId);
        aprvData.put("userId", userId);

        CamelMap aprvDoInfo = aprvService.getAprvDoInfo(aprvData);

        model.addAttribute("userId", userId);
        model.addAttribute("userNm", userNm);
        model.addAttribute("dpUserNm", dpUserNm);

        model.addAttribute("aprvId", aprvId);
        model.addAttribute("aprvDetlId", aprvDetlId);
        model.addAttribute("menuId", menuId);

        String aprvDoInfoJsonString = "";

        List<CamelMap> aprvDataInfo = (List<CamelMap>) aprvDoInfo.get("aprvData");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            aprvDoInfoJsonString = gson.toJson(aprvDataInfo);
        }
        catch (Exception e) {
            LOG.info(e.getMessage());
        }

        model.addAttribute("rstCd", aprvDoInfo.getString("rstCd"));
        model.addAttribute("rstMsg", aprvDoInfo.getString("rstMsg"));
        model.addAttribute("aprvGubun", aprvDoInfo.getString("aprvGubun"));
        model.addAttribute("sjtReqDoAprvTypeCd", aprvDoInfo.getString("sjtReqDoAprvTypeCd"));
        model.addAttribute("aprvData", aprvDoInfoJsonString);
        model.addAttribute("aprCd", ACT04_0);
        model.addAttribute("recCd", ACT04_1);
        model.addAttribute("retCd", ACT04_2);
        model.addAttribute("chkCd", ACT05_0);

        return "/aprv/doCommonPop";
    }

    @RequestMapping("/stsCommonPop")
    public String stsCommonPop(Model model, @RequestParam Map<String, Object> paramMap) throws Exception {
        //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = userVo.getUserId();
        final String userNm = userVo.getUserNm();
        final String dpUserNm = userVo.getDpUserNm();

        final String aprvId = StringUtils.defaultString((String) paramMap.get("aprvId"));
        final String menuId = StringUtils.defaultString((String) paramMap.get("menuId"));

        CamelMap aprvData = new CamelMap();
        aprvData.put("aprvId", aprvId);

        CamelMap aprvStsInfo = aprvService.getAprvStsInfo(aprvData);

        model.addAttribute("userId", userId);
        model.addAttribute("userNm", userNm);
        model.addAttribute("dpUserNm", dpUserNm);

        model.addAttribute("aprvId", aprvId);
        model.addAttribute("menuId", menuId);

        String aprvStsInfoJsonString = "";

        List<CamelMap> aprvDataInfo = (List<CamelMap>) aprvStsInfo.get("aprvData");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            aprvStsInfoJsonString = gson.toJson(aprvDataInfo);
        }
        catch (Exception e) {
            LOG.info(e.getMessage());
        }

        model.addAttribute("rstCd", aprvStsInfo.getString("rstCd"));
        model.addAttribute("rstMsg", aprvStsInfo.getString("rstMsg"));
        model.addAttribute("aprvGubun", aprvStsInfo.getString("aprvGubun"));
        model.addAttribute("aprvData", aprvStsInfoJsonString);
        model.addAttribute("aprCd", ACT04_0);
        model.addAttribute("retCd", ACT04_2);

        return "/aprv/stsCommonPop";
    }

    @RequestMapping("/reqAprv")
    @ResponseBody
    public Map<String, Object> reqAprv(@RequestBody Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("rstCd", "FAIL");

        try {
            if (paramMap.get("aprvInfo") == null || paramMap.get("aprvData") == null) {
                resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG_001.getName());
                return resultMap;
            }

            //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final String userId = userVo.getUserId();

            Map<String, Object> aprvInfoMap = (Map<String, Object>) paramMap.get("aprvInfo");

            final String aprvId = StringUtils.defaultString((String) aprvInfoMap.get("APRV_ID"));
            final String aprvDesc = StringUtils.defaultString((String) paramMap.get("aprvDesc"));

            CamelMap aprvData = new CamelMap();
            aprvData.put("aprvId", aprvId);
            aprvData.put("aprvDataList", paramMap.get("aprvData"));
            aprvData.put("aprvFileDataList", paramMap.get("aprvFileData"));
            aprvData.put("aprvDesc", aprvDesc);
            aprvData.put("userId", userId);

            resultMap = aprvService.reqAprv(aprvData);
        }
        catch (Exception e) {
            resultMap.put("rstCd", "FAIL");
            resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG.getName());
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        return resultMap;
    }

    @RequestMapping("/rtyAprv")
    @ResponseBody
    public Map<String, Object> rtyAprv(@RequestBody Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("rstCd", "FAIL");

        try {
            if (paramMap.get("aprvInfo") == null) {
                resultMap.put("rstMsg", AprvResultType.RTY_ERR_MSG_001.getName());
                return resultMap;
            }

            //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final String userId = userVo.getUserId();

            Map<String, Object> aprvInfoMap = (Map<String, Object>) paramMap.get("aprvInfo");

            final String aprvId = StringUtils.defaultString((String) aprvInfoMap.get("APRV_ID"));
            final String aprvDesc = StringUtils.defaultString((String) paramMap.get("aprvDesc"));

            CamelMap aprvData = new CamelMap();
            aprvData.put("aprvId", aprvId);
            aprvData.put("aprvFileDataList", paramMap.get("aprvFileData"));
            aprvData.put("aprvDesc", aprvDesc);
            aprvData.put("userId", userId);

            resultMap = aprvService.rtyAprv(aprvData);
        }
        catch (Exception e) {
            resultMap.put("rstCd", "FAIL");
            resultMap.put("rstMsg", AprvResultType.RTY_ERR_MSG.getName());
            LOG.info(e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping("/doAprv")
    @ResponseBody
    public Map<String, Object> doAprv(@RequestBody Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("rstCd", "FAIL");

        try {
            if (paramMap.get("aprvId") == null || paramMap.get("aprvDetlId") == null || paramMap.get("aprvRsltCd") == null) {
                resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_003.getName());
                return resultMap;
            }

            //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final String userId = userVo.getUserId();

            final String aprvId = StringUtils.defaultString((String) paramMap.get("aprvId"));
            final String aprvDetlId = StringUtils.defaultString((String) paramMap.get("aprvDetlId"));
            final String aprvRsltCd = StringUtils.defaultString((String) paramMap.get("aprvRsltCd"));
            final String aprvDesc = StringUtils.defaultString((String) paramMap.get("aprvDesc"));

            CamelMap aprvData = new CamelMap();
            aprvData.put("aprvId", aprvId);
            aprvData.put("aprvDetlId", aprvDetlId);
            aprvData.put("aprvRsltCd", aprvRsltCd);
            aprvData.put("aprvFileDataList", paramMap.get("aprvFileData"));
            aprvData.put("aprvDesc", aprvDesc);
            aprvData.put("userId", userId);

            resultMap = aprvService.doAprv(aprvData);
        }
        catch (Exception e) {
            resultMap.put("rstCd", "FAIL");
            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG.getName());
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        return resultMap;
    }

    @RequestMapping("/getAprvFileInfo")
    @ResponseBody
    public Map<String, Object> getAprvFileInfo(@RequestBody Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        final String aprvId = StringUtils.defaultString((String) paramMap.get("aprvId"));

        CamelMap aprvData = new CamelMap();
        aprvData.put("aprvId", aprvId);

        List<CamelMap> resultList = aprvService.getAprvFileInfo(aprvData);

        resultMap.put("rows", resultList);

        return resultMap;
    }

    @RequestMapping("/removeFile")
    @ResponseBody
    public Map<String, Object> removeFile(@RequestBody Map<String, Object> reqParam) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        final String tempFilePath = uploadTempPath;
        final String realFilePath = uploadRealPath + "/aprv";

        //UserVo userVo = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userVo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = userVo.getUserId();

        List<Map<String, Object>> rstList = (List<Map<String, Object>>) reqParam.get("reqData");

        for (Map<String, Object> rstMap : rstList) {
            String path = StringUtils.defaultString((String) reqParam.get("apndFilePathNm"));
            String fpath = path + File.separatorChar + rstMap.get("apndFileNm");

            if (path.startsWith(tempFilePath)) {
                try {
                    rmFile(fpath);
                }
                catch (Exception e) {
                    LOG.info(e.getMessage());
                }
            }
            else if (path.startsWith(realFilePath)) {
                String aprvId = StringUtils.defaultString((String) rstMap.get("aprvId"));
                String aprvDetlId = StringUtils.defaultString((String) rstMap.get("apndFileNo"));
                int fileNo = Integer.parseInt((String) rstMap.get("apndFileSrnno"));

                CamelMap aprvData = new CamelMap();
                aprvData.put("aprvId", aprvId);
                aprvData.put("aprvDetlId", aprvDetlId);
                aprvData.put("fileNo", fileNo);
                aprvData.put("userId", userId);

                aprvService.removePenAprvF(aprvData);

                try {
                    rmFile(fpath);
                }
                catch (Exception e) {
                    LOG.info(e.getMessage());
                }
            }
        }

        resultMap.put("rstCd", "SUCC");
        return resultMap;
    }

    private void rmFile(String fpath) throws Exception {
        File f = new File(fpath);

        if (f.isFile()) {
            FileUtils.deleteQuietly(f);
        }
    }
}