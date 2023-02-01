package com.design.composite.v1;

/**
 * 组合模式
 *
 * 使用 Employee 类来创建和打印员工的树形层次结构
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 14:07
 */
public class Main {

    public static void main(String[] args) {
        Employee Ceo = new Employee("王明", "CEO", 30000);

        Employee headSales = new Employee("销售主管", "销售部门", 20000);

        Employee headMarketing = new Employee("营销主管", "营销部门", 20000);

        Employee clerk1 = new Employee("文员1", "营销部门", 10000);
        Employee clerk2 = new Employee("文员2", "营销部门", 10000);

        Employee sales1 = new Employee("销售1", "销售部门", 10000);
        Employee sales2 = new Employee("销售2", "销售部门", 10000);

        Ceo.add(headSales);
        Ceo.add(headMarketing);

        headSales.add(sales1);
        headSales.add(sales2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        // 打印该组织的所有员工
        System.out.println(Ceo);
        for (Employee headEmployee : Ceo.getSubordinates()) {
            System.out.println(headEmployee);
            for (Employee employee : headEmployee.getSubordinates()) {
                System.out.println(employee);
            }
        }
    }

}
