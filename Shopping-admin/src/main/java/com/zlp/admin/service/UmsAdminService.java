package com.zlp.admin.service;

import com.zlp.admin.dto.UmsAdminLoginParam;
import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.mbg.model.UmsAdmin;
import com.zlp.mbg.model.UmsPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsAdminService {

    UmsAdmin register(UmsAdminParam umsAdminParam);

    UmsAdmin getAdminByUsername(String username);

    List<UmsPermission> getPermissionList(Long id);

    String login(UmsAdminLoginParam umsAdminLoginParam);

    String refreshToken(String token);

    List<UmsAdmin> userList(String name, Integer pageSize, Integer pageNum);

    int update(Long id, UmsAdmin umsAdmin);

    UmsAdmin getAdminById(Long id);

    @Transactional
    int updateRoles(Long adminId, String roleIds);
}
