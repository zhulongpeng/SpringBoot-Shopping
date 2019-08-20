package com.zlp.admin.service;

import com.zlp.admin.dto.PmsProductAttributeParam;
import org.springframework.transaction.annotation.Transactional;

public interface PmsProductAttributeService {

    @Transactional
    int create(PmsProductAttributeParam productAttributeParam);

}
