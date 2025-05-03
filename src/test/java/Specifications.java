import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.*;

import java.net.URI;

public class Specifications {

    public static String apiKey = "reqres-free-v1"; //  API ключ

    // cоздаем объект класса для создания запроса и присваиваем ему методы
    public static RequestSpecification requestSpec(String URI) {
        return new RequestSpecBuilder()
                .setBaseUri(URI)
                //  .addHeader("Authorization", "Bearer " + apiKey) // Добавляем заголовок с API-ключом
                .setContentType(ContentType.JSON)
                .build();
    }

    // cоздаем объект класса для проверки ответа и присваиваем ему методы
    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpec400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification responseSpecUnique() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();


// cоздаем метод для вызова request и response. Rest-Assured вызываем явно
    }

    public static void installSpecifications(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}