package com.zlp.admin.service;

import com.zlp.admin.dto.PmsProductAttributeCategoryItem;
import com.zlp.mbg.model.PmsProductAttributeCategory;
import java.util.*;

public interface PmsProductAttributeCategoryService {

    int create(String name);

    int update(Long id, String name);

    int delete(Long id);

    PmsProductAttributeCategory getPmsProductAttributeCategoryById(Long id);

    List<PmsProductAttributeCategory> getPmsProductAttributeCategoryList(Integer pageSize, Integer pageNum);

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
