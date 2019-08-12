package com.zlp.admin.service;

import com.zlp.mbg.model.UmsMemberLevel;

import java.util.List;

public interface UmsMemberLevelService {

    List<UmsMemberLevel> list(Integer defaultStatus);

}
