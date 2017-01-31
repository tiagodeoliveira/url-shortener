FROM java:8

ADD src /data/src
ADD gradle /data/gradle
COPY settings.gradle /data/settings.gradle
COPY build.gradle /data/build.gradle
COPY gradlew /data/gradlew
RUN chmod +x /data/gradlew

WORKDIR /data
RUN ./gradlew installDist
RUN cp -R build/install/url-shortener /release
RUN rm -rf /data

WORKDIR /release

EXPOSE 8080
CMD bin/url-shortener
