FROM java:8
VOLUME /tmp
# Maintainer
MAINTAINER "Joel Holmes <Holmes89@gmail.com>"
ADD target/sacred-text-api*.jar sacred-text-api.jar
RUN bash -c 'touch /religious-calendar-api.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sacred-text-api.jar"]