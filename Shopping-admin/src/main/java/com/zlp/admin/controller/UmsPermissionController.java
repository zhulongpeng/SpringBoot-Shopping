package com.zlp.admin.controller;

import com.zlp.admin.dto.UmsPermissionNode;
import com.zlp.admin.service.UmsPermissionService;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.UmsPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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

    @ApiOperation(value = "根据id批量删除权限")
    @PostMapping(value = "/delete")
    @ResponseBody
    public CommonResult delete(
            @RequestParam("ids") List<Long> ids
    ){
        CommonResult commonResult = null;
        try {
            int count = permissionService.delete(ids);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation("以层级结构返回所有列表")
    @GetMapping(value = "/treeList")
    @ResponseBody
    public CommonResult<List<UmsPermissionNode>> treeList(){
        CommonResult commonResult = null;
        try {
            List<UmsPermissionNode> permissionNodeList = permissionService.treeList();
            commonResult = CommonResult.success(permissionNodeList);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "获取所有权限列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<List<UmsPermission>> list(){
        CommonResult commonResult = null;
        try {
            List<UmsPermission> permissionList = permissionService.list();
            commonResult = CommonResult.success(permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

}
