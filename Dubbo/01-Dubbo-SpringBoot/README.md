# Dubbo简单的使用

一个简单的入门 Demo，这里我采用 SpringBoot 的全注解方式，感觉 XML 的方式更麻烦一点

**代码地址**

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/Dubbo/01-Dubbo-SpringBoot](https://github.com/dolyw/ProjectStudy/tree/master/Dubbo/01-Dubbo-SpringBoot)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/Dubbo/01-Dubbo-SpringBoot](https://gitee.com/dolyw/ProjectStudy/tree/master/Dubbo/01-Dubbo-SpringBoot)

## 1. 前言

一般现在用于生产环境的 Dubbo 注册中心都是 ZooKeeper，那我们首先启动 ZooKeeper，安装可以查看: [ZooKeeper安装使用](00-ZooKeeper-Use.html)

## 2. 创建

我们先创建一个父工程

### 2.1. 父工程

打开 IDEA，File-New-Project...，选择 Maven 点击下一步 Next

groupId 我们填写我们的包名 com.demo，artifactId 填写我们的项目名 01-Dubbo-SpringBoot，version 先默认，点击下一步 Next

位置存在在 E:\XUE\ProjectStudy\Dubbo\01-Dubbo-SpringBoot，点击完成 Finish

创建完成，打开，可以把除 pom.xml 的其他文件都删除了，再新建一个 .gitignore 的 Git 忽略文件，IDEA 生成的文件删除了还是会生成，所以就不用删了

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191213001.png)

修改 pom.xml 的版本属性，写一个统一的版本号，子工程统一使用

```xml
<version>${revision}</version>

<properties>
    <revision>1.0.0</revision>
    <java.version>1.8</java.version>
    <dubbo.version>2.7.4.1</dubbo.version>
</properties>
```

### 2.2. API工程

这个主要是存放接口的，抽取出来，方便服务提供者和消费者直接使用，不需要两边建立重复的接口文件

我们在父工程下点击 File-New-Module，还是选择 Maven，点击下一步 Next，填写模块名 artifactId 为 common-api，点击下一步 Next，修改名称为 common-api，点击完成 Finish 即可

修改 common-api 工程 pom.xml 版本号 version 为 ${revision}，pom.xml如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <artifactId>01-Dubbo-SpringBoot</artifactId>
        <groupId>com.demo</groupId>
        <version>${revision}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <groupId>com.demo</groupId>
    <artifactId>common-api</artifactId>
    <packaging>jar</packaging>
    <version>${revision}</version>

    <name>common-api</name>
    <description>Dubbo Demo project</description>

</project>
```

并且创建一个服务接口 IDemoService

```java
package com.demo;

/**
 * IDemoService
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/12/18 16:37
 */
public interface IDemoService {

    /**
     * 说你好
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2019/12/20 10:11
     */
    String sayHello(String name);

}
```

### 2.3. Provider工程

这个是提供服务的工程，我们在父工程下点击 File-New-Module，这里选择 Spring Initializr(SpringBoot)，默认点击下一步 Next，填写模块名 artifactId 为 provider-service

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191220002.png)

点击下一步 Next，修改名称为 provider-service，点击完成 Finish 即可，然后修改当前模块的 pom 文件如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <artifactId>01-Dubbo-SpringBoot</artifactId>
        <groupId>com.demo</groupId>
        <version>${revision}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <groupId>com.demo</groupId>
    <artifactId>provider-service</artifactId>
    <packaging>jar</packaging>
    <version>${revision}</version>

    <name>provider-service</name>
    <description>Dubbo Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- Dubbo Spring Boot Starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.version}</version>
        </dependency>

        <!-- Zookeeper dependencies -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- common-api -->
        <dependency>
            <groupId>com.demo</groupId>
            <artifactId>common-api</artifactId>
            <version>${revision}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

Zookeeper 配置信息 application.yml

```yml
server:
  port: 8881

spring:
  application:
    name: provider-service

demo:
  service:
    version: 1.0

