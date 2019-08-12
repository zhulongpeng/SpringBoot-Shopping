package com.zlp.admin.controller;

import com.zlp.admin.dto.UmsAdminLoginParam;
import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.admin.service.UmsAdminService;
import com.zlp.common.api.CommonPage;
import com.zlp.common.api.CommonResult;
import com.zlp.common.util.XaUtil;
import com.zlp.mbg.model.UmsAdmin;
import com.zlp.mbg.model.UmsPermission;
import com.zlp.mbg.model.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

@Api(tags = "UmsAdminController", description = "后台用户管理")
@Controller
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService adminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult<UmsAdmin> register(
            @RequestBody UmsAdminParam umsAdminParam
    ){
        CommonResult<UmsAdmin> commonResult = null;
        try {
            UmsAdmin umsAdmin = adminService.register(umsAdminParam);
            if(umsAdmin == null) commonResult = CommonResult.failed();
            commonResult = CommonResult.success(umsAdmin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    @ApiOperation(value = "登录之后返回token")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CommonResult login(
            @RequestBody UmsAdminLoginParam umsAdminLoginParam
    ){
        CommonResult commonResult = null;
        try {
            String token = adminService.login(umsAdminLoginParam);
            if(XaUtil.isNotEmpty(token)) {
                HashMap<String, String> result = new HashMap<>();
                result.put("token", token);
                result.put("tokenHead", tokenHead);
                commonResult = CommonResult.success(result);
            }else{
                commonResult = CommonResult.validateFailed("用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/token/refresh")
    @ResponseBody
    public CommonResult refreshToken(
            HttpServletRequest request
    ){
       String token = request.getHeader(tokenHeader);
       String refreshToken = adminService.refreshToken(token);
       if(XaUtil.isEmpty(refreshToken)){
           return CommonResult.failed();
       }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "info")
    @ResponseBody
    public CommonResult getAdminInfo(
            Principal principal
    ){
        CommonResult commonResult = null;
        try {
            String userName = principal.getName();
            UmsAdmin umsAdmin = adminService.getAdminByUsername(userName);
            Map<String, Object> data = new HashMap<>();
            data.put("username", umsAdmin.getUsername());
            data.put("roles", new String[]{"TEST"});
            data.put("icon", umsAdmin.getIcon());
            commonResult = CommonResult.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "登出功能")
    @PostMapping(value = "/logout")
    @ResponseBody
    public CommonResult logout(){
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> userList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
    ){
        CommonResult commonResult = null;
        try {
            List<UmsAdmin> adminList = adminService.userList(name, pageSize, pageNum);
            commonResult = CommonResult.success(CommonPage.restPage(adminList));
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonResult updateAdmin(
            @PathVariable Long id,
            @RequestBody UmsAdmin umsAdmin
    ){
        CommonResult commonResult = null;
        try {
            int count =  adminService.update(id, umsAdmin);
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

    @ApiOperation("通过id获取admin信息")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(
            @PathVariable Long id
    ){
        CommonResult commonResult = null;
        try {
            UmsAdmin umsAdmin = adminService.getAdminById(id);
            if(XaUtil.isEmpty(umsAdmin)) {
                commonResult = CommonResult.success(umsAdmin);
            }else{
                commonResult = CommonResult.failed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    @ResponseBody
    public CommonResult roleUpdate(
            @ApiParam(name = "adminId", value = "adminId") @RequestParam(name = "adminId") Long adminId,
            @ApiParam(name = "roleIds", value = "roleIds用分号间隔") @RequestParam(name = "roleIds") String roleIds
    ){
        CommonResult commonResult = null;
        try {
            int count = adminService.updateRoles(adminId, roleIds);
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


    @ApiOperation(value = "删除指定用户信息")
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    public CommonResult delete(
            @PathVariable Long id
    ){
        CommonResult commonResult = null;
        try {
            int count =  adminService.delete(id);
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

    @ApiOperation(value = "获取指定用户的角色")
    @GetMapping(value = "/role/{adminId}")
    @ResponseBody
    public CommonResult<List<UmsRole>> getRoleList(
            @PathVariable Long adminId
    ){
        CommonResult commonResult = null;
        try {
            List<UmsRole> roleList = adminService.getRoleList(adminId);
            commonResult = CommonResult.success(roleList);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation("给用户分配增减权限")
    @PostMapping(value = "/permission/update")
    @ResponseBody
    public CommonResult updatePermission(
            @RequestParam Long adminId,
            @RequestParam("permissionIds") List<Long> permissionIds
    ){
        CommonResult commonResult = null;
        try {
            int count = adminService.updatePermission(adminId, permissionIds);
            if(count > 0){
                commonResult = CommonResult.success(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    @ApiOperation(value = "获取用户所有权限包括+-权限")
    @GetMapping
    @ResponseBody
    public CommonResult<List<UmsPermission>> getUmsPermissionList(
            @PathVariable Long adminId
    ){
        CommonResult commonResult = null;
        try {
            List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
            commonResult = CommonResult.success(permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }


}
