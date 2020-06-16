# 浅析分布式Session

> 分布式架构中 Session 的问题

在单体服务器的年代，Session 直接保存在服务器中，是没有问题的，而且实现起来很容易，但是随着分布式架构的流行，单个服务器已经不能满足系统的需要了，通常都会把系统部署在多台服务器上，通过负载均衡把请求分发到其中的一台服务器上，那么很有可能第一次请求访问的 A 服务器，创建了 Session ，但是第二次访问到了 B 服务器，这时就会出现取不到 Session 的情况，于是，分布式架构中，Session 共享就成了一个很大的问题

比如集群中存在 A、B 两台服务器，用户在第一次访问网站时，Nginx 通过其负载均衡机制将用户请求转发到 A 服务器，这时 A 服务器就会给用户创建一个 Session。当用户第二次发送请求时，Nginx 将其负载均衡到 B 服务器，而这时候B服务器并不存在 Session，所以就会将用户踢到登录页面。这将大大降低用户体验度，导致用户的流失，这种情况是项目绝不应该出现的

我们应当对产生的 Session 进行处理，通过 **Session 复制(同步)，Cookie 保存，Session 绑定 或 Session 共享**等方式保证用户的体验度，当然也可以使用**无状态认证机制，JWT 来处理**，有兴趣的查看: [ShiroJwt](https://github.com/dolyw/ShiroJwt)

## 1. Session复制

Session 复制是早期企业应用系统使用比较多的一种服务器集群 Session 管理机制。应用服务器开启 Web 容器的的 Session 复制功能，在集群中的几台服务器之间同步 Session 对象，每台服务器上都保存所有用户的 Session 信息，这样任何一台机器宕机都不会导致 Session 数据的丢失，而服务器使用 Session 时候，也只需要在本机获取即可

### 1.1. 优缺点

- 优点
    - 配置相对简单，且从本机读取 Session 也相当快捷
- 缺点
    - 只能使用在集群规模比较小的情况下，当集群规模比较大的时候，集群服务器之间需要大量的通信进行 Session 的复制，占用服务器和网络的大量资源，系统负担较大，还会存在延迟甚至同步失败

### 1.2. 实现方式

在 Tomcat 安装目录下的 config 目录中的 server.xml 文件中，将注释打开，Tomcat 必须在同一个网关内，要不然收不到广播，同步不了Session，在 web.xml 中开启 Session 复制: `<distributable/>`

### 1.3. 总结

在小规模集群下，用户并发量不大的情况可以采用，当集群规模比较大的时候，不推荐

## 2. Cookie保存

将 Session 存储到 Cookie 中，但是缺点也很明显

- Cookie 的大小类型存在限制
- 每次请求都得带着 Session 影响性能，给网络增大开销
- 数据存储在客户端本地，Cookie 可被修改或者存在破解的可能

## 3. Session绑定

使用 IP 绑定策略，也叫 Session 会话保持(黏滞会话)

指将用户锁定到某一个服务器上，比如上面说的例子，用户第一次请求时，负载均衡器将用户的请求转发到了 A 服务器上，如果负载均衡器设置了 Session 绑定的话，那么用户以后的每次请求都会转发到 A 服务器上，相当于把用户和 A 服务器绑定到了一块，无论发送多少次请求都被同一个服务器处理，但是这样做失去了负载均衡的意义

### 3.1. 优缺点

- 优点
    - 配置相对简单，不需要对 Session 做任何处理，集群规模比较大，大量用户访问的情况也能支持
- 缺点
    - 容易造成单点故障，如果有一台服务器宕机，那么该台服务器上的 Session 信息将会丢失
    - 前端不能有负载均衡，如果有，Session 绑定将会出问题

### 3.2. 实现方式

Nginx 实现

```conf
upstream aaa {
	# Ip_hash;
    ip_hash;
	server xx.xxx.xxx.xx:8080;
	Server xx.xxx.xxx.xx:8081;
}

server {
	listen 80;
	server_name www.xxx.com;
	#root /usr/local/nginx/html;
	#index index.html index.htm;
	location / {
		proxy_pass http: xx.xxx.xxx.xx;
		index index.html index.htm;
	}
}
```

### 3.3. 总结

虽然保证了每个用户都能准确的拿到自己的 Session，而且大量用户访问也不怕，但是这种会话保持不符合系统高可用的需求。这种方案有着致命的缺陷：一旦某台服务器发生宕机，则该服务器上的所有 Session 信息就会不存在，用户请求就会切换到其他服务器，而其他服务器因为没有其对应的 Session 信息导致无法完成相关业务。所以这种方法基本上不会被采纳

## 4. Session共享

使用分布式缓存方案比如 Memcached、Redis，但是要求 Memcached 或 Redis 必须是集群保证高可用，(Terracotta)，也可以持久化到数据库，不过推荐分布式缓存缓存，现在采用最多的都是这种方案

- 优点
    - 实现了 Session 共享
    - 服务器重启 Session 不丢失，不过也要注意 Session 在 Redis 中的刷新/失效机制
    - 不仅可以跨服务器 Session 共享，甚至可以跨平台，例如网页端和 APP 端
- 缺点
    - 多了一次网络调用，Web 容器需要向缓存访问
    - Session依赖缓存

## 5. Redis实现

Redis 实现 Session共享

**代码地址**

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionRedis](https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionRedis)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionRedis](https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionRedis)

