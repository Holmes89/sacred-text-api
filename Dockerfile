FROM java:8
VOLUME /tmp
# Maintainer
MAINTAINER "Joel Holmes <Holmes89@gmail.com>"
ADD target/sacred-text-api*.jar sacred-text-api.jar
RUN bash -c 'touch /religious-calendar-api.jar'
RUN mkdir -p /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sacred-text-api.jar"]