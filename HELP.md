# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/#build-image)


API Endpoints:
* http://localhost:8080/v1/customer/get/book/title/{title}
* http://localhost:8080/v1/admin/add/book
* http://localhost:8080/v1/admin/update/book
* http://localhost:8080/v1/admin/remove/book/{bookId}
* http://localhost:8080/v1/customer/add/shopping/cart
* http://localhost:8080/v1/customer/remove/shopping/cart
* http://localhost:8080/v1/customer/checkout/shopping/cart/{cartId}

Curl Details:
* curl --location 'http://localhost:8080/v1/customer/get/book/title/test' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic dmlqYXk6dmlqYXkxMjM=' \
  --header 'Cookie: JSESSIONID=BFD42BADDB0BC1210C455A514D2DB685'

* curl --location 'http://localhost:8080/v1/admin/add/book' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YWRtaW46YWRtaW4xMjM=' \
  --header 'Cookie: JSESSIONID=B925311A36DE5E15CA7992BB0523DE36' \
  --data '{
  "title":"test1",
  "author":"test1",
  "category":1,
  "price":100.00,
  "stockQuantity":30
}'

* curl --location --request PUT 'http://localhost:8080/v1/admin/update/book' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YWRtaW46YWRtaW4xMjM=' \
  --header 'Cookie: JSESSIONID=7C92AF4C54822B0239AFC2FF2F244D19' \
  --data '{
  "id":1,
  "title":"test",
  "author":"test",
  "category":1,
  "price":151,
  "stockQuantity":10
}'

* curl --location --request DELETE 'http://localhost:8080/v1/admin/remove/book/1' \
  --header 'Authorization: Basic YWRtaW46YWRtaW4xMjM=' \
  --header 'Cookie: JSESSIONID=94905B25332CC2BC9B9A2ED76D83DAAC'

* curl --location 'http://localhost:8080/v1/customer/add/shopping/cart' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic dmlqYXk6dmlqYXkxMjM=' \
  --header 'Cookie: JSESSIONID=82EE17B70666555908D7044AC10F25B2' \
  --data '{
  "userId":1,
  "bookId":1,
  "quantity":5
  }'

* curl --location --request DELETE 'http://localhost:8080/v1/customer/remove/shopping/cart' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic dmlqYXk6dmlqYXkxMjM=' \
  --header 'Cookie: JSESSIONID=157574F7483B74E4EFF6E32133BC67B5' \
  --data '{
  "userId":1,
  "bookId":1
  }'

* curl --location --request POST 'http://localhost:8080/v1/customer/checkout/shopping/cart/1' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic dmlqYXk6dmlqYXkxMjM=' \
  --header 'Cookie: JSESSIONID=8F168A12F0451E926851013D02517264'
