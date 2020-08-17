package com.wang.other;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.wang.model.HttpCode;
import com.wang.util.HmacSha256Util;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Desc 其他测试
 * @Author wang926454
 * @Date 2018/6/19 15:33
 */
public class TestOther {

    private static final Logger logger = LoggerFactory.getLogger(TestOther.class);

    @Test
    public void String() {
        // 格式化输出
        int i = 1;
        System.out.println(String.format("%02d", i));

        //测试01相加
        String s = "01";
        System.out.println(Integer.parseInt(s));
        System.out.println(Integer.parseInt(s) + 1);
        System.out.println(String.format("%02d", Integer.parseInt(s) + 1));

        //测试15相加
        String s2 = "15";
        System.out.println(Integer.parseInt(s2));
        System.out.println(Integer.parseInt(s2) + 1);
        System.out.println(String.format("%02d", Integer.parseInt(s2) + 1));
    }

    @Test
    public void String2() {
        String s2 = "0101";
        System.out.println(Integer.parseInt(s2));
        System.out.println(Integer.parseInt(s2) + 1);
        System.out.println(String.format("%06d", Integer.parseInt(s2) + 1));
    }

    @Test
    public void String3() {
        String s1 = "pdms_dev".replace("pdms_", "");
        System.out.println(s1);
        Random random = new Random();
        System.out.println(random.nextInt(10));
    }

