FROM tomcat:8.5.31-jre8
#工作目录
WORKDIR /usr/local/tomcat/webapps/
#删除tomcat默认文件
#RUN rm -rf *
#添加自己的war
ADD TestWeb.war .

#Expose http port
EXPOSE 8080
#启动命令
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]