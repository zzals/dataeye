package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MetaObjectAprvInfo extends CamelMap implements Serializable {
    private static final long serialVersionUID = 1829459157018727181L;

    public MetaObjectAprvInfo() {
        put("penAprvM", new PenAprvM());
        put("penAprvDs", new ArrayList<PenAprvD>());
        put("penAprvFs", new ArrayList<PenAprvF>());
    }

    public PenAprvM getPenAprvM() {
        return (PenAprvM) get("penAprvM");
    }

    public void setPenAprvM(PenAprvM penAprvM) {
        put("penAprvM", penAprvM);
    }

    public List<PenAprvD> getPenAprvDs() {
        return (List<PenAprvD>) get("penAprvDs");
    }

    public void setPenAprvDs(List<PenAprvD> penAprvDs) {
        put("penAprvDs", penAprvDs);
    }

    public void add(PenAprvD penAprvD) {
        getPenAprvDs().add(penAprvD);
    }

    public List<PenAprvF> getPenAprvFs() {
        return (List<PenAprvF>) get("penAprvFs");
    }

    public void setPenAprvFs(List<PenAprvF> penAprvFs) {
        put("penAprvFs", penAprvFs);
    }

    public void addFile(PenAprvF penAprvF) {
        getPenAprvFs().add(penAprvF);
    }
}
