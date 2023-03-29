FROM adoptopenjdk/openjdk8-openj9:alpine-slim

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/medical-chat.jar app.jar

# 这样写会以shell方式执行，会替换变量
ENV JAVA_OPTS="-server -Xms128m -Xmx128m"

#启动容器时的进程
ENTRYPOINT java ${JAVA_OPTS} -Xshareclasses -Xquickstar -jar ./app.jar

#暴露端口
EXPOSE 8032