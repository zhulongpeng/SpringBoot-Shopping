package com.zlp.admin.controller;

import com.zlp.admin.service.UmsRoleService;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "UmsRoleController",description = "后台用户角色管理")
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult create(
            @RequestBody UmsRole role
    ){
        CommonResult commonResult = null;
        try {
            int count = roleService.createRole(role);
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

    @ApiOperation(value = "修改角色")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonResult update(
            @PathVariable Long id, @RequestBody UmsRole role
    ){
        CommonResult commonResult = null;
        try {
            int count = roleService.updateRole(id, role);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "批量删除角色")
    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult delete(
            @RequestParam("ids") List<Long> ids
    ){
        CommonResult commonResult = null;
        try {
            int count = roleService.deleteRoles(ids);
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
