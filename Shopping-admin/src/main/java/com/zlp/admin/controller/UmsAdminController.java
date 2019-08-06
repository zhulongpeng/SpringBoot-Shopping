package com.zlp.admin.controller;

import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.admin.service.UmsAdminService;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.UmsAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @PostMapping(value = "/register")
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


}
