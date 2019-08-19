package com.zlp.admin.service;


import com.zlp.admin.dto.PmsBrandParam;
import com.zlp.mbg.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

public interface PmsBrandService {

    int create(PmsBrandParam pmsBrandParam);

    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    int deleteBrand(Long id);

    List<PmsBrand> listAllBrand();

    List<PmsBrand> listBrand(String keyword, Integer pageNum, Integer pageSize);

    int deleteBrands(List<Long> ids);
}
