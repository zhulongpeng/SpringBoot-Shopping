package com.zlp.admin.controller;

import com.zlp.admin.service.UmsMemberLevelService;
import com.zlp.common.api.CommonResult;
import com.zlp.mbg.model.UmsMemberLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * 会员等级管理
 */
@Controller
@Api(tags = "UmsMemberLevelController", description = "会员等级管理")
@RequestMapping("memberLevel")
public class UmsMemberLevelController {

    @Autowired
    private UmsMemberLevelService memberLevelService;

    @ApiOperation("查询会员等级")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<List<UmsMemberLevel>> list(
            @PathVariable("defaultStatus") Integer defaultStatus
    ){
        CommonResult commonResult = null;
        try {
            List<UmsMemberLevel> memberLevels = memberLevelService.list(defaultStatus);
            commonResult = CommonResult.success(memberLevels);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }



}
