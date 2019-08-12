package com.zlp.admin.service;

import com.zlp.mbg.model.UmsRole;

import java.util.List;

public interface UmsRoleService {

    int createRole(UmsRole role);

    int updateRole(Long id, UmsRole role);

    int deleteRoles(List<Long> ids);

}
