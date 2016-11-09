package com.trangiabao.giaothong.sathach.cauhoi.model;

public class CauTraLoi {

    private int id;
    private String cauTraLoi;
    private boolean dapAn;
    private boolean checked;

    public CauTraLoi(int id, String cauTraLoi, boolean dapAn) {
        this.id = id;
        this.cauTraLoi = cauTraLoi;
        this.dapAn = dapAn;
        this.checked = false;
    }

    public int getId() {
        return id;
    }

    public String getCauTraLoi() {
        return cauTraLoi;
    }

    public boolean isDapAn() {
        return dapAn;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
