package com.design.chainofresponsibility;

/**
 * 责任链模式(职责链模式)
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/19 10:35
 */
public class Main {

    public static void main(String[] args) {
        StudentClient studentClient = new StudentClient();
        studentClient.leave(3, "王明请假");
        studentClient.leave(7, "王美丽请假");
        studentClient.leave(10, "王小明请假");
        studentClient.leave(13, "王晓婷请假");
    }

}
