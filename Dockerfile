# Chọn base image chứa Java
FROM openjdk:17-jdk-slim

# Cài đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file jar từ target vào thư mục làm việc trong container
COPY target/Expenses-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng mà Spring Boot sử dụng (thường là 8080)
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
