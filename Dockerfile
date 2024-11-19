# Sử dụng OpenJDK 17
FROM openjdk:17-slim AS build

# Cài đặt Maven
RUN apt-get update && apt-get install -y maven

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Build ứng dụng bằng Maven
RUN mvn clean package -DskipTests

# Sử dụng OpenJDK để chạy ứng dụng
FROM openjdk:17-jdk-slim

# Thiết lập biến môi trường
ENV DB_HOST=localhost
ENV DB_PORT=5432
ENV DB_USER=postgres
ENV DB_PASSWORD=admin

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép tệp JAR từ giai đoạn build vào container
COPY --from=build /app/target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]

