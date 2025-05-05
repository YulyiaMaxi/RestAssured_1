import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class Test_Refactoring {
    public String URI = "https://reqres.in";
    public String apiKey = "reqres-free-v1"; //  API ключ

    @Test

    public void checkIDinAvatar() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());

        Response response = given()
                .when()
                .header("x-api-key", "reqres-free-v1") // заменяем Authorization на x-api-key
                .get("/api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))//проверка
                .body("data.avatar", notNullValue())
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> avatars = jsonPath.get("data.avatar");
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");// внутри списка нельзя преобразовать в стринг

        for (int i = 0; i < avatars.size(); i++) { // это не массив, поэтому ставим ;
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i).toString()));// здест не сравниваем, а проверяем утверждение

        }
        Assertions.assertTrue(emails.stream().allMatch(x -> x.endsWith("@reqres.in")));
    }

    @Test
    public void checkSuccessRegTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());

        Map<String, String> user = new HashMap<>();// создаем hashmap для запроса
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        // задаем значения и ключ
        Response response =
                given() // создаем метод для получения успешной регистрации - через given, when, then
                        .body(user) // берем тело запроса про пользователя с его данными регистрации
                        .when()
                        .header("x-api-key", "reqres-free-v1") // заменяем Authorization на x-api-key
                        .post("/api/register")
                        .then()
                        .log().all()
                        // .body("id", equalTo(4))
                        //  .body("token", equalTo("QpwL5tke4Pnpja7X4"))  // можем обойтись без response - не класть никуда данные и не вызывать extract и преобразование в jsonpath
                        .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");
        Assert.assertEquals(4, id);
        Assert.assertEquals("QpwL5tke4Pnpja7X4", token);

    }

    @Test
    public void UnsuccessReg() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec400());
        Map<String, String> user = new HashMap<>();// создаем hashmap для запроса
        user.put("email", "sydney@fife");
        Response response = given()
                .body(user)// для метода post body задаем в  начале
                .when()
                .header("x-api-key", "reqres-free-v1") // заменяем Authorization на x-api-key
                .post("/api/register")
                .then()
                .log().all().extract().response();
        JsonPath jsonPath = response.jsonPath();
        String error = jsonPath.get("error");
        Assert.assertEquals("Missing password", jsonPath.get("error"));

    }

    @Test
    public void SortedDataTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());
        Response response = given()
                .when()
                .header("x-api-key", "reqres-free-v1")
                .get("/api/unknown")
                .then()
                .log().all().extract().response();
        JsonPath jsonPath = response.jsonPath();

        //  List<Integer> years = list.stream().map(ListResource::getYear).collect(Collectors.toList());
        List<Integer> years = jsonPath.get("data.year");
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);// сравниваем с years, тк изначально предполагается. что годы отсортированы
        System.out.println("отсортированные годы" + " " + sortedYears);
        System.out.println("годы" + " " + years);
    }

    @Test
    public void DeleteUserTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpecUnique());
        given()
                .when()
                .header("x-api-key", "reqres-free-v1")
                .delete("/api/users/2")
                .then()
                .log().all();
    }

    @Test
    public void TimeUserTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());
        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");
        Response response =
                given()
                        .body(user)// берем тело запроса, который мы создали в классе UserTime
                        .when()
                        .header("x-api-key", "reqres-free-v1")
                        .put("/api/users/2")
                        .then()
                        .log().all().extract().response();//надо всегда после логирования куда-то класть результат - либо в pojo класс, либо в response

        JsonPath jsonPath = response.jsonPath();

        String regex = "(.{9})$";
        String regex2 = "(.{15})$";

        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex2, "");//  cоздаем переменную для текущего времени НА КОМПЬЮТЕРЕ и "отрезаем" последние 5 символов через метод replace на ничто

        Assert.assertEquals(jsonPath.get("updatedAt").toString().replaceAll(regex, ""), currentTime);
        System.out.println("время на компьютере" + " " + currentTime);

        System.out.println("время из ответа сервера" + " " + jsonPath.get("updatedAt").toString().replaceAll(regex, ""));

    }
}
