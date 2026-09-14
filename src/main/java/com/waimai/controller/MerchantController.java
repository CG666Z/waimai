package com.waimai.controller;

import com.waimai.entity.Merchant;
import com.waimai.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商家相关接口。
 */
@RestController
@RequestMapping("/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    // GET /merchants  查所有商家
    @GetMapping
    public List<Merchant> list() {
        return merchantService.listMerchants();
    }
}
