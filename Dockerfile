RUN apt-get update && apt-get install -y python-software-properties software-properties-common
RUN add-apt-repository ppa:webupd8team/java

RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections

RUN apt-get update && apt-get install -y oracle-java8-installer maven

ADD . /usr/local/lesson12task1
RUN cd /usr/local/lesson12task1 && mvn install
CMD ["java", "-cp", "/usr/local/lesson12task1/target/lesson12task1-1.0.war", "lesson12task1"]