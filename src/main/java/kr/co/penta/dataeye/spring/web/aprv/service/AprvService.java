package kr.co.penta.dataeye.spring.web.aprv.service;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.MetaObjectAprvInfo;

import java.util.List;
import java.util.Map;

public interface AprvService {
    /* 정보 저장 */
    void saveObjInfo(MetaObjectAprvInfo metaObjectAprvInfo, String userId) throws Exception;
    /* 파일 저장 */
    void saveObjFileInfo(MetaObjectAprvInfo metaObjectAprvInfo, String userId) throws Exception;
    /* 결재 요청 정보 */
    CamelMap getAprvMInfo(CamelMap aprvData) throws Exception;
    /* 결재 라인 정보 */
    List<CamelMap> getAprvLineInfo(CamelMap aprvData) throws Exception;
    /* 승인 정보 */
    CamelMap getAprvDoInfo(CamelMap aprvData) throws Exception;
    /* 결재 정보 */
    CamelMap getAprvStsInfo(CamelMap aprvData) throws Exception;
    /* 파일 정보 */
    List<CamelMap> getAprvFileInfo(CamelMap aprvData) throws Exception;
    /* 요청 */
    CamelMap reqAprv(CamelMap aprvData) throws Exception;
    /* 반려 */
    CamelMap rtyAprv(CamelMap aprvData) throws Exception;
    /* 승인 */
    CamelMap doAprv(CamelMap aprvData) throws Exception;
    /* 파일 삭제 */
    void removePenAprvF(CamelMap aprvData) throws Exception;
}