package com.example.koiware.salesking;

public class AppCategoryItem {
    String categoryCd;
    String categoryImgUrl;
    String categoryNm;



    public AppCategoryItem(String categoryCd, String categoryImgUrl, String categoryNm) {
        this.categoryCd = categoryCd;
        this.categoryImgUrl =  categoryImgUrl;
        this.categoryNm =  categoryNm;
    }

    public String getCategoryCd() {
        return categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public String getCategoryImgUrl() {
        return categoryImgUrl;
    }

    public void setCategoryImgUrl(String categoryImgUrl) {
        this.categoryImgUrl = categoryImgUrl;
    }

    public String getCategoryNm() {
        return categoryNm;
    }

    public void setCategoryNm(String categoryNm) {
        this.categoryNm = categoryNm;
    }

}
