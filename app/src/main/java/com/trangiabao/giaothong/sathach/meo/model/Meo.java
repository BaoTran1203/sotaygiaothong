package com.trangiabao.giaothong.sathach.meo.model;

public class Meo {

    private int id;
    private String phan;
    private String html;

    public Meo(int id, String phan, String html) {
        this.id = id;
        this.phan = phan;
        this.html = html;
    }

    public int getId() {
        return id;
    }

    public String getPhan() {
        return phan;
    }

    public String getHtml() {
        return html;
    }
}
