package com.trangiabao.giaothong.sathach.meo;

public class Meo {

    private String id;
    private String phan;
    private String html;

    public Meo(String id, String phan, String html) {
        this.id = id;
        this.phan = phan;
        this.html = html;
    }

    public String getId() {
        return id;
    }

    public String getPhan() {
        return phan;
    }

    public String getHtml() {
        return html;
    }
}
