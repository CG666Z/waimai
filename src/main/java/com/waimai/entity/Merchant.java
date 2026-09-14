package com.waimai.entity;

/**
 * 商家实体类，对应数据库 merchant 表。
 */
public class Merchant {

    private Long id;
    private String name;
    private String phone;
    private String address;
    private String category;

    public Merchant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Merchant{id=" + id + ", name='" + name + "', category='" + category + "'}";
    }
}
