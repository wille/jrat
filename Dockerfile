FROM ubuntu:17.10

RUN apt-get update
RUN apt-get install -y openjdk-8-jre zip

WORKDIR /build

COPY makefile .
COPY zkm/ zkm/
COPY controller/zkm.txt zkm-controller.txt
COPY client/zkm.txt zkm-client.txt

COPY controller/controller.jar .
COPY controller/modules modules
COPY controller/files/client.jar .

RUN java -jar zkm/ZKM.jar zkm-controller.txt
RUN java -jar zkm/ZKM.jar zkm-client.txt

RUN mkdir out/modules
RUN mv out/*-controller.jar out/*-client.jar out/modules

COPY controller/files/db.dat controller/files/Builder.exe out/files/
RUN mkdir /deploy
RUN cd out && zip /deploy/jrat-archive.zip *
