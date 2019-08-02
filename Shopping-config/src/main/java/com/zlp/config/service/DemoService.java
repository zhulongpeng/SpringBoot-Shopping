package com.zlp.config.service;

import com.zlp.config.dto.PmsBrandDto;
import com.zlp.mbg.model.PmsBrand;

import java.util.List;

/**
 * Creared by Administrator on 2019/8/1
 */
public interface DemoService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrandDto pmsBrand);
}
