package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetaObjectReqInfo extends CamelMap {

    private static final long serialVersionUID = -6455761825850135619L;

    public MetaObjectReqInfo() {
        put("penObjMT", new PenObjMT());
        put("penObjDTs", new ArrayList<PenObjDT>());
    }

    public PenObjMT getPenObjMT() {
        return (PenObjMT) get("penObjMT");
    }

    public void setPenObjMT(PenObjMT penObjMT) {
        put("penObjMT", penObjMT);
    }

    public List<PenObjDT> getPenObjDTs() {
        return (List<PenObjDT>) get("penObjDTs");
    }

    public void setPenObjDTs(List<PenObjDT> penObjDTs) {
        put("penObjDTs", penObjDTs);
    }

    public void setPenObjDTs(PenObjMT penObjMT, List<PenObjDT> penObjDTs) {
        List<PenObjDT> penObjDTs2 = new ArrayList<>();
        /*for (CamelMap map : penObjDTs) {
            penObjDTs2.add(new PenObjDT(penObjMT, map));
        }
        */
        for (Map map : penObjDTs) {
            penObjDTs2.add(new PenObjDT(penObjMT, map));
        }
        setPenObjDTs(penObjDTs2);
    }

    public void add(PenObjDT penObjDT) {
        getPenObjDTs().add(penObjDT);
    }
}
