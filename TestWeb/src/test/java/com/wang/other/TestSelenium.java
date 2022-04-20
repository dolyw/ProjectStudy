package com.wang.other;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * TestSelenium
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/10/29 16:54
 */
public class TestSelenium {

    @Test
    public void test01() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Tools\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        // String title = driver.getTitle();
        // System.out.printf(title);
        WebElement msgWebElement = driver.findElement(By.id("msg"));
        String base64 = msgWebElement.getText();
        while (true) {
            base64 = msgWebElement.getText();
            System.out.println(base64);
            if (StrUtil.isNotBlank(base64)) {
                break;
            }
        }
        String[] baseStr = base64.split(",");
        base64ToFile(baseStr[1], "xxx.pdf", "E:\\pdf\\");

        /*driver.get("https://www.baidu.com");
        driver.manage().window().setSize(new Dimension(480, 800));
        Thread.sleep(2000);*/

        // driver.quit();
        Thread.sleep(2000);
        driver.get("https://www.baidu.com");

        // driver.get("https://gitee.com/dolyw");
        Thread.sleep(2000);
        driver.quit();


        driver.close();
    }

    @Test
    public void test02() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Tools\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        /*chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-java");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-images");
        chromeOptions.addArguments("--disable-popup-blocking");*/
        // chromeOptions.addArguments("--single-process");
        // chromeOptions.addArguments("--disable-extensions");
        // 禁止默认浏览器检查
        // chromeOptions.addArguments("no-default-browser-check");
        // chromeOptions.addArguments("about:histograms");
        // chromeOptions.addArguments("about:cache");
        WebDriver driver = new ChromeDriver(chromeOptions);
        try {
            driver.get("https://www.areacodelocations.info/areacodelist.html");
            // String title = driver.getTitle();
            // System.out.printf(title);
            WebElement webElement =  driver.findElement(By.tagName("tbody"));
            List<WebElement> webElementList =  webElement.findElements(By.tagName("tr"));
            for (WebElement webElementTr : webElementList) {
                List<WebElement> webElementTDList =  webElementTr.findElements(By.tagName("td"));
                if (webElementTDList.size() > 0) {
                    if (webElementTDList.get(1).getText().indexOf("Canada") >= 0) {
                        if (!"-".equals(webElementTDList.get(2).getText())) {
                            System.out.println(webElementTDList.get(0).getText() + ":" + webElementTDList.get(1).getText() + ":" + webElementTDList.get(2).getText());
                        }
                    }
                }
            }
        } finally {
            driver.quit();
            driver.close();
        }
    }

    @Test
    public void test03() throws Exception{
        String fileName = "D:\\Program Files (x86)\\Reader_v1.9.3.2\\天命大反派.txt";
        if (!FileUtil.exist(fileName)) {
            File file = new File(fileName);
            file.createNewFile();
        } else {
            System.out.println("文件已经存在");
            return;
        }

        if (System.getProperties().getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "D:\\Tools\\chromedriver_win32\\chromedriver2.exe");
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-java");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-images");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--single-process");
        chromeOptions.addArguments("--disable-extensions");
        // 禁止默认浏览器检查
        chromeOptions.addArguments("no-default-browser-check");
        chromeOptions.addArguments("about:histograms");
        chromeOptions.addArguments("about:cache");

        WebDriver chromeDriver = new ChromeDriver(chromeOptions);

        TestSelenium.text(chromeDriver, "https://www.xs123.org/xs/33/33112/21404025.html", fileName);
        // TestSelenium.text(chromeDriver, "https://www.xs123.org/xs/33/33112/70349769.html", fileName);
        // TestSelenium.text(chromeDriver, "https://www.xs123.org/xs/33/33112/70963309.html", fileName);

        chromeDriver.close();
    }

    public static void text(WebDriver chromeDriver, String url, String fileName) throws Exception {
        chromeDriver.get(url);
        Thread.sleep(100);
        WebElement boxWebElement = chromeDriver.findElement(By.className("box_con"));
        WebElement titleElement = boxWebElement.findElement(By.tagName("h1"));

        if (titleElement.getText().contains("1030")) {
            return;
        }
        if ("玄幻：我！天命大反派".equals(titleElement.getText())) {
            return;
        }

        // 标题
        List<String> lines = new ArrayList<>();
        String title = "第" + titleElement.getText().substring(0,4).trim() + "章 " + titleElement.getText().substring(4).trim();
        title.replaceAll("/?", "");
        title.replaceAll("：", "");
        System.out.println(title);
        lines.add(title);

        // 正文
        WebElement conWebElement = chromeDriver.findElement(By.id("content"));
        String con = conWebElement.getText();
        con = con.replaceAll("<br/>", "");
        con = con.replaceAll("\n", "");
        con = con.replaceAll(",", "，");
        String[] conArray = con.split("。");
        for (String text : conArray) {
            if (text.length() > 50) {
                String[] conArray2 = text.split("，");
                for (int i = 0; i < conArray2.length; i++) {
                    if (i + 2 < conArray2.length) {
                        lines.add(conArray2[i] + "，" + conArray2[++i] + "，" + conArray2[++i] + "，");
                    } else if (i + 1 < conArray2.length) {
                        lines.add(conArray2[i] + "，" + conArray2[++i] + "，");
                    } else {
                        lines.add(conArray2[i] + "。");
                    }
                    lines.add("");
                }
                lines.add("");
            } else {
                lines.add(text + "。");
                lines.add("");
            }
        }

        FileUtil.appendUtf8Lines(lines, fileName);

        Thread.sleep(10);

        WebElement btnWebElement = chromeDriver.findElement(By.className("bottem2"));
        List<WebElement> btnListWebElement = btnWebElement.findElements(By.tagName("a"));

        // 递归调用
        TestSelenium.text(chromeDriver, btnListWebElement.get(2).getAttribute("href"), fileName);
    }

    public static void base64ToFile(String base64, String fileName, String savePath) {
        File file = null;
        String filePath = savePath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
