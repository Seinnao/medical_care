#依赖的父镜像
FROM ascdc/jdk8
#作者
MAINTAINER whz
#jar包添加到镜像中
ADD medical-getaway-0.0.1-SNAPSHOT.jar app.jar
#容器暴露的端口 即jar程序在容器中运行的端口
EXPOSE 8030
#容器启动之后要执行的命令
ENTRYPOINT ["java","-jar","app.jar"]
