package com.zlp.config.service.impl;

import com.zlp.config.service.DemoService;
import com.zlp.mbg.mapper.PmsBrandMapper;
import com.zlp.mbg.model.PmsBrand;
import com.zlp.mbg.model.PmsBrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }
}
