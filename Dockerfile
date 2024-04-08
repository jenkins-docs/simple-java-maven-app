FROM java-17
WORKDIR /src
COPY *.jar .
CMD [java, "*.jar"]
