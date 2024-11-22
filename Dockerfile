# Giai đoạn 1: Build Stage
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Chỉ copy các tệp cần thiết để tận dụng cache của Docker
COPY pom.xml ./
COPY src ./src

# Build ứng dụng
RUN mvn clean package -DskipTests

# Giai đoạn 2: Run Stage
FROM eclipse-temurin:17-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file JAR từ giai đoạn build
COPY --from=build /app/target/*.jar app.jar

# Mở cổng ứng dụng
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
