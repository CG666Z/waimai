package com.waimai.mapper;

import com.waimai.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商家表的数据访问（MyBatis 版）。
 */
@Mapper
public interface MerchantMapper {

    @Select("SELECT id, name, phone, address, category FROM merchant")
    List<Merchant> findAll();
}
