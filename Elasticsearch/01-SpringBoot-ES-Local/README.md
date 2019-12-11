# 01-SpringBoot-ES-Local

> 目录:[https://github.com/dolyw/Elasticsearch](https://github.com/dolyw/Elasticsearch)

#### 软件架构

1. SpringBoot + REST Client

#### 项目介绍

SpringBoot整合ES的方式(TransportClient、Data-ES、Elasticsearch SQL、REST Client)

* **TransportClient**

TransportClient即将弃用，所以这种方式不考虑

* **Data-ES**

Spring提供的封装的方式，好像底层也是基于TransportClient，Elasticsearch7.0后的版本不怎么支持，SpringBoot的Spring Boot Data Elasticsearch Starter最高版本2.1.7.RELEASE下载的是Spring Data Elasticsearch的3.1.5版本对应的是Elasticsearch 6.4.3版本，Spring Data Elasticsearch最新版3.1.10对应的还是Elasticsearch 6.4.3版本，我安装的是最新的Elasticsearch 7.2.0版本所以也没办法使用

* **Elasticsearch SQL**

将Elasticsearch的`Query DSL`用`SQL`转换查询，早期有一个第三方的插件Elasticsearch-SQL，后来随着官方也开始做这方面，这个插件好像就没怎么更新了，有兴趣的可以查看：[https://www.cnblogs.com/jajian/p/10053504.html](https://www.cnblogs.com/jajian/p/10053504.html)

* **REST Client**

官方推荐使用，所以我们采用这个方式，这个分为两个**Low Level REST Client**和**High Level REST Client**，**Low Level REST Client**是早期出的API比较简陋了，还需要自己去拼写`Query DSL`，**High Level REST Client**使用起来更好用，更符合面向对象的感觉，两个都使用下吧

#### 预览图示

* 查询

![查询](https://docs.dolyw.com/Project/Elasticsearch/image/20190815001.gif)

* 添加

![添加](https://docs.dolyw.com/Project/Elasticsearch/image/20190815002.gif)

* 修改

![修改](https://docs.dolyw.com/Project/Elasticsearch/image/20190815003.gif)

* 删除

![删除](https://docs.dolyw.com/Project/Elasticsearch/image/20190815004.gif)

#### 配置代码

创建一个`SpringBoot 2.1.3`的`Maven`项目，这块不再详细描述，添加如下`REST Client`依赖

```xml
<elasticsearch.version>7.2.0</elasticsearch.version>

<!-- Java Low Level REST Client -->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-client</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>

<!-- Java High Level REST Client -->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-high-level-client</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```

* 配置文件

```yml
server:
    port: 8080

spring:
    thymeleaf:
        # 开发时关闭缓存不然没法看到实时页面
        cache: false
        # 启用不严格检查
        mode: LEGACYHTML5

# Elasticsearch配置
elasticsearch:
    hostname: 127.0.0.1
    port: 9500
```

```java
@Configuration
public class RestClientConfig {

    @Value("${elasticsearch.hostname}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private int port;

    /**
     * LowLevelRestConfig
     *
     * @param
     * @return org.elasticsearch.client.RestClient
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/12 18:56
     */
    @Bean
    public RestClient restClient() {
        // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是IP，参数2是端口，参数3是通信协议
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(hostname, port, "http"));
        // 设置Header编码
        Header[] defaultHeaders = {new BasicHeader("content-type", "application/json")};
        clientBuilder.setDefaultHeaders(defaultHeaders);
        // 添加其他配置，这些配置都是可选的，详情配置可看https://blog.csdn.net/jacksonary/article/details/82729556
        return clientBuilder.build();
    }

    /**
     * HighLevelRestConfig
     *
     * @param
     * @return org.elasticsearch.client.RestClient
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/12 18:56
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是IP，参数2是端口，参数3是通信协议
        return new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, "http")));
    }

}
```

* 这样就配置完成了

#### Controller入口

* LowLevelRestController

```java
@RestController
@RequestMapping("/low")
public class LowLevelRestController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LowLevelRestController.class);

    /**
     * PATTERN
     */
    private static Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    @Autowired
    private RestClient restClient;

    /**
     * 同步执行HTTP请求
     *
     * @param
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/8 17:15
     */
    @GetMapping("/es")
    public ResponseBean getEsInfo() throws IOException {
        Request request = new Request("GET", "/");
        // performRequest是同步的，将阻塞调用线程并在请求成功时返回Response，如果失败则抛出异常
        Response response = restClient.performRequest(request);
        // 获取请求行
        RequestLine requestLine = response.getRequestLine();
        // 获取host
        HttpHost host = response.getHost();
        // 获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        // 获取响应头
        Header[] headers = response.getHeaders();
        // 获取响应体
        String responseBody = EntityUtils.toString(response.getEntity());
        return new ResponseBean(HttpStatus.OK.value(), "查询成功", JSON.parseObject(responseBody));
    }


    /**
     * 异步执行HTTP请求
     *
     * @param
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/8 17:15
     */
    @GetMapping("/es/async")
    public ResponseBean asynchronous() {
        Request request = new Request("GET", "/");
        restClient.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                logger.info("异步执行HTTP请求并成功");
            }

            @Override
            public void onFailure(Exception exception) {
                logger.info("异步执行HTTP请求并失败");
            }
        });
        return new ResponseBean(HttpStatus.OK.value(), "异步请求中", null);
    }

    /**
     * 分词分页查询列表
     *
     * @param page
	 * @param rows
	 * @param keyword
     * @return com.example.common.ResponseBean
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/9 15:32
     */
    @GetMapping("/book")
    public ResponseBean getBookList(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer rows,
                                    String keyword) {
        Request request = new Request("POST", new StringBuilder("/_search").toString());
        // 添加Json返回优化
        request.addParameter("pretty", "true");
        // 拼接查询Json
        IndexRequest indexRequest = new IndexRequest();
        XContentBuilder builder = null;
        Response response = null;
        String responseBody = null;
        try {
            builder = JsonXContent.contentBuilder()
                    .startObject()
                    .startObject("query")
                    .startObject("multi_match")
                    .field("query", keyword)
                    .array("fields", new String[]{"name", "desc"})
                    .endObject()
                    .endObject()
                    .startObject("sort")
                    .startObject("id")
                    .field("order", "desc")
                    .endObject()
                    .endObject()
                    .endObject();
            indexRequest.source(builder);
            // 设置请求体并指定ContentType，如果不指定会乱码
            request.setEntity(new NStringEntity(indexRequest.source().utf8ToString(), ContentType.APPLICATION_JSON));
            // 执行HTTP请求
            response = restClient.performRequest(request);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            return new ResponseBean(HttpStatus.NOT_FOUND.value(), "can not found the book by your id", null);
        }
        return new ResponseBean(HttpStatus.OK.value(), "查询成功", JSON.parseObject(responseBody));
    }

    /**
     * 根据Id获取ES对象
     *
     * @param id
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/8 17:48
     */
    @GetMapping("/book/{id}")
    public ResponseBean getBookById(@PathVariable("id") String id) {
        Request request = new Request("GET", new StringBuilder("/book/book/")
                .append(id).toString());
        // 添加Json返回优化
        request.addParameter("pretty", "true");
        Response response = null;
        String responseBody = null;
        try {
            // 执行HTTP请求
            response = restClient.performRequest(request);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            return new ResponseBean(HttpStatus.NOT_FOUND.value(), "can not found the book by your id", null);
        }
        return new ResponseBean(HttpStatus.OK.value(), "查询成功", JSON.parseObject(responseBody));
    }

    /**
     * 添加ES对象, Book的ID就是ES中存储的Document的ID，ES的POST和PUT可以看下面这个文章
     * https://blog.csdn.net/z457181562/article/details/93470152
     *
     * @param bookDto
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/8 17:46
     */
    @PostMapping("/book")
    public ResponseBean add(@RequestBody BookDto bookDto) throws IOException {
        // Endpoint直接指定为Index/Type的形式
        /*Request request = new Request("POST", new StringBuilder("/book/book/").toString());*/
        // 防重复新增数据
        bookDto.setId(System.currentTimeMillis());
        Request request = new Request("PUT", new StringBuilder("/book/book/")
                .append(bookDto.getId()).append("/_create").toString());
        // 设置其他一些参数比如美化Json
        request.addParameter("pretty", "true");
        // 设置请求体并指定ContentType，如果不指定会乱码
        request.setEntity(new NStringEntity(JSONObject.toJSONString(bookDto), ContentType.APPLICATION_JSON));
        // 发送HTTP请求
        Response response = restClient.performRequest(request);
        // 获取响应体
        String responseBody = EntityUtils.toString(response.getEntity());
        return new ResponseBean(HttpStatus.OK.value(), "添加成功", JSON.parseObject(responseBody));
    }

    /**
     * 根据Id更新Book，ES的POST和PUT可以看下面这个文章
     *
     * https://blog.csdn.net/z457181562/article/details/93470152
     * @param bookDto
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/9 10:04
     */
    @PutMapping("/book")
    public ResponseBean update(@RequestBody BookDto bookDto) throws IOException {
        // 构造HTTP请求
        /*Request request = new Request("POST", new StringBuilder("/book/book/")
                .append(bookDto.getId()).append("/_update").toString());*/
        Request request = new Request("PUT", new StringBuilder("/book/book/")
                .append(bookDto.getId()).toString());
        // 设置其他一些参数比如美化Json
        request.addParameter("pretty", "true");
        /*// 将数据丢进去，这里一定要外包一层'doc'，否则内部不能识别
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doc", new JSONObject(bookDto));*/
        // 设置请求体并指定ContentType，如果不指定会乱码
        request.setEntity(new NStringEntity(JSONObject.toJSONString(bookDto), ContentType.APPLICATION_JSON));
        // 执行HTTP请求
        Response response = restClient.performRequest(request);
        // 获取返回的内容
        String responseBody = EntityUtils.toString(response.getEntity());
        return new ResponseBean(HttpStatus.OK.value(), "更新成功", JSON.parseObject(responseBody));
    }

    /**
     * 使用脚本更新Name
     *
     * @param id
	 * @param bookDto
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/9 11:37
     */
    @PutMapping("/book/{id}")
    public ResponseEntity<String> update2(@PathVariable("id") String id, @RequestBody BookDto bookDto) throws IOException {
        // 构造HTTP请求
        Request request = new Request("POST", new StringBuilder("/book/book/")
                .append(id).append("/_update").toString());
        // 设置其他一些参数比如美化Json
        request.addParameter("pretty", "true");
        JSONObject jsonObject = new JSONObject();
        // 创建脚本语言，如果是字符变量，必须加单引号
        StringBuilder op1 = new StringBuilder("ctx._source.name=").append("'" + bookDto.getName() + "'");
        jsonObject.put("script", op1);
        request.setEntity(new NStringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
        // 执行HTTP请求
        Response response = restClient.performRequest(request);
        // 获取返回的内容
        String responseBody = EntityUtils.toString(response.getEntity());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/8 17:54
     */
    @DeleteMapping("/book/{id}")
    public ResponseBean deleteById(@PathVariable("id") String id) throws IOException {
        Request request = new Request("DELETE", new StringBuilder("/book/book/")
                .append(id).toString());
        request.addParameter("pretty", "true");
        // 执行HTTP请求
        Response response = restClient.performRequest(request);
        // 获取结果
        String responseBody = EntityUtils.toString(response.getEntity());
        return new ResponseBean(HttpStatus.OK.value(), "删除成功", JSON.parseObject(responseBody));
    }
}
```

* HighLevelRestController

```java
@RestController
@RequestMapping("/high")
public class HighLevelRestController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(HighLevelRestController.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 获取ES信息
     *
     * @param
     * @return com.example.common.ResponseBean
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/14 17:11
     */
    @GetMapping("/es")
    public ResponseBean getEsInfo() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        // 查询ES
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return new ResponseBean(HttpStatus.OK.value(), "查询成功", searchResponse);
    }

    /**
     * 列表查询
     *
     * @param page
     * @param rows
     * @param keyword
     * @return com.example.common.ResponseBean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/15 16:01
     */
    @GetMapping("/book")
    public ResponseBean list(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer rows,
                             String keyword) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页采用简单的from + size分页，适用数据量小的，了解更多分页方式可自行查阅资料
        searchSourceBuilder.from((page - 1) * rows);
        searchSourceBuilder.size(rows);
        // 查询条件，只有查询关键字不为空才带查询条件
        if (StringUtils.isNoneBlank(keyword)) {
            QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "name", "desc");
            searchSourceBuilder.query(queryBuilder);
        }
        // 排序，根据ID倒叙
        searchSourceBuilder.sort("id", SortOrder.DESC);
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        // 查询ES
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        // 获取总数
        Long total = hits.getTotalHits().value;
        // 遍历封装列表对象
        List<BookDto> bookDtoList = new ArrayList<>();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            bookDtoList.add(JSON.parseObject(searchHit.getSourceAsString(), BookDto.class));
        }
        // 封装Map参数返回
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("count", total);
        result.put("data", bookDtoList);
        return new ResponseBean(HttpStatus.OK.value(), "查询成功", result);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return com.example.common.ResponseBean
     * @throws IOException
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/15 14:10
     */
    @GetMapping("/book/{id}")
    public ResponseBean getById(@PathVariable("id") String id) throws IOException {
        // GetRequest
        GetRequest getRequest = new GetRequest(Constant.INDEX, id);
        // 查询ES
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        BookDto bookDto = JSON.parseObject(getResponse.getSourceAsString(), BookDto.class);
        return new ResponseBean(HttpStatus.OK.value(), "查询成功", bookDto);
    }

    /**
     * 添加文档
     *
     * @param bookDto
     * @return com.example.common.ResponseBean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/15 16:01
     */
    @PostMapping("/book")
    public ResponseBean add(@RequestBody BookDto bookDto) throws IOException {
        // IndexRequest
        IndexRequest indexRequest = new IndexRequest(Constant.INDEX);
        Long id = System.currentTimeMillis();
        bookDto.setId(id);
        String source = JSON.toJSONString(bookDto);
        indexRequest.id(id.toString()).source(source, XContentType.JSON);
        // 操作ES
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return new ResponseBean(HttpStatus.OK.value(), "添加成功", indexResponse);
    }

    /**
     * 修改文档
     *
     * @param bookDto
     * @return com.example.common.ResponseBean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/15 16:02
     */
    @PutMapping("/book")
    public ResponseBean update(@RequestBody BookDto bookDto) throws IOException {
        // UpdateRequest
        UpdateRequest updateRequest = new UpdateRequest(Constant.INDEX, bookDto.getId().toString());
        updateRequest.doc(JSON.toJSONString(bookDto), XContentType.JSON);
        // 操作ES
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return new ResponseBean(HttpStatus.OK.value(), "修改成功", updateResponse);
    }

    /**
     * 删除文档
     *
     * @param id
     * @return com.example.common.ResponseBean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/15 16:02
     */
    @DeleteMapping("/book/{id}")
    public ResponseBean deleteById(@PathVariable("id") String id) throws IOException {
        // DeleteRequest
        DeleteRequest deleteRequest = new DeleteRequest(Constant.INDEX, id);
        // 操作ES
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return new ResponseBean(HttpStatus.OK.value(), "删除成功", deleteResponse);
    }

}
```

**LowLevelRestController**和**HighLevelRestController**一对比就能看出来了，**HighLevelRestController**使用起来更舒服

接口都实现了，实际请查看代码，最后用`Vue` + `ElementUI`写了一个前端界面

#### 前端界面

* 界面实现网页common.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head th:fragment="headVue(title)">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:text="${title}">加载中</title>
    <link rel="shortcut icon" th:href="@{/favicon.ico}" type="image/x-icon"/>
    <link rel="stylesheet" th:href="@{element-ui/index.css}">
    <style>
        /* elementUI的确认弹出框时页面右侧缩小5px的解决方法 */
        body {
            padding-right:0 !important;
        }
        /* elementUI的Table表头错位的解决方法 */
        body .el-table th.gutter {
            display: table-cell!important;
        }
        body .el-table colgroup.gutter {
            display: table-cell!important;
        }
        label {
            font-weight: 700;
        }
    </style>
    <!-- 引入Vue，Element UI，Axios，Moment -->
    <script th:src="@{js/vue.min.js}"></script>
    <script th:src="@{element-ui/index.js}"></script>
    <script th:src="@{js/axios.min.js}"></script>
    <script th:src="@{js/moment.min.js}"></script>
</head>

</html>
```

* 界面实现网页index.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head th:include="/common/common :: headVue('随心')"></head>

<style>

	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}
	.clearfix:after {
		clear: both
	}

	/* 谷歌浏览器滚动条美化 */
	::-webkit-scrollbar {
		width: 15px;
		height: 15px;
	}

	::-webkit-scrollbar-track,
	::-webkit-scrollbar-thumb {
		border-radius: 999px;
		border: 5px solid transparent;
	}

	::-webkit-scrollbar-track {
		box-shadow: 1px 1px 5px rgba(143, 143, 143, 0.2) inset;
	}

	::-webkit-scrollbar-thumb {
		min-height: 20px;
		background-clip: content-box;
		box-shadow: 0 0 0 5px rgba(143, 143, 143, 0.466) inset;
	}

	::-webkit-scrollbar-corner {
		background: transparent;
	}

</style>

<body>

<div id="app">

	<el-card>

		<div slot="header" class="clearfix">
			<span>Elasticsearch的CURD</span>
		</div>

		<!-- 查询表单 -->
		<el-form :inline="true" :model="searchForm" label-width="90px" size="small">

			<el-form-item>
				<el-input placeholder="请输入查询关键字" @keyup.enter.native="list(searchForm)" v-model="searchForm.keyword" clearable></el-input>
				<el-input v-show="false" placeholder="请输入查询关键字" @keyup.enter.native="list(searchForm)" v-model="searchForm.keyword" clearable></el-input>
			</el-form-item>

			<el-form-item>
				<el-button icon="el-icon-search" plain @click="list(searchForm)">查询</el-button>
			</el-form-item>

			<el-form-item>
				<el-button type="info" plain icon="el-icon-plus" @click="preAdd" :loading="genLoading">添加</el-button>
			</el-form-item>

		</el-form>

		<!-- 数据表格 -->
		<el-table v-loading="tableLoading" :data="tableData" @selection-change="handleSelectionChange" border>
			<!--<el-table-column type="selection" align="center" width="55"></el-table-column>-->
			<el-table-column type="index" align="center" min-width="60"></el-table-column>
			<el-table-column prop="id" label="ID" align="center" min-width="100" show-overflow-tooltip></el-table-column>
			<el-table-column prop="name" label="名称" align="center" min-width="100" show-overflow-tooltip></el-table-column>
			<el-table-column prop="desc" label="描述" align="center" min-width="80" show-overflow-tooltip></el-table-column>
			<!--<el-table-column prop="createTime" label="创建时间" align="center" min-width="100" show-overflow-tooltip>
				<template slot-scope="scope">
					{{ moment(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}
				</template>
			</el-table-column>-->
			<el-table-column label="操作" align="center" fixed="right" min-width="60">
				<template slot-scope="scope">
					<el-button size="mini" icon="el-icon-edit" type="primary" plain @click="preById(scope.row)" :loading="genLoading">修改</el-button>
					<el-button size="mini" icon="el-icon-delete" type="danger" plain @click="delById(scope.row)" :loading="genLoading">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<div align="right" style="margin-top: 20px;">
			<el-pagination
				:current-page="searchForm.page"
				:page-sizes="[1, 8, 16, 32, 48]"
				:page-size="searchForm.rows"
				:total="totalCount"
				layout="total, sizes, prev, pager, next, jumper"
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
			/>
		</div>
	</el-card>

	<el-dialog :title="title" :visible.sync="dialogVisible" width="40%">
		<el-form :model="bookDto" label-width="90px" style="width: 520px;">
			<el-form-item label="ID">
				<el-input v-model="bookDto.id" disabled="true" autocomplete="off" placeholder="ID自动生成"></el-input>
			</el-form-item>
			<el-form-item label="名称">
				<el-input v-model="bookDto.name" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="描述">
				<el-input v-model="bookDto.desc" autocomplete="off"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button :loading="genLoading" @click="dialogVisible = false">取 消</el-button>
			<el-button type="primary" :loading="genLoading" @click="deal">确 定</el-button>
		</div>
	</el-dialog>

</div>

<script type="text/javascript" th:inline="javascript">
    /*<![CDATA[*/
    var myApp = new Vue({
        el: '#app',
        data: {
            // 表格加载条控制
            tableLoading: false,
            // 按钮加载条控制
            genLoading: false,
            // Table数据
            tableData: [],
            // Table数据总条数
            totalCount: 0,
            // Table选择的数据
            multipleSelection: [],
            // 查询条件
            searchForm: {
                // 当前页
                page: 1,
                // 每页条数
                rows: 8,
                // 查询关键字
                keyword: ''
            },
            // 表详细弹出框标题
            title: '添加',
            // 表详细弹出框是否显示
            dialogVisible: false,
            // 操作对象
            bookDto: {
                // ID
                id: 1,
                // 名称
                name: '',
                // 描述
                desc: ''
            }
        },
        // 启动时就执行
        mounted: function() {
            // ES信息查询
            // this.queryES();
            // 列表查询
            this.list(this.searchForm);
        },
        methods: {
            // 查询ES信息
            queryES: function() {
                axios.get('/high/es').then(res => {
                    console.log(res);
                }).catch(err => {
                    console.log(err);
                    this.$message.error('查询失败');
                });
            },
            // 每页条数改变
            handleSizeChange: function(rows) {
                this.searchForm.rows = rows;
                // console.log(this.searchForm.rows);
                // 刷新列表
                this.list(this.searchForm);
            },
            // 当前页数改变
            handleCurrentChange: function(page) {
                this.searchForm.page = page;
                // 刷新列表
                this.list(this.searchForm);
            },
            // 选择数据改变触发事件
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 列表查询
            list: function(searchForm) {
                // 加载显示
                this.tableLoading = true;
                axios.get('/high/book', {
                    params: {
                        'page': this.searchForm.page,
                        'rows': this.searchForm.rows,
                        'keyword': this.searchForm.keyword
                    }
                }).then(res => {
                    // console.log(res);
                    var data = res.data.data;
                    this.tableData = data.data;
                    this.totalCount = data.count;
                }).catch(err => {
                    console.log(err);
                    this.$message.error('查询失败');
                }).then(() => {
                    this.tableLoading = false;
            	});
            },
            // 添加
            preAdd: function() {
                this.genLoading = true;
                // this.$nextTick Dom渲染完执行
                this.$nextTick(() => {
                    this.title = "添加";
                    this.bookDto = {};
                    this.dialogVisible = true;
                    this.genLoading = false;
                });
            },
            // 预修改
            preById: function(row) {
                this.genLoading = true;
                this.title = "修改";
                this.dialogVisible = true;
                axios.get('/high/book/' + row.id).then(res => {
                    // console.log(res);
                    this.bookDto = res.data.data;
                }).catch(err => {
                    console.log(err);
                    this.$message.error('查询失败');
                }).then(() => {
                    this.genLoading = false;
                });
            },
            // 添加或者修改
            deal: function() {
                this.genLoading = true;
                if (this.bookDto.id) {
                    // ID存在修改
                    axios.put('/high/book', this.bookDto).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: res.data.msg,
                                type: 'success'
                            });
                            this.dialogVisible = false;
                            // 列表查询必须慢点，ES没有事务性，查询太快，数据还没更新
                            this.tableLoading = true;
                            setTimeout(() => {this.list(this.searchForm);}, 1000);
                        } else {
                            this.$message.error('修改失败');
                        }
                    }).catch(err => {
                        console.log(err);
                        this.$message.error('修改失败');
                    }).then(() => {
                        this.genLoading = false;
                    });
                } else {
                    // ID不存在添加
                    axios.post('/high/book', this.bookDto).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: res.data.msg,
                                type: 'success'
                            });
                            this.dialogVisible = false;
                            // 列表查询必须慢点，ES没有事务性，查询太快，数据还没更新
                            this.tableLoading = true;
                            setTimeout(() => {this.list(this.searchForm);}, 1000);
                        } else {
                            this.$message.error('添加失败');
                        }
                    }).catch(err => {
                        console.log(err);
                        this.$message.error('添加失败');
                    }).then(() => {
                        this.genLoading = false;
                    });
                }
            },
            // 删除
            delById: function (row) {
                this.genLoading = true;
                this.$confirm('是否确定删除', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.delete('/high/book/' + row.id).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: res.data.msg, 
                                type: 'success'
                            });
                            // 列表查询必须慢点，ES没有事务性，查询太快，数据还没更新
                            this.tableLoading = true;
                            setTimeout(() => {this.list(this.searchForm);}, 1000);
                        } else {
                            this.$message.error('删除失败');
                        }
                    }).catch(err => {
                        console.log(err);
                        this.$message.error('删除失败');
                    }).then(() => {
                        this.genLoading = false;
                    });
                }).catch(() => {
                    this.genLoading = false;
                });
            }
        }
    });
    /*]]>*/
</script>

</body>

</html>
```

#### 安装教程

运行项目src\main\java\com\example\Application.java即可，访问[http://localhost:8080](http://localhost:8080)即可

#### 搭建参考

1. 感谢MaxZing的在SpringBoot整合Elasticsearch的Java Rest Client:[https://www.jianshu.com/p/0b4f5e41405e](https://www.jianshu.com/p/0b4f5e41405e)
2. 感谢jacksonary的SpringBoot整合ES的三种方式（API、REST Client、Data-ES）:[https://blog.csdn.net/jacksonary/article/details/82729556](https://blog.csdn.net/jacksonary/article/details/82729556)
3. 感谢青青天空树的springboot整合elasticsearch7.2(基于官方high level client):[https://cloud.tencent.com/developer/article/1478267](https://cloud.tencent.com/developer/article/1478267)
4. 感谢zhyingke的elasticsearch7 基本语法(基于官方high level client):[https://blog.csdn.net/z457181562/article/details/93470152](https://blog.csdn.net/z457181562/article/details/93470152)
5. 感谢wangzhen3798的ElasticSearch中如何进行排序:[https://blog.csdn.net/wangzhen3798/article/details/83582474](https://blog.csdn.net/wangzhen3798/article/details/83582474)
6. 感谢WeirdLang的ElasticSearch RestHighLevelClient 通用操作:[https://www.cnblogs.com/WeidLang/p/10245659.html](https://www.cnblogs.com/WeidLang/p/10245659.html)