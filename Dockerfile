FROM gradle:jdk11-alpine
COPY . /top-hits-analyzer
WORKDIR /top-hits-analyzer
RUN gradle build
ENTRYPOINT ["java", "-jar", "/top-hits-analyzer/build/libs/TopHitsAnalyzer-1.0.0.jar"]