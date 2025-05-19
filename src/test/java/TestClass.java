import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class TestClass {
    public String URI = "https://reqres.in";
    public String apiKey = "reqres-free-v1"; //  API ключ

    @Test
    public void checkAvatarIdTest() {

        // для проверки совпадения аватаров и id сначала создаем список со ссылкой на класс UserData
        // условие

        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());
        List<UserData> users = given()
                .when()
                .header("x-api-key", "reqres-free-v1") // заменяем Authorization на x-api-key
                // .contentType(ContentType.JSON) // задаем формат, в каком хотим получить данные
                .get("/api/users?page=2") // пописываем путь, где возьмем инфо
                // что надо сделать
                .then()
                .log().all() //выгрузить всю инфу
                .extract().body().jsonPath().getList("datum", UserData.class);
        users.forEach(x ->
                Assertions.assertTrue(x.getAvatar().contains(x.getId().toString()),
                        "Avatar does not contain user ID for user: " + x.getId())
        );

// Проверка, что все email заканчиваются на "@regres.in"
        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@regres.in")),
                "Not all users have email ending with @regres.in");
// первый вариант -  out of the box
// другой вариант - через спецификацию

    }

    @Test
    public void checkSuccessRegistTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        Registration user = new Registration("eve.holt@reqres.in", "pistol");

        RegSuccess success = given() // создаем метод для получения успешной регистрации - через given, when, then
                .body(user) // берем тело запроса про пользователя с его данными регистрации
                .when()
                .header("x-api-key", "reqres-free-v1") // заменяем Authorization на x-api-key
                .post("/api/register")
                .then()
                .log().all().extract().as(RegSuccess.class);
        Assert.assertNotNull(RegSuccess.getId()); // исправлено на success.getId()
        Assert.assertNotNull(RegSuccess.getToken());
        Assert.assertEquals(id, RegSuccess.getId()); // исправлено на success.getId()
        Assert.assertEquals(token, RegSuccess.getToken()); // исправлено на success.getToken()
    }

    @Test
    public void UnsuccessReg() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec400());
        String error = "Missing password";

        Registration user = new Registration("sydney@fife", "");
        UnsuccessReg unsuccess = given()
                .body(user) // берем тело запроса про пользователя с его данными регистрации
                .when()
                .header("x-api-key", "reqres-free-v1") // заменяем Authorization на x-api-key
                .post("/api/register")
                .then()
                .log().all().extract().as(UnsuccessReg.class);
        Assert.assertNotNull(UnsuccessReg.getError());

        Assert.assertEquals(error, UnsuccessReg.getError());
    }

    @Test
    public void SortedDataTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URI), Specifications.responseSpec());
        List<ListResource> list = given()
                .header("x-api-key", "reqres-free-v1")
                .get("/api/unknown")
                .then()
                .log().all().extract().body().jsonPath().getList("data", ListResource.class);
        List<Integer> years = list.stream().map(ListResource::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);// сравниваем с years, тк изначально предполагается. что годы отсортированы
        System.out.println("отсортированные годы" + sortedYears);
        System.out.println("годы" + years);
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
        String name = "morpheus";
        String job = "zion resident";
        UserTime user = new UserTime(name, job);
        UserTimeNew userNew = (UserTimeNew) given()
                .body(user)// берем тело запроса, который мы создали в классе UserTime
                .when()
                .header("x-api-key", "reqres-free-v1")
                .put("/api/users/2")
                .then()
                .log().all()
                .extract().as(UserTimeNew.class);
        String regex = "(.{9})$";
        String regex2 = "(.{15})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex2, "");//  cоздаем переменную для текущего времени НА КОМПЬЮТЕРЕ и "отрезаем" последние 5 символов через метод replace на ничто
        Assert.assertEquals(userNew.getUpdateAt().replaceAll(regex, ""), currentTime);
        System.out.println(userNew.getUpdateAt().replaceAll(regex, ""));
        System.out.println(currentTime);
    }
}