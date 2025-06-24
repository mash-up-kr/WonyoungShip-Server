FROM eclipse-temurin:21-jdk

COPY doong2-bootstrap/build/libs/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxMetaspaceSize=256M -Xmx256M -Xss256k"
ENV BPL_JVM_MEMORY_CALCULATOR_DISABLED=true
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
