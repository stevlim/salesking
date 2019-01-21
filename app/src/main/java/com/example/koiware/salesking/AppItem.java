package com.example.koiware.salesking;

public class AppItem {
    String seq;
    String appNm;
    String appDesc;
    int downCnt;
    String resId;
    String no;

    public AppItem(String appNm, String appDesc) {
        this.appNm = appNm;
        this.appDesc =  appDesc;
    }

    public AppItem(String appNm, String appDesc, int downCnt, String resId) {
        this.appNm = appNm;
        this.appDesc = appDesc;
        this.downCnt = downCnt;
        this.resId = resId;
    }

    public AppItem(String seq, String appNm, String appDesc, int downCnt, String resId) {
        this.seq = seq;
        this.appNm = appNm;
        this.appDesc = appDesc;
        this.downCnt = downCnt;
        this.resId = resId;
    }

    public AppItem(String seq, String appNm, String appDesc, int downCnt, String resId, String no) {
        this.seq = seq;
        this.appNm = appNm;
        this.appDesc = appDesc;
        this.downCnt = downCnt;
        this.resId = resId;
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }


    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public int getDownCnt() {
        return downCnt;
    }

    public void setDownCnt(int downCnt) {
        this.downCnt = downCnt;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getAppNm() {
        return appNm;
    }

    public void setAppNm(String appNm) {
        this.appNm = appNm;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }
}
