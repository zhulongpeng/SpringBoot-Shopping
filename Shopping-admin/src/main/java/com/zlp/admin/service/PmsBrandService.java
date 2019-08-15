package com.zlp.admin.service;


import com.zlp.admin.dto.PmsBrandParam;
import org.springframework.transaction.annotation.Transactional;

public interface PmsBrandService {

    int create(PmsBrandParam pmsBrandParam);

    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);
}
