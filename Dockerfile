# Sử dụng Java 17 như môi trường chạy dự án của bạn
FROM eclipse-temurin:17-jdk-alpine

# Thiết lập thư mục làm việc trong Docker container
WORKDIR /app

# Sao chép file Gradle wrapper
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Đảm bảo script gradlew có quyền thực thi
RUN chmod +x ./gradlew

# Biên dịch và đóng gói ứng dụng
RUN ./gradlew bootJar --no-daemon

# Tinh chỉnh JVM để chạy trong container
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Mở cổng mạng
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "build/libs/livestockmanagementapi-0.0.1-SNAPSHOT.jar"]