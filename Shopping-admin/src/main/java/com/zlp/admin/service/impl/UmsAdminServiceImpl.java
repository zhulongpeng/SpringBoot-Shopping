package com.zlp.admin.service.impl;

import com.zlp.admin.dao.UmsAdminRoleRelationDao;
import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.admin.service.UmsAdminService;
import com.zlp.common.api.Constant;
import com.zlp.common.util.XaUtil;
import com.zlp.mbg.mapper.UmsAdminMapper;
import com.zlp.mbg.model.UmsAdmin;
import com.zlp.mbg.model.UmsAdminExample;
import com.zlp.mbg.model.UmsPermission;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setStatus(Constant.Status.valid);
        umsAdmin.setCreateTime(new Date());
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        if(XaUtil.isNotEmpty(umsAdmins)) return null;
        String encode = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encode);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if(XaUtil.isEmpty(adminList)) return null;
        return adminList.get(0);
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }
}
