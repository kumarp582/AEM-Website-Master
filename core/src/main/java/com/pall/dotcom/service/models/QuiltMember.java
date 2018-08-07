package com.pall.dotcom.service.models;

public abstract class QuiltMember {
    
    private boolean isIndustry; 
    
    private boolean isProduct;

    public void setIndustry(boolean isIndustry) {
        this.isIndustry = isIndustry;
        this.isProduct = !isIndustry;
    }

    public void setProduct(boolean isProduct) {
        this.isProduct = isProduct;
        this.isIndustry = !isProduct;
    }

    public boolean isIndustry() {
        return isIndustry;
    }

    public boolean isProduct() {
        return isProduct;
    }

}
