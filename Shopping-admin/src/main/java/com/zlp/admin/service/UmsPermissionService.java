package com.zlp.admin.service;

import com.zlp.mbg.model.UmsPermission;

/**
 * Creared by Administrator on 2019/8/12
 */
public interface UmsPermissionService {

    int create(UmsPermission permission);

    int update(Long id, UmsPermission permission);

}
