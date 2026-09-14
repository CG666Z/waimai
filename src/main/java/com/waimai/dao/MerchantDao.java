package com.waimai.dao;

import com.waimai.entity.Merchant;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商家表的数据访问。
 */
@Repository
public class MerchantDao extends BaseDao {

    // 查所有商家（逛店列表用）
    public List<Merchant> findAll() {
        String sql = "SELECT id, name, phone, address, category FROM merchant";
        return executeQuery(sql, rs -> {
            Merchant m = new Merchant();
            m.setId(rs.getLong("id"));
            m.setName(rs.getString("name"));
            m.setPhone(rs.getString("phone"));
            m.setAddress(rs.getString("address"));
            m.setCategory(rs.getString("category"));
            return m;
        });
    }
}
