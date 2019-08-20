package com.zlp.admin.controller;


import com.zlp.admin.dto.PmsProductAttributeCategoryItem;
import com.zlp.admin.service.PmsProductAttributeCategoryService;
import com.zlp.common.api.CommonPage;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.PmsProductAttributeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * 商品属性分类
 */
@Api(tags = "PmsProductAttributeCategoryController",description = "商品属性分类管理")
@RestController
@RequestMapping(value = "/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @ApiOperation(value = "添加商品属性分类")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonResult create(
            @RequestParam String name
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsProductAttributeCategoryService.create(name);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }else{
                commonResult = CommonResult.failed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "修改商品属性分类")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonResult update(
            @PathVariable Long id, @RequestParam String name
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsProductAttributeCategoryService.update(id, name);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }else{
                commonResult = CommonResult.failed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "删除单个商品属性分类")
    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    public CommonResult delete(
            @PathVariable Long id
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsProductAttributeCategoryService.delete(id);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }else{
                commonResult = CommonResult.failed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "获取单个商品属性分类信息")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonResult<PmsProductAttributeCategory> getItem(
            @PathVariable Long id
    ){
        CommonResult commonResult = null;
        try {
            commonResult = CommonResult.success(pmsProductAttributeCategoryService.getPmsProductAttributeCategoryById(id));
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "分页获取所有商品属性分类")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<List<PmsProductAttributeCategory>> getList(
            @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum
    ){
        CommonResult commonResult = null;
        try {
            List<PmsProductAttributeCategory> pmsProductAttributeCategoryList = pmsProductAttributeCategoryService.getPmsProductAttributeCategoryList(pageSize, pageNum);
            commonResult = CommonResult.success(CommonPage.restPage(pmsProductAttributeCategoryList));
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "获取所有商品属性及其下属性")
    @GetMapping(value = "/value/withAttr")
    @ResponseBody
    public CommonResult<List<PmsProductAttributeCategoryItem>> getListWithAttr(){
        CommonResult commonResult = null;
        try {
            commonResult = CommonResult.success(pmsProductAttributeCategoryService.getListWithAttr());
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }
}