embedded:
  zookeeper:
    port: 2181

dubbo:
  application:
    name: ${spring.application.name}
  scan:
    # 扫描Service注解
    base-packages: com.demo.service
  protocol:
    name: dubbo
    port: -1
  registry:
    address: zookeeper://127.0.0.1:${embedded.zookeeper.port}
    file: ${user.home}/dubbo-cache/${spring.application.name}/dubbo.cache
```

实现一个服务接口 DemoServiceImpl，如果无法引入 IDemoService，就把 common-api 工程打包下

```java
package com.demo.service;

import com.demo.IDemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * DemoServiceImpl
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/12/20 10:10
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements IDemoService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        System.out.println(name + "连接成功");
        return serviceName + ": " + name;
    }

}
```

### 2.4. Consumer工程

这个是消费服务的工程，我们在父工程下点击 File-New-Module，这里选择 Spring Initializr(SpringBoot)，默认点击下一步 Next，填写模块名 artifactId 为 consumer-service

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191220003.png)

点击下一步 Next，修改名称为 consumer-service，点击完成 Finish 即可，然后修改当前模块的 pom 文件如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <artifactId>01-Dubbo-SpringBoot</artifactId>
        <groupId>com.demo</groupId>
        <version>${revision}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <groupId>com.demo</groupId>
    <artifactId>consumer-service</artifactId>
    <packaging>jar</packaging>
    <version>${revision}</version>

    <name>consumer-service</name>
    <description>Dubbo Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- Dubbo Spring Boot Starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.version}</version>
        </dependency>

        <!-- Zookeeper dependencies -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- common-api -->
        <dependency>
            <groupId>com.demo</groupId>
            <artifactId>common-api</artifactId>
            <version>${revision}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

Zookeeper 配置信息 application.yml

```yml
server:
  port: 8882

spring:
  application:
    name: consumer-service

demo:
  service:
    version: 1.0

embedded:
  zookeeper:
    port: 2181

dubbo:
  application:
    name: ${spring.application.name}
  registry:
    address: zookeeper://127.0.0.1:${embedded.zookeeper.port}
    file: ${user.home}/dubbo-cache/${spring.application.name}/dubbo.cache
```

我们写一个 DemoController 来调用服务

```java
package com.demo.controller;

import com.demo.IDemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/12/20 11:08
 */
@RestController
@RequestMapping
public class DemoController {

    @Reference(version = "${demo.service.version}")
    private IDemoService demoService;

    @GetMapping("/")
    public String sayHello() {
        String text = demoService.sayHello("Hello");
        System.out.println(text);
        return text;
    }

    @GetMapping("/demo")
    public String sayXXX(@RequestParam("name") String name) {
        String text = demoService.sayHello(name);
        System.out.println(text);
        return text;
    }

}
```

### 2.5. 聚合

把父工程的 pom 文件改下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.demo</groupId>
    <artifactId>01-Dubbo-SpringBoot</artifactId>
    <packaging>pom</packaging>
    <version>${revision}</version>

    <name>01-Dubbo-SpringBoot</name>
    <description>Dubbo Demo project</description>

    <properties>
        <revision>1.0.0</revision>
        <java.version>1.8</java.version>
        <dubbo.version>2.7.4.1</dubbo.version>
    </properties>

    <modules>
        <module>common-api</module>
        <module>provider-service</module>
    </modules>

</project>
```

## 3. 启动

先启动 Zookeeper，然后启动 provider-service 提供服务，再启动 consumer-service 调用服务，两个都是 SpringBoot 工程，可以直接启动，启动成功后可以直接调用 consumer-service 的两个 Controller 方法

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191220004.png)

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191220005.png)

后台打印

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191220006.png)

![图片](https://cdn.jsdelivr.net/gh/wliduo/CDN@master/2019/12/20191220007.png)

这样就调用成功了，Dubbo 简单的生产服务和消费服务就是这样

**参考**

* [dubbo-spring-boot-project](https://github.com/apache/dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples/dubbo-registry-zookeeper-samples)