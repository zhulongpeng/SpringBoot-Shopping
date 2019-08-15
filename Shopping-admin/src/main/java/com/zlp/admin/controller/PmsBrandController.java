package com.zlp.admin.controller;

import com.zlp.admin.dto.PmsBrandParam;
import com.zlp.admin.service.PmsBrandService;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.CommentGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("更细品牌")
    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:update')")
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

}
