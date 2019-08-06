package com.zlp.config.controller;

import com.zlp.common.api.CommonPage;
import com.zlp.common.api.CommonResult;
import com.zlp.config.dto.PmsBrandDto;
import com.zlp.config.service.DemoService;
import com.zlp.mbg.model.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "demo", description = "demo详情")
@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @ApiOperation(value = "获取全部品牌列表")
    @GetMapping("/brand/listALL")
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(demoService.listAllBrand());
    }

    @ApiOperation(value = "添加品牌")
    @PostMapping(value = "/brand/listAll", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult createBrand(@Validated @RequestBody PmsBrandDto pmsBrand, BindingResult result){
        if(result.hasErrors()){
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if(count == 1){
            commonResult = CommonResult.success(pmsBrand);
        }else{
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }

    @ApiOperation("更新品牌")
    @PostMapping(value = "/brand/update/{id}")
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @Validated @RequestBody PmsBrandDto pmsBrandDto, BindingResult result){
        if(result.hasErrors()){
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if(count == 1){
            commonResult = CommonResult.success(pmsBrandDto);
        }else{
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }

    @ApiOperation(value = "删除品牌")
    @GetMapping(value = "/brand/delete/{id}")
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id){
        int count = demoService.deleteBrand(id);
        if(count == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failed("操作失败");
        }
    }

    @ApiOperation(value = "分页获取品牌列表")
    @GetMapping(value = "/brand/list")
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize
    ){
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @ApiOperation(value = "根据编号查询品牌信息")
    @GetMapping(value = "/brand/{id}")
    @ResponseBody
    public CommonResult<PmsBrand> brand(
            @PathVariable("id") Long id
    ){
        return CommonResult.success(demoService.getBrand(id));
    }

}