    /**
     * @Desc 两个时间间隔
     * @Author Wang926454
     * @Date 2018/6/23 14:15
     */
    @Test
    public void Date() {
        String date1 = "2018-07-07";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(date1);
            System.out.println(this.differentHoursByMillisecond(new Date(), date));
        } catch (ParseException e) {
            e.getMessage();
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentHoursByMillisecond(Date date1, Date date2) {
        int hour = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600));
        return hour;
    }

    /**
     * @Desc 逗号分割测试
     * @Author Wang926454
     * @Date 2018/7/4 13:34
     */
    @Test
    public void StringBuffer() {
        StringBuffer stringBuffer = new StringBuffer("34,55,88");
        System.out.println(stringBuffer.toString());

        StringBuffer strbuff = new StringBuffer();
        String no = "3411,5522,8833";
        strbuff.append(no);
        System.out.println(strbuff.toString());

        String[] strArray = convertStrToArray(no);
        for (String str : strArray) {
            System.out.println(str);
        }
        for (int i = 0; i < strArray.length; i++) {
            System.out.println(strArray[i]);
        }
    }

    /**
     * @Desc 将字符串以逗号分割成数组
     * @Author Wang926454
     * @Date 2018/7/4 13:48
     */
    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    /**
     * 格式化输出时间
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/25 18:10
     */
    @Test
    public void Date2() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()).toString());
        System.out.println(System.currentTimeMillis());
        long time = 1536308101495L;
        System.out.println(System.currentTimeMillis() - time);
    }

    @Test
    public void t3() {
        try {
            System.out.println("c");
            return;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            System.out.println("finally");
        }
        System.out.println("con");
    }

    @Test
    public void t4() {
        String s = "dsa2:dsa1:wqe3";
        String[] strArray = null;
        strArray = s.split(":"); //拆分字符为"," ,然后把结果交给数组strArray
        System.out.println(strArray[strArray.length - 1]);
    }

    @Test
    public void t5() {
        System.out.println(HttpCode.SUCCESS);
        System.out.println(HttpCode.SUCCESS.code());
    }

    @Test
    public void t6() {
        List<String> nameList = new ArrayList<>();
        nameList.add("罗坚 - ljian");
        nameList.add("林鑫 - lxin");
        nameList.add("李玲莉 - llingli");
        nameList.add("董宾波 - dbinbo");
        nameList.add("袁少杰 - yshaojie");
        nameList.add("李慧 - lhui");
        nameList.add("张勇峰 - zhyongfeng");
        nameList.add("杨文兰 - ywenlan");
        nameList.add("李奇 - lqi");
        nameList.add("王正荣 - wangzr");
        nameList.add("席芳 - xif");
        nameList.add("向峰雄 - xiangfx");
        nameList.add("刘丹 - liudan");
        nameList.add("王凯 - wangka");
        nameList.add("张永辉 - zhangyonghui");
        nameList.add("范相国 - fanxiangguo");
        nameList.add("姜星 - jiangxing");
        nameList.add("戚静雨 - qijingyu");
        nameList.add("陈悦 - chenyue");
        nameList.add("李双双 - liss");
        nameList.add("刘爱青 - liuaq");
        nameList.add("高雅 - gaoy");
        nameList.add("姜玉洋 - jiangyuyang");
        nameList.add("李惠兰 - lihuilan");
        nameList.add("李娟儒 - lijuanru");
        nameList.add("颜翠 - yancui");
        nameList.add("张琳 - zhanglin");
        nameList.add("金永光 - jinyongguang");
        nameList.add("谭启涵 - tanqihan");
        nameList.add("陈禧琳 - chenxilin");
        nameList.add("陈泓霏 - chenhongfei");
        nameList.add("池婷婷 - chitingting");
        nameList.add("白皓 - baihao");
        nameList.add("王利多 - wliduo");
        nameList.add("胡中秋 - hzhongqiu");
        nameList.add("罗雄 - lxiong");
        nameList.add("谢睿昌 - xruichang");
        nameList.add("王鹏飞 - wpengfei");
        StringBuilder userInsert = null;
        StringBuilder gwInsert = null;
        String start = "INSERT INTO `t_sso_user` (`employee_id`, `user_code`, `user_name`, `password`, `pwd_set_date`, `pwd_expire_date`, `entry_time`, `indentifyno`, `mobile`, `secret`, `validind`, `certified`, `locked`, `phone`, `address`, `email`, `remark`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES ('";
        String end = "', '7bc4123f52b862662652399f06e38a7fc70e55293014f8e5ab2e454f9bd9445d7bde48f17ed5f2873485986ccf6df0e36155e43754530394b47ef2fb64c59a29', '2019-01-14 00:00:00', '2020-01-31 00:00:00', null, null, '', null, '1', null, '0', null, null, 'xxx@xx.com', null, 'wliduo NP-263', now(), 'wliduo NP-263', now());";
        String startGw = "INSERT INTO `t_sso_user_grade` (`grade_code`, `user_id`, `created_by`, `created_date`, `updated_by`, `updated_date`) select '";
        String endGw = "', u.user_id, 'wliduo NP-263', now(), 'wliduo NP-263', now() from t_sso_user u where u.user_code = '";
        for (String name: nameList) {
            if (name.length() <= 0) {
                continue;
            }
            String[] nameArray = name.split(" - ");
            String temp = nameArray[0];
            nameArray[0] = nameArray[1];
            nameArray[1] = temp;
            userInsert = new StringBuilder(start);
            userInsert.append(nameArray[0]).append("cpjl").append("', '").append(nameArray[0]).append("cpjl")
                    .append("', '").append(nameArray[1]).append("-产品经理").append(end);
            System.out.println(userInsert.toString());
            gwInsert = new StringBuilder(startGw);
            gwInsert.append("GW0001").append(endGw).append(nameArray[0]).append("cpjl';");
            System.out.println(gwInsert.toString());
            gwInsert = new StringBuilder(startGw);
            gwInsert.append("QUOTMS0002").append(endGw).append(nameArray[0]).append("cpjl';");
            System.out.println(gwInsert.toString());
            System.out.println("");
            userInsert = new StringBuilder(start);
            userInsert.append(nameArray[0]).append("fashy").append("', '").append(nameArray[0]).append("fashy")
                    .append("', '").append(nameArray[1]).append("-方案审核业管").append(end);
            System.out.println(userInsert.toString());
            gwInsert = new StringBuilder(startGw);
            gwInsert.append("GW0003").append(endGw).append(nameArray[0]).append("fashy';");
            System.out.println(gwInsert.toString());
            gwInsert = new StringBuilder(startGw);
            gwInsert.append("QUOTMS0002").append(endGw).append(nameArray[0]).append("fashy';");
            System.out.println(gwInsert.toString());
            System.out.println("");
            userInsert = new StringBuilder(start);
            userInsert.append(nameArray[0]).append("fasp").append("', '").append(nameArray[0]).append("fasp")
                    .append("', '").append(nameArray[1]).append("-方案审批").append(end);
            System.out.println(userInsert.toString());
            gwInsert = new StringBuilder(startGw);
            gwInsert.append("GW0004").append(endGw).append(nameArray[0]).append("fasp';");
            System.out.println(gwInsert.toString());
            gwInsert = new StringBuilder(startGw);
            gwInsert.append("QUOTMS0002").append(endGw).append(nameArray[0]).append("fasp';");
            System.out.println(gwInsert.toString());
            System.out.println("");


        }
    }

    @Test
    public void t7() {
        String fileName = "E:\\Work\\My File\\202003\\SSO\\开发人员sso账户授权产品工厂权限.xlsx";
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName).sheet().doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            // logger.info("读取到数据:{}", JSON.toJSONString(data));
            String[] nameArray = { data.get(0), data.get(1)};
            logger.info("INSERT INTO `t_sso_user`(`employee_id`, `user_code`, `user_name`, `password`, `pwd_set_date`, `pwd_expire_date`, `entry_time`, `indentifyno`, `mobile`, `secret`, `validind`, `certified`, `locked`, `phone`, `address`, `email`, `remark`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES ('{}', '{}', '{}', '7bc4123f52b862662652399f06e38a7fc70e55293014f8e5ab2e454f9bd9445d7bde48f17ed5f2873485986ccf6df0e36155e43754530394b47ef2fb64c59a29', '2020-03-12 00:00:00', '2020-05-25 00:00:00', NULL, NULL, '', NULL, '1', '0', '0', NULL, NULL, 'xxx@xxx.com', NULL, 'wliduo NP-263', now(), 'wliduo NP-263', now());", nameArray[1], nameArray[1], nameArray[0]);
            logger.info("INSERT INTO `t_sso_user_app`(`app_id`, `user_id`, `validind`, `created_by`, `created_date`, `updated_by`, `updated_date`) select '157a5152c6724ada8ba34f52c12e8f72', u.user_id, '1', 'wliduo NP-263', now(), 'wliduo NP-263', now() from t_sso_user u where u.user_code = '{}';", nameArray[1]);
            logger.info("INSERT INTO `t_sso_user_app`(`app_id`, `user_id`, `validind`, `created_by`, `created_date`, `updated_by`, `updated_date`) select '53441aef81dd41ca93df1ca5d888f9c6', u.user_id, '1', 'wliduo NP-263', now(), 'wliduo NP-263', now() from t_sso_user u where u.user_code = '{}';", nameArray[1]);
            logger.info("INSERT INTO `t_sso_user_company`(`company_id`, `user_id`, `created_by`, `created_date`, `updated_by`, `updated_date`) select '012204', u.user_id, 'wliduo NP-263', now(), 'wliduo NP-263', now() from t_sso_user u where u.user_code = '{}';", nameArray[1]);
            logger.info("INSERT INTO `t_sso_user_grade` (`grade_code`, `user_id`, `created_by`, `created_date`, `updated_by`, `updated_date`) select 'GW0006', u.user_id, 'wliduo NP-263', now(), 'wliduo NP-263', now() from t_sso_user u where u.user_code = '{}';", nameArray[1]);
            logger.info("INSERT INTO `t_sso_user_grade` (`grade_code`, `user_id`, `created_by`, `created_date`, `updated_by`, `updated_date`) select 'QUOTMS0001', u.user_id, 'wliduo NP-263', now(), 'wliduo NP-263', now() from t_sso_user u where u.user_code = '{}';", nameArray[1]);
        }
    }

    @Test
    public void t8() {
        String s1 = "";
        String s2 = new String("");
        System.out.println(s1.equals(s2));
        for (int i = 0; i < 1196; i++) {
            s1 = s1 + i + "+";
        }
        System.out.println(s1);
    }

    @Test
    public void t9() {
        String s1 = "gdser";
        String s2 = new String("王");
        System.out.println(HmacSha256Util.hmacSha256(s2, s1));
    }

    @Test
    public void t10() throws Exception {
        String s1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http://vass.ngrok2.xiaomiqiu.cn/portal/getCode?reqUrl=REQURL&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
        String s2 = URLEncoder.encode(s1, "UTF-8");
        String s3 = URLDecoder.decode(s2, "UTF-8");
        System.out.println(s2);
        System.out.println(s3);
    }

    public static void main(String[] args) throws Exception {
        /**
         * 准备发送端
         * DatagramSocket()
         * 构造一个数据报套接字绑定到本地主机机器上的任何可用的端口。
         */
        DatagramSocket ds = new DatagramSocket();
        /**
         * 准备数据包
         *1、 DatagramPacket(byte[] buf, int length)
         * 构造一个 DatagramPacket length接收数据包的长度
         *2、 String的getBytes()
         * 方法是得到一个操作系统默认的编码格式的字节数组
         *3、 setSocketAddress()
         * 设置SocketAddress(通常是IP地址+端口号)都的远程主机发送数据报。
         * 4、InetSocketAddress(InetAddress addr, int port)
         * 创建一个套接字地址的IP地址和端口号。
         */
        String str = "3,19900000001,20171101155915,1,113.26171805987126,23.239234619127426,0.0,192,82|83,WS001,2020051100011002,13570577458";
        byte[] ch = str.getBytes();
        DatagramPacket dp = new DatagramPacket(ch, ch.length);
        dp.setSocketAddress(new InetSocketAddress("127.0.0.1", 4567));
        // 发送数据
        ds.send(dp);
        // 关闭套接字
        ds.close();
    }

}
