package com.zlp.admin.service;

import com.zlp.admin.dto.UmsPermissionNode;
import com.zlp.mbg.model.UmsPermission;

import java.util.List;

/**
 * Creared by Administrator on 2019/8/12
 */
public interface UmsPermissionService {

    int create(UmsPermission permission);

    int update(Long id, UmsPermission permission);

    int delete(List<Long> ids);

    List<UmsPermissionNode> treeList();

    List<UmsPermission> list();
}