### 5.1. Config

* pom.xml
```xml
<!-- Redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- Spring Session Redis -->
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

* application.yml
```yml
server:
  port: 8888

spring:
  redis:
    host: 127.0.0.1
    port: 6379
  session:
    # Spring Session使用存储类型
    # SpirngBoot默认就是使用Redis方式，如果不想用可以填none
    store-type: redis
```

* 在启动类中加入`@EnableRedisHttpSession`注解
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Spring Session使用
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 11:52
 */
@EnableRedisHttpSession
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### 5.2. Controller

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:46
 */
@RestController
@RequestMapping("/")
public class SessionController {

    /**
     * 测试Session共享
     *
     * @param request
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/15 18:15
     */
    @GetMapping(value = "/session")
    public String getSession(HttpServletRequest request) {
        String msg = "";
        HttpSession session = request.getSession();
        if (session.getAttribute("msg") != null) {
            return session.getAttribute("msg").toString();
        } else {
            session.setAttribute("msg", "Hello");
        }
        return msg;
    }

}
```

### 5.3. Run

本地的 Redis 服务这里就不说了，启动服务，调用方法会发现 Redis 缓存了 Session 数据，可以启动多个端口，测试，还可以重启应用，可以发现 Session 里的属性值也还存在

## 6. MySQL实现

MySQL 实现 Session共享

**代码地址**

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionMySql](https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionMySql)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionMySql](https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/SessionMySql)

### 6.1. Config

* SQL可以自己创建，现在是会默认启动创建
```sql
DROP TABLE IF EXISTS SPRING_SESSION_ATTRIBUTES;
DROP TABLE IF EXISTS SPRING_SESSION;
CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
```

* pom.xml
```xml
<!-- SpringBoot JDBC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<!-- MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<!-- Spring Session JDBC -->
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-jdbc</artifactId>
</dependency>
```

* application.yml
```yml
server:
  port: 8889
  servlet:
    session:
      # Session超时时间
      timeout: 30m

spring:
  session:
    # Spring Session使用存储类型
    # 使用jdbc，如果不想用可以填none
    store-type: jdbc
    jdbc:
      # 初始化数据库schema的SQL脚本
      schema: classpath:org/springframework/session/jdbc/schema-mysql.sql
      # 用于存储会话的数据库表名
      table-name: SPRING_SESSION
      # 如果有需要，在启动时可创建必要的Session表。如果默认的表名已经配置或者个性化模式中已经配置，则自动启动
      initialize-schema: always
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/dev?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
    username: root
    password: root
```

* 在启动类中加入`@EnableJdbcHttpSession`注解
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * Spring Session使用
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 11:52
 */
@EnableJdbcHttpSession
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### 6.2. Controller

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:46
 */
@RestController
@RequestMapping("/")
public class SessionController {

    /**
     * 测试Session共享
     *
     * @param request
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/15 18:15
     */
    @GetMapping(value = "/session")
    public String getSession(HttpServletRequest request) {
        String msg = "";
        HttpSession session = request.getSession();
        if (session.getAttribute("msg") != null) {
            return session.getAttribute("msg").toString();
        } else {
            session.setAttribute("msg", "Hello");
        }
        return msg;
    }

}
```

### 6.3. Run

本地的 MySQL 服务这里就不说了，启动服务，调用方法会发现 MySQL 表插入了 Session 数据，可以启动多个端口，测试，还可以重启应用，可以发现 Session 里的属性值也还存在