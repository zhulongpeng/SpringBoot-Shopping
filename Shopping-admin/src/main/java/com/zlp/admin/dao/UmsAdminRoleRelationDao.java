package com.zlp.admin.dao;

import com.zlp.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Creared by Administrator on 2019/8/6
 */
public interface UmsAdminRoleRelationDao {

    List<UmsPermission> getPermissionList(@Param("admin") Long adminId);
}
