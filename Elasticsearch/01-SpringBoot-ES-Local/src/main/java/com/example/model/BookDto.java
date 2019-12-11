package com.example.model;


import java.io.Serializable;

/**
 * BookDto
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/8/19 18:57
 */
public class BookDto implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 空构造方法
     */
    public BookDto() {}

    /**
     * 构造赋值方法
     */
    public BookDto(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
