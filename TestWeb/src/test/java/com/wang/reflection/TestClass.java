package com.wang.reflection;

import com.wang.model.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc 对象测试
 * @Author wang926454
 * @Date 2018/6/19 15:33
 */
public class TestClass {

    /**
     * @Desc 获取属性名，类型，值
     * @Author wang926454
     * @Date 2018/7/27 10:35
     */
    @Test
    public void Class() {
        User user = new User();
        List<Map<String, Object>> list = this.getFiledsInfo(user);
        System.out.println(list);
    }

    /**
     * 根据属性名获取属性值
     */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            //log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    private String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * 无法获取继承类的私有属性
     */
    private List<Map<String, Object>> getFiledsInfo(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> infoMap;
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            infoMap = new HashMap<String, Object>();
            infoMap.put("type", field.getType().toString());
            infoMap.put("name", field.getName());
            infoMap.put("value", getFieldValueByName(field.getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     */
    public Object[] getFiledValues(Object o) {
        String[] fieldNames = this.getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = this.getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }

    /**
     * @Desc 获取java.util.HashSet所有方法名和参数名
     * @Author wang926454
     * @Date 2018/7/27 10:35
     */
    @Test
    public void Class2() {
        getMethodInfo("java.util.HashSet");
    }

    @Test
    public void Class3() {
        getMethodInfo("com.wang.model.User");
    }

    /**
     * @Desc 传入全类名获得对应类中所有方法名和参数名
     * @Author wang926454
     * @Date 2018/7/27 10:34
     */
    private void getMethodInfo(String pkgName) {
        try {
            Class clazz = Class.forName(pkgName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                System.out.println("方法名称:" + methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    String parameterName = clas.getName();
                    System.out.println("参数名称:" + parameterName);
                }
                System.out.println("*****************************");
            }
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }
}
