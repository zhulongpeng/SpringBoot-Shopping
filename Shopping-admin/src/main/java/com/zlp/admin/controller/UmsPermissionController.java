package com.zlp.admin.controller;

import com.zlp.admin.service.UmsPermissionService;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.UmsPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 后台用户权限管理
 */
@Controller
@Api(tags = "UmsPermissionController",description = "后台用户权限管理")
@RequestMapping("/permission")
public class UmsPermissionController {

    @Autowired
    private UmsPermissionService permissionService;

    @ApiOperation(value = "添加权限")
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult create(
            @RequestBody UmsPermission permission
    ){
        CommonResult commonResult = null;
        try {
            int count = permissionService.create(permission);
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

    @ApiOperation(value = "修改权限")
    @PostMapping(value = "/update/{id}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult update(
            @PathVariable Long id,
            @RequestBody UmsPermission permission){
        CommonResult commonResult = null;
        try {
            int count = permissionService.update(id,permission);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }
}
