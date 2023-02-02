package com.design.prototype;

/**
 * 地址
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/1 17:32
 */
public class Location implements Cloneable {

    private StringBuffer address;
    private String remark;

    public Location(StringBuffer address, String remark) {
        this.address = address;
        this.remark = remark;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // String基础属性可以不用处理，但是StringBuffer等引用对象需要处理
        Location location = (Location) super.clone();
        location.setAddress(new StringBuffer(address));
        return location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address=" + address +
                ", remark='" + remark + '\'' +
                '}';
    }

    public StringBuffer getAddress() {
        return address;
    }

    public void setAddress(StringBuffer address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
