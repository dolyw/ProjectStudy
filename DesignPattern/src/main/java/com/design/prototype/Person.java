package com.design.prototype;

/**
 * 人，姓名，年龄，地址
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/1 17:32
 */
public class Person implements Cloneable {

    private String name;
    private int age;
    private Location location;

    public Person(String name, int age, Location location) {
        this.name = name;
        this.age = age;
        this.location = location;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        // 基础属性String及int可以不用处理
        // 内置引用不重新进行克隆是浅拷贝，会导致指向同一个Location
        // 必须针对内部引用再次进行克隆，这样才是深拷贝
        person.setLocation((Location) location.clone());
        return person;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", location=" + location +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
