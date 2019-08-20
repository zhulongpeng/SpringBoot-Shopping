package com.zlp.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zlp.admin.dao.PmsProductAttributeCategoryDao;
import com.zlp.admin.dto.PmsProductAttributeCategoryItem;
import com.zlp.admin.service.PmsProductAttributeCategoryService;
import com.zlp.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.zlp.mbg.model.PmsProductAttributeCategory;
import com.zlp.mbg.model.PmsProductAttributeCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;

    @Autowired
    private PmsProductAttributeCategoryDao productAttributeCategoryDao;

    @Override
    public int create(String name) {
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setName(name);
        return pmsProductAttributeCategoryMapper.insertSelective(pmsProductAttributeCategory);
    }

    @Override
    public int update(Long id, String name) {
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setId(id);
        pmsProductAttributeCategory.setName(name);
        return pmsProductAttributeCategoryMapper.updateByPrimaryKeySelective(pmsProductAttributeCategory);
    }

    @Override
    public int delete(Long id) {
        return pmsProductAttributeCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductAttributeCategory getPmsProductAttributeCategoryById(Long id) {
        return pmsProductAttributeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProductAttributeCategory> getPmsProductAttributeCategoryList(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return pmsProductAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        return productAttributeCategoryDao.getListWithAttr();
    }
}
