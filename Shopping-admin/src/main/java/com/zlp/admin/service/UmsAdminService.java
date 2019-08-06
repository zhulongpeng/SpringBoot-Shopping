package com.zlp.admin.service;

import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.mbg.model.UmsAdmin;
import com.zlp.mbg.model.UmsPermission;

import java.util.List;

public interface UmsAdminService {

    UmsAdmin register(UmsAdminParam umsAdminParam);

    UmsAdmin getAdminByUsername(String username);

    List<UmsPermission> getPermissionList(Long id);
}
