package com.wang.model;

import java.io.Serializable;

/**
 * @Desc Jedis测试
 * @Author Wang926454
 * @Date 2018/5/10 15:11
 */
public class Item implements Serializable {

    private static final long serialVersionUID = -271194151336975234L;

    private Integer id;

    private String name;

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
