package com.zlp.admin.service.impl;

import com.zlp.admin.dao.UmsRolePermissionRelationDao;
import com.zlp.admin.service.UmsRoleService;
import com.zlp.mbg.mapper.UmsRoleMapper;
import com.zlp.mbg.mapper.UmsRolePermissionRelationMapper;
import com.zlp.mbg.model.UmsRole;
import com.zlp.mbg.model.UmsRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;

    @Autowired
    private UmsRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    public int createRole(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus(1);
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int deleteRoles(List<Long> ids) {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(umsRoleExample);
    }
}
