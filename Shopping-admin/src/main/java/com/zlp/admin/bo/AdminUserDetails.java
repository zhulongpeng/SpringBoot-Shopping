package com.zlp.admin.bo;

import com.zlp.mbg.model.UmsAdmin;
import com.zlp.mbg.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

import java.util.Collection;
import java.util.stream.Collectors;

public class AdminUserDetails implements UserDetails {

    private UmsAdmin umsAdmin;

    private List<UmsPermission> permissionList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> umsPermissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = umsPermissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回用户当前的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue()!=null)
                .map(permission ->new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
