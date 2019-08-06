package com.zlp.admin.controller;

import com.zlp.admin.dto.UmsAdminLoginParam;
import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.admin.service.UmsAdminService;
import com.zlp.common.api.CommonResult;
import com.zlp.common.util.XaUtil;
import com.zlp.mbg.model.UmsAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

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
            if(XaUtil.isEmpty(token)) commonResult = CommonResult.validateFailed("用户名或密码错误");
            HashMap<String, String> result = new HashMap<>();
            result.put("token", token);
            result.put("tokenHead", tokenHead);
            commonResult = CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }


}
