package com.zlp.admin.controller;

import com.zlp.admin.dto.PmsBrandParam;
import com.zlp.admin.service.PmsBrandService;
import com.zlp.common.api.CommonPage;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

    @ApiOperation(value = "添加品牌")
    @PostMapping(value = "/create")
    @ResponseBody
//    @PreAuthorize("hasAuthority('pms:brand:create')")
    public CommonResult create(
            @Validated @RequestBody PmsBrandParam pmsBrandParam, BindingResult result
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsBrandService.create(pmsBrandParam);
            if(count == 1){
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

    @ApiOperation("更新品牌")
    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
//    @PreAuthorize("hasAuthority('pms:brand:update')")
    public CommonResult update(
            @PathVariable("id") Long id,
            @Validated @RequestBody PmsBrandParam pmsBrandParam,
            BindingResult result
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsBrandService.updateBrand(id, pmsBrandParam);
            if(count == 1){
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

    @ApiOperation(value = "删除品牌")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult delete(
            @PathVariable("id") Long id
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsBrandService.deleteBrand(id);
            if(count == 1){
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

    @ApiOperation(value = "获取全部品牌")
    @GetMapping(value = "/listAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<List<PmsBrand>> getList(){
        CommonResult commonResult = null;
        try {
            commonResult = CommonResult.success(pmsBrandService.listAllBrand());
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "根据品牌名称分页获取品牌列表")
    @GetMapping(value = "/list")
    @ResponseBody
//    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<CommonPage<PmsBrand>> getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        CommonResult commonResult = null;
        try {
            commonResult = CommonResult.success(CommonPage.restPage(pmsBrandService.listBrand(keyword, pageNum, pageSize)));
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "批量删除品牌")
    @PostMapping(value = "/delete/batch")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult deleteBatch(
            @RequestParam("ids") List<Long> ids
    ){
        CommonResult commonResult = null;
        try {
            int count = pmsBrandService.deleteBrands(ids);
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




}
