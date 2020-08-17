package com.wang.decrypt;

import java.util.Base64;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * Base64
 *
 * @author Wang926454
 * @date 2018/8/21 15:14
 */
public class EncrypBase64 {

    /**
     * 加密JDK1.8
     *
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String encode(String str) throws Exception {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     *
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String decode(String str) throws Exception {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }

    /**
     * 加密JDK1.7
     *
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String encode7(String str) throws Exception {
        return new BASE64Encoder().encode(str.getBytes("utf-8"));
    }

    /**
     * 解密JDK1.7
     *
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String decode7(String str) throws Exception {
        byte[] b = new BASE64Decoder().decodeBuffer(str);
        return new String(b, "utf-8");
    }

    /**
     * 测试
     *
     * @param args
     * @return void
     * @author Wang926454
     * @date 2018/8/21 15:16
     */
    public static void main(String[] args) throws Exception {
        EncrypBase64 de1 = new EncrypBase64();
        String msg = "1、同一保险期限内，每一被保险人限购一份，多买无效。\n" +
                "Within the same period of insurance, each insured person is restricted to one purchase. Any additional purchase shall be invalid.\n" +
                "2、被保险人的承保年龄为出生满30天（含）至80周岁（含）。\n" +
                "Coverage of the insured person's age is 30 days （including 30 days） to 80 years of age （including）.\n" +
                "3、本保单仅承保被保险人从中国大陆境内出发的旅行，且必须于出行前投保并交付保险费，以保证保险及时起保。\n" +
                "This policy covers only the insured's journey from the mainland of China and must be insured and premium paid before the trip to ensure the timely commencement of insurance.\n" +
                "4、本保单仅承保在境内常住的被保险人，即最近一年内在中国大陆境内工作或居住满183天的要求；如涉及紧急救援，将送返至被保险人在中国的常住地址。\n" +
                "This policy covers only the insured who is resident in China, which is the requirement of working or residing in the mainland of China for 183 days in the last year; in case of emergency rescue, it will be returned to the insured's resident address in China.\n" +
                "5、根据中国保监会规定：父母为其未成年子女投保的人身保险，在被保险人成年之前，各保险合同约定的被保险人死亡给付的保险金额总和、被保险人死亡时各保险公司实际给付的保险金总和按以下限额执行：对于被保险人不满10周岁的，不得超过人民币20万元；对于被保险人已满10周岁但未满18周岁的，不得超过人民币50万元。\n" +
                "According to the regulations of CIRC: the death benefit limit for person under 10 years old is RMB 200,000, while the death benefit limit for person between 10 to 18 years old is RMB 500,000.\n" +
                "6、保单生效时年满71-80周岁的被保险人，其涉及的身故、伤残及医疗费用相关保险的保险金额为上述保障计划中所载保险金额的一半（50%），保险费维持不变。\n" +
                "Insured persons aged 71-80 at the time of the policy's entry into force cover half （50%） of the amount of insurance covered by the above-mentioned insurance scheme for death, disability and medical expenses, and the premium remains unchanged.\n" +
                "7、本保单不承保任何直接或间接由于计划或实际前往投保时已经处于战争状态、已被宣布为紧急状态的以及已危险提示仍继续前往的国家或地区，或在上述国家或地区旅行期间发生的意外事故。\n" +
                "This policy does not cover any country or region that has been in a state of war, declared to be in a state of emergency and has been warned of continuing to travel, either directly or indirectly, as a result of planned or actual travel to the country or region, or in the event of an accident occurring during the travel of the said country or region.\n";
        String encodeMsg7 = EncrypBase64.encode7(msg);
        //System.out.println("JDK1.7明文是:" + msg);
        System.out.println("JDK1.7加密后:" + encodeMsg7);
        System.out.println("解密后:" + EncrypBase64.decode7(encodeMsg7));
        String encodeMsg = EncrypBase64.encode(msg);
        //System.out.println("JDK1.8明文是:" + msg);
        //System.out.println("JDK1.8加密后:" + encodeMsg);
        System.out.println("解密后:" + EncrypBase64.decode("aHR0cDovL2xvY2FsaG9zdDo4MDgwLz9hdXRoU3RhdGU9MQ=="));
    }

}
