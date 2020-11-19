(function () {
    'use strict';

    (function (root, factory) {
        if ((typeof define === 'function') && define.amd) {
            return define(['jquery'], factory);
        } else {
            return factory(root.DE, root.jQuery);
        }
    })(window, function (DE, $) {

        DE = DE || {};
        DE.schema = {
    		PenObjM : function(){
    	        return {
    	            OBJ_TYPE_ID:"",
    	            OBJ_ID:"",
    	            DEL_YN:"",
    	            ADM_OBJ_ID:"",
    	            OBJ_NM:"",
    	            OBJ_ABBR_NM:"",
    	            OBJ_DESC:"",
    	            PATH_OBJ_TYPE_ID:"",
    	            PATH_OBJ_ID:"",
    	            setObjTypeId: function(objTypeId) {
    	                if (objTypeId == undefined) {
    	                    this.OBJ_TYPE_ID = "";
    	                } else {
    	                    this.OBJ_TYPE_ID = objTypeId;
    	                }
    	            },
    	            setObjId: function(objId) {
    	                if (objId == undefined || objId == "자동생성") {
    	                    this.OBJ_ID = "";
    	                } else {
    	                    this.OBJ_ID = objId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setAdmObjId: function(admObjId) {
    	                if (admObjId == undefined) {
    	                    this.ADM_OBJ_ID = "";
    	                } else {
    	                    this.ADM_OBJ_ID = admObjId;
    	                }
    	            },
    	            setObjNm: function(objNm) {
    	                if (objNm == undefined) {
    	                    this.OBJ_NM = "";
    	                } else {
    	                    this.OBJ_NM = objNm;
    	                }
    	            },
    	            setObjAbbrNm: function(objAbbrNm) {
    	                if (objAbbrNm == undefined) {
    	                    this.OBJ_ABBR_NM = "";
    	                } else {
    	                    this.OBJ_ABBR_NM = objAbbrNm;
    	                }
    	            },
    	            setObjDesc: function(objDesc) {
    	                if (objDesc == undefined) {
    	                    this.OBJ_DESC = "";
    	                } else {
    	                    this.OBJ_DESC = objDesc;
    	                }
    	            },
    	            setPathObjTypeId: function(pathObjTypeId) {
    	                if (pathObjTypeId == undefined) {
    	                    this.PATH_OBJ_TYPE_ID = "";
    	                } else {
    	                    this.PATH_OBJ_TYPE_ID = pathObjTypeId;
    	                }
    	            },
    	            setPathObjId: function(pathObjId) {
    	                if (pathObjId == undefined) {
    	                    this.PATH_OBJ_ID = "";
    	                } else {
    	                    this.PATH_OBJ_ID = pathObjId;
    	                }
    	            }
    	        };
    	    },
    	    PenObjD : function() {
    	        return {
    	            OBJ_TYPE_ID:"",
    	            OBJ_ID:"",
    	            ATR_ID_SEQ:"",
    	            ATR_VAL_SEQ:"",
    	            DEL_YN:"",
    	            OBJ_ATR_VAL:"",
    	            UI_COMP_CD:"",
    	            CNCT_ATR_YN:"",
    	            setObjTypeId: function(objTypeId) {
    	                if (objTypeId == undefined) {
    	                    this.OBJ_TYPE_ID = "";
    	                } else {
    	                    this.OBJ_TYPE_ID = objTypeId;
    	                }
    	            },
    	            setObjId: function(objId) {
    	                if (objId == undefined || objId == "자동생성") {
    	                    this.OBJ_ID = "";
    	                } else {
    	                    this.OBJ_ID = objId;
    	                }
    	            },
    	            setAtrIdSeq: function(atrIdSeq) {
    	                if (atrIdSeq == undefined) {
    	                    this.ATR_ID_SEQ = "";
    	                } else {
    	                    this.ATR_ID_SEQ = atrIdSeq;
    	                }
    	            },
    	            setAtrValSeq: function(atrValSeq) {
    	                if (atrValSeq == undefined) {
    	                    this.ATR_VAL_SEQ = "";
    	                } else {
    	                    this.ATR_VAL_SEQ = atrValSeq;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setObjAtrVal: function(objAtrVal) {
    	                if (objAtrVal == undefined) {
    	                    this.OBJ_ATR_VAL = "";
    	                } else {
    	                    this.OBJ_ATR_VAL = objAtrVal;
    	                }
    	            },
    	            setUiCompCd: function(uiCompCd) {
    	                if (uiCompCd == undefined) {
    	                    this.UI_COMP_CD = "";
    	                } else {
    	                    this.UI_COMP_CD = uiCompCd;
    	                }
    	            },
    	            setCnctAtrYn: function(cnctAtrYn) {
    	                if (cnctAtrYn == undefined) {
    	                    this.CNCT_ATR_YN = "";
    	                } else {
    	                    this.CNCT_ATR_YN = cnctAtrYn;
    	                }
    	            },
    	        };
    	    },
    	    PenObjR : function() {
    	        return {
    	            OBJ_TYPE_ID:"",
    	            OBJ_ID:"",
    	            REL_OBJ_TYPE_ID:"",
    	            REL_OBJ_ID:"",
    	            DEL_YN:"",
    	            CRET_DT:"",
    	            CRETR_ID:"",
    	            CHG_DT:"",
    	            CHGR_ID:"",
    	            REL_TYPE_ID:"",
    	            OBJ_REL_ANALS_CD:"",
    	            setObjTypeId: function(objTypeId) {
    	                if (objTypeId == undefined) {
    	                    this.OBJ_TYPE_ID = "";
    	                } else {
    	                    this.OBJ_TYPE_ID = objTypeId;
    	                }
    	            },
    	            setObjId: function(objId) {
    	                if (objId == undefined || objId == "자동생성") {
    	                    this.OBJ_ID = "";
    	                } else {
    	                    this.OBJ_ID = objId;
    	                }
    	            },
    	            setRelObjTypeId: function(relObjTypeId) {
    	                if (relObjTypeId == undefined) {
    	                    this.REL_OBJ_TYPE_ID = "";
    	                } else {
    	                    this.REL_OBJ_TYPE_ID = relObjTypeId;
    	                }
    	            },
    	            setRelObjId: function(relObjId) {
    	                if (relObjId == undefined || relObjId == "자동생성") {
    	                    this.REL_OBJ_ID = "";
    	                } else {
    	                    this.REL_OBJ_ID = relObjId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = "";
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = "";
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = "";
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = "";
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setRelTypeId: function(relTypeId) {
    	                if (relTypeId == undefined) {
    	                    this.REL_TYPE_ID = "";
    	                } else {
    	                    this.REL_TYPE_ID = relTypeId;
    	                }
    	            },
    	            setObjRelAnalsCd: function(objRelAnalsCd) {
    	                if (objRelAnalsCd == undefined) {
    	                    this.OBJ_REL_ANALS_CD = "";
    	                } else {
    	                    this.OBJ_REL_ANALS_CD = objRelAnalsCd;
    	                }
    	            }
    	        };
    	    },
    	    PenCdGrpM : function() {
    	        return {
    	            CD_GRP_ID:null,
    	            DEL_YN:null,
    	            CRET_DT:null,
    	            CRETR_ID:null,
    	            CHG_DT:null,
    	            CHGR_ID:null,
    	            CD_GRP_NM:null,
    	            CD_GRP_DESC:null,
    	            UP_CD_GRP_ID:null,
    	            EFCT_ST_DATE:null,
    	            EFCT_ED_DATE:null,
    	            setCdGrpId: function(cdGrpId) {
    	                if (cdGrpId == undefined) {
    	                    this.CD_GRP_ID = null;
    	                } else {
    	                    this.CD_GRP_ID = cdGrpId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = null;
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = null;
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = null;
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = null;
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = null;
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setCdGrpNm: function(cdGrpNm) {
    	                if (cdGrpNm == undefined) {
    	                    this.CD_GRP_NM = null;
    	                } else {
    	                    this.CD_GRP_NM = cdGrpNm;
    	                }
    	            },
    	            setCdGrpDesc: function(cdGrpDesc) {
    	                if (cdGrpDesc == undefined) {
    	                    this.CD_GRP_DESC = null;
    	                } else {
    	                    this.CD_GRP_DESC = cdGrpDesc;
    	                }
    	            },
    	            setUpCdGrpId: function(upCdGrpId) {
    	                if (upCdGrpId == undefined) {
    	                    this.UP_CD_GRP_ID = null;
    	                } else {
    	                    this.UP_CD_GRP_ID = upCdGrpId;
    	                }
    	            },
    	            setEfctStDate: function(efctStDate) {
    	                if (efctStDate == undefined || efctStDate == "") {
    	                    this.EFCT_ST_DATE = null;
    	                } else {
    	                    this.EFCT_ST_DATE = efctStDate;
    	                }
    	            },
    	            setEfctEdDate: function(efctEdDate) {
    	                if (efctEdDate == undefined || efctEdDate == "") {
    	                    this.EFCT_ED_DATE = null;
    	                } else {
    	                    this.EFCT_ED_DATE = efctEdDate;
    	                }
    	            }
    	        };
    	    },
    	    PenCdM : function() {
    	        return {
    	            CD_GRP_ID:"",
    	            CD_ID:"",
    	            DEL_YN:"",
    	            CRET_DT:"",
    	            CRETR_ID:"",
    	            CHG_DT:"",
    	            CHGR_ID:"",
    	            CD_NM:"",
    	            CD_DESC:"",
    	            SORT_NO:"",
    	            USE_YN:"",
    	            UP_CD_GRP_ID:"",
    	            UP_CD_ID:"",
    	            EFCT_ST_DATE:"",
    	            EFCT_ED_DATE:"",
    	            setCdGrpId: function(cdGrpId) {
    	                if (cdGrpId == undefined) {
    	                    this.CD_GRP_ID = "";
    	                } else {
    	                    this.CD_GRP_ID = cdGrpId;
    	                }
    	            },
    	            setCdId: function(cdId) {
    	                if (cdId == undefined) {
    	                    this.CD_ID = "";
    	                } else {
    	                    this.CD_ID = cdId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = "";
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = "";
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = "";
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = "";
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setCdNm: function(cdNm) {
    	                if (cdNm == undefined) {
    	                    this.CD_NM = "";
    	                } else {
    	                    this.CD_NM = cdNm;
    	                }
    	            },
    	            setCdDesc: function(cdDesc) {
    	                if (cdDesc == undefined) {
    	                    this.CD_DESC = "";
    	                } else {
    	                    this.CD_DESC = cdDesc;
    	                }
    	            },
    	            setSortNo: function(sortNo) {
    	                if (sortNo == undefined) {
    	                    this.SORT_NO = "";
    	                } else {
    	                    this.SORT_NO = sortNo;
    	                }
    	            },
    	            setUseYn: function(useYn) {
    	                if (useYn == undefined) {
    	                    this.USE_YN = "";
    	                } else {
    	                    this.USE_YN = useYn;
    	                }
    	            },
    	            setUpCdGrpId: function(upCdGrpId) {
    	                if (upCdGrpId == undefined) {
    	                    this.UP_CD_GRP_ID = "";
    	                } else {
    	                    this.UP_CD_GRP_ID = upCdGrpId;
    	                }
    	            },
    	            setUpCdId: function(upCdId) {
    	                if (upCdId == undefined) {
    	                    this.UP_CD_ID = "";
    	                } else {
    	                    this.UP_CD_ID = upCdId;
    	                }
    	            },
    	            setEfctStDate: function(efctStDate) {
    	                if (efctStDate == undefined) {
    	                    this.EFCT_ST_DATE = "";
    	                } else {
    	                    this.EFCT_ST_DATE = efctStDate;
    	                }
    	            },
    	            setEfctEdDate: function(efctEdDate) {
    	                if (efctEdDate == undefined) {
    	                    this.EFCT_ED_DATE = "";
    	                } else {
    	                    this.EFCT_ED_DATE = efctEdDate;
    	                }
    	            }
    	        };
    	    },
    	    PenAtrM : function() {
    	        return {
    	            ATR_ID:"",
    	            DEL_YN:"",
    	            CRET_DT:"",
    	            CRETR_ID:"",
    	            CHG_DT:"",
    	            CHGR_ID:"",
    	            ATR_NM:"",
    	            ATR_DESC:"",
    	            DATA_TYPE_CD:"",
    	            COL_LEN:"",
    	            ATR_RFRN_CD:"",
    	            SQL_SBST:"",
    	            UI_COMP_CD:"",
    	            setAtrId: function(atrId) {
    	                if (atrId == undefined) {
    	                    this.ATR_ID = "";
    	                } else {
    	                    this.ATR_ID = atrId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = "";
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = "";
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = "";
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = "";
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setAtrNm: function(atrNm) {
    	                if (atrNm == undefined) {
    	                    this.ATR_NM = "";
    	                } else {
    	                    this.ATR_NM = atrNm;
    	                }
    	            },
    	            setAtrDesc: function(atrDesc) {
    	                if (atrDesc == undefined) {
    	                    this.ATR_DESC = "";
    	                } else {
    	                    this.ATR_DESC = atrDesc;
    	                }
    	            },
    	            setDataTypeCd: function(dataTypeCd) {
    	                if (dataTypeCd == undefined) {
    	                    this.DATA_TYPE_CD = "";
    	                } else {
    	                    this.DATA_TYPE_CD = dataTypeCd;
    	                }
    	            },
    	            setColLen: function(colLen) {
    	                if (colLen == undefined) {
    	                    this.COL_LEN = "";
    	                } else {
    	                    this.COL_LEN = colLen;
    	                }
    	            },
    	            setAtrRfrnCd: function(atrRfrnCd) {
    	                if (atrRfrnCd == undefined) {
    	                    this.ATR_RFRN_CD = "";
    	                } else {
    	                    this.ATR_RFRN_CD = atrRfrnCd;
    	                }
    	            },
    	            setSqlSbst: function(sqlSbst) {
    	                if (sqlSbst == undefined) {
    	                    this.SQL_SBST = "";
    	                } else {
    	                    this.SQL_SBST = sqlSbst;
    	                }
    	            },
    	            setUiCompCd: function(uiCompCd) {
    	                if (uiCompCd == undefined) {
    	                    this.UI_COMP_CD = "";
    	                } else {
    	                    this.UI_COMP_CD = uiCompCd;
    	                }
    	            }
    	        };
    	    },
    	    PenObjTypeM : function() {
    	        return {
    	            OBJ_TYPE_ID:"",
    	            DEL_YN:"",
    	            CRET_DT:"",
    	            CRETR_ID:"",
    	            CHG_DT:"",
    	            CHGR_ID:"",
    	            OBJ_TYPE_NM:"",
    	            OBJ_TYPE_DESC:"",
    	            SORT_NO:"",
    	            UP_OBJ_TYPE_ID:"",
    	            HIER_LEV_NO:"",
    	            LST_LEV_YN:"",
    	            ATR_ADM_CD:"",
    	            setObjTypeId: function(objTypeId) {
    	                if (objTypeId == undefined) {
    	                    this.OBJ_TYPE_ID = "";
    	                } else {
    	                    this.OBJ_TYPE_ID = objTypeId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = "";
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = "";
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = "";
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = "";
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setObjTypeNm: function(objTypeNm) {
    	                if (objTypeNm == undefined) {
    	                    this.OBJ_TYPE_NM = "";
    	                } else {
    	                    this.OBJ_TYPE_NM = objTypeNm;
    	                }
    	            },
    	            setObjTypeDesc: function(objTypeDesc) {
    	                if (objTypeDesc == undefined) {
    	                    this.OBJ_TYPE_DESC = "";
    	                } else {
    	                    this.OBJ_TYPE_DESC = objTypeDesc;
    	                }
    	            },
    	            setSortNo: function(sortNo) {
    	                if (sortNo == undefined) {
    	                    this.SORT_NO = "";
    	                } else {
    	                    this.SORT_NO = sortNo;
    	                }
    	            },
    	            setUpObjTypeId: function(upObjTypeId) {
    	                if (upObjTypeId == undefined) {
    	                    this.UP_OBJ_TYPE_ID = "";
    	                } else {
    	                    this.UP_OBJ_TYPE_ID = upObjTypeId;
    	                }
    	            },
    	            setHierLevNo: function(hierLevNo) {
    	                if (hierLevNo == undefined) {
    	                    this.HIER_LEV_NO = "";
    	                } else {
    	                    this.HIER_LEV_NO = hierLevNo;
    	                }
    	            },
    	            setLstLevYn: function(lstLevYn) {
    	                if (lstLevYn == undefined) {
    	                    this.LST_LEV_YN = "";
    	                } else {
    	                    this.LST_LEV_YN = lstLevYn;
    	                }
    	            },
    	            setAtrAdmCd: function(atrAdmCd) {
    	                if (atrAdmCd == undefined) {
    	                    this.ATR_ADM_CD = "";
    	                } else {
    	                    this.ATR_ADM_CD = atrAdmCd;
    	                }
    	            }
    	        };
    	    },
    	    PenObjTypeAtrD : function() {
    	        return {
    	            META_TYPE_CD:"",
    	            OBJ_TYPE_ID:"",
    	            ATR_ID_SEQ:"",
    	            DEL_YN:"",
    	            CRET_DT:"",
    	            CRETR_ID:"",
    	            CHG_DT:"",
    	            CHGR_ID:"",
    	            ATR_ID:"",
    	            ATR_ALIAS_NM:"",
    	            SORT_NO:"",
    	            MULTI_ATR_YN:"",
    	            CNCT_ATR_YN:"",
    	            MAND_YN:"",
    	            ATR_ADM_TGT_YN:"",
    	            DEGR_NO:"",
    	            INDC_YN:"",
    	            FIND_TGT_NO:"",
    	            AVAIL_CHK_PGM_ID:"",
    	            setMetaTypeCd: function(metaTypeCd) {
    	                if (metaTypeCd == undefined) {
    	                    this.META_TYPE_CD = "";
    	                } else {
    	                    this.META_TYPE_CD = metaTypeCd;
    	                }
    	            },
    	            setObjTypeId: function(objTypeId) {
    	                if (objTypeId == undefined) {
    	                    this.OBJ_TYPE_ID = "";
    	                } else {
    	                    this.OBJ_TYPE_ID = objTypeId;
    	                }
    	            },
    	            setAtrIdSeq: function(atrIdSeq) {
    	                if (atrIdSeq == undefined) {
    	                    this.ATR_ID_SEQ = "";
    	                } else {
    	                    this.ATR_ID_SEQ = atrIdSeq;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = "";
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = "";
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = "";
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = "";
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setAtrId: function(atrId) {
    	                if (atrId == undefined) {
    	                    this.ATR_ID = "";
    	                } else {
    	                    this.ATR_ID = atrId;
    	                }
    	            },
    	            setAtrAliasNm: function(atrAliasNm) {
    	                if (atrAliasNm == undefined) {
    	                    this.ATR_ALIAS_NM = "";
    	                } else {
    	                    this.ATR_ALIAS_NM = atrAliasNm;
    	                }
    	            },
    	            setSortNo: function(sortNo) {
    	                if (sortNo == undefined) {
    	                    this.SORT_NO = "";
    	                } else {
    	                    this.SORT_NO = sortNo;
    	                }
    	            },
    	            setMultiAtrYn: function(multiAtrYn) {
    	                if (multiAtrYn == undefined) {
    	                    this.MULTI_ATR_YN = "";
    	                } else {
    	                    this.MULTI_ATR_YN = multiAtrYn;
    	                }
    	            },
    	            setCnctAtrYn: function(cnctAtrYn) {
    	                if (cnctAtrYn == undefined) {
    	                    this.CNCT_ATR_YN = "";
    	                } else {
    	                    this.CNCT_ATR_YN = cnctAtrYn;
    	                }
    	            },
    	            setMandYn: function(mandYn) {
    	                if (mandYn == undefined) {
    	                    this.MAND_YN = "";
    	                } else {
    	                    this.MAND_YN = mandYn;
    	                }
    	            },
    	            setAtrAdmTgtYn: function(atrAdmTgtYn) {
    	                if (atrAdmTgtYn == undefined) {
    	                    this.ATR_ADM_TGT_YN = "";
    	                } else {
    	                    this.ATR_ADM_TGT_YN = atrAdmTgtYn;
    	                }
    	            },
    	            setDegrNo: function(degrNo) {
    	                if (degrNo == undefined) {
    	                    this.DEGR_NO = "";
    	                } else {
    	                    this.DEGR_NO = degrNo;
    	                }
    	            },
    	            setIndcYn: function(indcYn) {
    	                if (indcYn == undefined) {
    	                    this.INDC_YN = "";
    	                } else {
    	                    this.INDC_YN = indcYn;
    	                }
    	            },
    	            setFindTgtNo: function(findTgtNo) {
    	                if (findTgtNo == undefined) {
    	                    this.FIND_TGT_YN = "";
    	                } else {
    	                    this.FIND_TGT_YN = findTgtNo;
    	                }
    	            },
    	            setAvailChkPgmId: function(availChkPgmId) {
    	                if (availChkPgmId == undefined) {
    	                    this.AVAIL_CHK_PGM_ID = "";
    	                } else {
    	                    this.AVAIL_CHK_PGM_ID = availChkPgmId;
    	                }
    	            }
    	        };
    	    },
    	    PenOrgM : function() {
    	        return {
    	            ORG_ID:"",
    	            DEL_YN:"",
    	            CRET_DT:"",
    	            CRETR_ID:"",
    	            CHG_DT:"",
    	            CHGR_ID:"",
    	            ORG_NM:"",
    	            UP_ORG_ID:"",
    	            SORT_NO:"",
    	            ORG_TYPE_CD:"",
    	            setOrgId: function(orgId) {
    	                if (orgId == undefined) {
    	                    this.ORG_ID = "";
    	                } else {
    	                    this.ORG_ID = orgId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = "";
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = "";
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = "";
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = "";
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = "";
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setOrgNm: function(orgNm) {
    	                if (orgNm == undefined) {
    	                    this.ORG_NM = "";
    	                } else {
    	                    this.ORG_NM = orgNm;
    	                }
    	            },
    	            setUpOrgId: function(upOrgId) {
    	                if (upOrgId == undefined) {
    	                    this.UP_ORG_ID = "";
    	                } else {
    	                    this.UP_ORG_ID = upOrgId;
    	                }
    	            },
    	            setSortNo: function(sortNo) {
    	                if (sortNo == undefined) {
    	                    this.SORT_NO = "";
    	                } else {
    	                    this.SORT_NO = sortNo;
    	                }
    	            },
    	            setOrgTypeCd: function(orgTypeCd) {
    	                if (orgTypeCd == undefined) {
    	                    this.ORG_TYPE_CD = "";
    	                } else {
    	                    this.ORG_TYPE_CD = orgTypeCd;
    	                }
    	            }
    	        };
    	    },
    	    PenUserM : function() {
    	        return {
    	            USER_ID:null,
    	            DEL_YN:null,
    	            CRET_DT:null,
    	            CRETR_ID:null,
    	            CHG_DT:null,
    	            CHGR_ID:null,
    	            USER_NM:null,
    	            USER_PW:null,
    	            ORG_ID:null,
    	            GRAD_CD:null,
    	            MAIL_ID:null,
    	            HP_TEL_NO:null,
    	            setUserId: function(userId) {
    	                if (userId == undefined) {
    	                    this.USER_ID = null;
    	                } else {
    	                    this.USER_ID = userId;
    	                }
    	            },
    	            setDelYn: function(delYn) {
    	                if (delYn == undefined) {
    	                    this.DEL_YN = null;
    	                } else {
    	                    this.DEL_YN = delYn;
    	                }
    	            },
    	            setCretDt: function(cretDt) {
    	                if (cretDt == undefined) {
    	                    this.CRET_DT = null;
    	                } else {
    	                    this.CRET_DT = cretDt;
    	                }
    	            },
    	            setCretrId: function(cretrId) {
    	                if (cretrId == undefined) {
    	                    this.CRETR_ID = null;
    	                } else {
    	                    this.CRETR_ID = cretrId;
    	                }
    	            },
    	            setChgDt: function(chgDt) {
    	                if (chgDt == undefined) {
    	                    this.CHG_DT = null;
    	                } else {
    	                    this.CHG_DT = chgDt;
    	                }
    	            },
    	            setChgrId: function(chgrId) {
    	                if (chgrId == undefined) {
    	                    this.CHGR_ID = null;
    	                } else {
    	                    this.CHGR_ID = chgrId;
    	                }
    	            },
    	            setUserNm: function(userNm) {
    	                if (userNm == undefined) {
    	                    this.USER_NM = null;
    	                } else {
    	                    this.USER_NM = userNm;
    	                }
    	            },
    	            setUserPw: function(userPw) {
    	                if (userPw == undefined) {
    	                    this.USER_PW = null;
    	                } else {
    	                    this.USER_PW = userPw;
    	                }
    	            },
    	            setOrgId: function(orgId) {
    	                if (orgId == undefined) {
    	                    this.ORG_ID = null;
    	                } else {
    	                    this.ORG_ID = orgId;
    	                }
    	            },
    	            setGradCd: function(gradCd) {
    	                if (gradCd == undefined) {
    	                    this.GRAD_CD = null;
    	                } else {
    	                    this.GRAD_CD = gradCd;
    	                }
    	            },
    	            setMailId: function(mailId) {
    	                if (mailId == undefined) {
    	                    this.MAIL_ID = null;
    	                } else {
    	                    this.MAIL_ID = mailId;
    	                }
    	            },
    	            setHpTelNo: function(hpTelNo) {
    	                if (hpTelNo == undefined) {
    	                    this.HP_TEL_NO = null;
    	                } else {
    	                    this.HP_TEL_NO = hpTelNo;
    	                }
    	            }
    	        };
    	    }
        }
    });

}).call(this);