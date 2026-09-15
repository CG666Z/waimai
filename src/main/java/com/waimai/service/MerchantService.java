package com.waimai.service;

import com.waimai.entity.Merchant;
import com.waimai.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家业务逻辑。
 */
@Service
public class MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    public List<Merchant> listMerchants() {
        return merchantMapper.findAll();
    }
}
