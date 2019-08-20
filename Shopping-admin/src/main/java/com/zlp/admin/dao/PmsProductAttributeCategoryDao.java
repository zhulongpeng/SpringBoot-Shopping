package com.zlp.admin.dao;

import com.zlp.admin.dto.PmsProductAttributeCategoryItem;

import java.util.List;


public interface PmsProductAttributeCategoryDao {

    List<PmsProductAttributeCategoryItem> getListWithAttr();

}
