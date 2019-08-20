package com.zlp.admin.controller;

import com.zlp.admin.dto.PmsProductAttributeParam;
import com.zlp.admin.service.PmsProductAttributeService;
import com.zlp.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 商品属性管理
 */
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RestController
@RequestMapping(value = "/PmsProductAttributeController")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;

    @ApiOperation(value = "添加商品属性信息")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductAttributeParam productAttributeParam, BindingResult bindingResult){
        CommonResult commonResult = null;
        try {
            int count = pmsProductAttributeService.create(productAttributeParam);
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
