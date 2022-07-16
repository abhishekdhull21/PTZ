package com.aimers.zone.Modals;

public class TransactionModal {
    private String type;
    private String rs;
    private String remarks;
    private String sn;
    private String status;

    public TransactionModal(String sn,String type, String rs,String status, String remarks) {
        this.sn = sn;
        this.type = type;
        this.rs = rs;
        this.remarks = remarks;
        this.status = status;
    }
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
