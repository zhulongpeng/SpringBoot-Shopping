package com.zlp.admin.dto;

import com.zlp.mbg.model.PmsProductAttribute;

import java.util.List;

public class PmsProductAttributeCategoryItem {

    private List<PmsProductAttribute> productAttributeList;

    public List<PmsProductAttribute> getProductAttributeList() {
        return productAttributeList;
    }

    public void setProductAttributeList(List<PmsProductAttribute> productAttributeList) {
        this.productAttributeList = productAttributeList;
    }
}
