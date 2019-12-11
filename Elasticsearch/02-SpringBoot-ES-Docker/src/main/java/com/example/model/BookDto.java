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
     * 内容
     */
    private String content;

    /**
     * 描述
     */
    private String describe;

    /**
     * 空构造方法
     */
    public BookDto() {}

    /**
     * 构造赋值方法
     */
    public BookDto(Long id, String name, String content, String describe) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.describe = describe;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

}
