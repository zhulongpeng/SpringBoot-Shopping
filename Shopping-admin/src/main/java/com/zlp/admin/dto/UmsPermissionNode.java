package com.zlp.admin.dto;

import com.zlp.mbg.model.UmsPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UmsPermissionNode extends UmsPermission {

    @Getter
    @Setter
    private List<UmsPermissionNode> children;

}
