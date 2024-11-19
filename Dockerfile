# Sử dụng OpenJDK 17
FROM openjdk:17-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Cài đặt Maven và build ứng dụng
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# Thiết lập các biến môi trường (nếu cần)
ENV DB_HOST=localhost
ENV DB_PORT=5432
ENV DB_USER=postgres
ENV DB_PASSWORD=admin

# Sao chép tệp JAR đã build vào container
COPY target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
