package com.waimai.service;

import com.waimai.dao.MerchantDao;
import com.waimai.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家业务逻辑。
 */
@Service
public class MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    public List<Merchant> listMerchants() {
        return merchantDao.findAll();
    }
}
