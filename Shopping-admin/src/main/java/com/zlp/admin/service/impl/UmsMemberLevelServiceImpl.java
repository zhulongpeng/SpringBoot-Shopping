package com.zlp.admin.service.impl;

import com.zlp.admin.service.UmsMemberLevelService;
import com.zlp.mbg.mapper.UmsMemberLevelMapper;
import com.zlp.mbg.model.UmsMemberLevel;
import com.zlp.mbg.model.UmsMemberLevelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {

    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;

    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        UmsMemberLevelExample umsMemberLevelExample = new UmsMemberLevelExample();
        umsMemberLevelExample.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return umsMemberLevelMapper.selectByExample(umsMemberLevelExample);
    }
}
