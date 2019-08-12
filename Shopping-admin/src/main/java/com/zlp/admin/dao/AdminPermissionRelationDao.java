package com.zlp.admin.dao;

import com.zlp.mbg.model.UmsAdminPermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Creared by Administrator on 2019/8/12
 */
public interface AdminPermissionRelationDao {
    int insertList(@Param("list") List<UmsAdminPermissionRelation> relationList);
}
