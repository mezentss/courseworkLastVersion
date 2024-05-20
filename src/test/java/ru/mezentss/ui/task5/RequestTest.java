package ru.mezentss.ui.task5;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Test;
import ru.mezentss.api.task5.pojos.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class RequestTest {

    @Test
    @DisplayName("Получить список пользователей со страницы 2")
    public void getUsers() {
        List<UserData> users = given().
                when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserListSchema.json"))
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", UserData.class);
        assertThat(users).extracting(UserData::getId).isNotNull();
        assertThat(users).extracting(UserData::getFirst_name).contains("Tobias");
        assertThat(users).extracting(UserData::getLast_name).contains("Funke");
    }

    @Test
    @DisplayName("Получить пользователя с id=2")
    public void getUser() {
        UserData user = given().
                when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserSingleSchema.json"))
                .extract().jsonPath().getObject("data", UserData.class);
        assertThat(user).extracting(UserData::getId).isEqualTo(2);
        assertThat(user).extracting(UserData::getEmail).isEqualTo("janet.weaver@reqres.in");
        assertThat(user).extracting(UserData::getFirst_name).isEqualTo("Janet");
        assertThat(user).extracting(UserData::getLast_name).isEqualTo("Weaver");
        assertThat(user).extracting(UserData::getAvatar).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    @DisplayName("Получить пользователя с id=22")
    public void getUserNotFound() {
        given().
                when()
                .get("https://reqres.in/api/users/22")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Получить список ресурсов")
    public void getResourses() {
        List<ResourceData> resourses = given().
                when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceListSchema.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", ResourceData.class);
        assertThat(resourses).extracting(ResourceData::getId).isNotNull();
        assertThat(resourses).extracting(ResourceData::getName).contains("fuchsia rose");
        assertThat(resourses).extracting(ResourceData::getYear).contains(2001);
        assertThat(resourses).extracting(ResourceData::getColor).contains("#C74375");
    }

    @Test
    @DisplayName("Получить ресурс с id=2")
    public void getResource() {
        ResourceData resource = given().
                when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceSingleSchema.json"))
                .extract().jsonPath().getObject("data", ResourceData.class);
        assertThat(resource).extracting(ResourceData::getId).isEqualTo(2);
        assertThat(resource).extracting(ResourceData::getName).isEqualTo("fuchsia rose");
        assertThat(resource).extracting(ResourceData::getYear).isEqualTo(2001);
        assertThat(resource).extracting(ResourceData::getColor).isEqualTo("#C74375");
        assertThat(resource).extracting(ResourceData::getPantone_value).isEqualTo("17-2031");
    }

    @Test
    @DisplayName("Получить ресурс с id=22")
    public void getResourceNotFound() {
        given().
                when()
                .get("https://reqres.in/api/unknown/22")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Создать пользователя")
    public void createUser() {
        UserRequest rq =
                UserRequest.builder()
                        .name("morpheus")
                        .job("leader")
                        .build();

        UserResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateUserResponseSchema.json"))
                .extract().as(UserResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(UserResponse::getName)
                .isEqualTo(rq.getName());

        assertThat(rs)
                .isNotNull()
                .extracting(UserResponse::getJob)
                .isEqualTo(rq.getJob());
    }

    @Test
    @DisplayName("Обновить пользователя метод PUT")
    public void updateUserPut() {
        UserRequest rq =
                UserRequest.builder()
                        .name("morpheus")
                        .job("tester")
                        .build();

        UserResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateUserResponseSchema.json"))
                .extract().as(UserResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(UserResponse::getName)
                .isEqualTo(rq.getName());

        assertThat(rs)
                .isNotNull()
                .extracting(UserResponse::getJob)
                .isEqualTo(rq.getJob());
    }

    @Test
    @DisplayName("Обновить пользователя метод PATCH")
    public void updateUserPatch() {
        UserRequest rq =
                UserRequest.builder()
                        .name("morpheus")
                        .job("tester")
                        .build();

        UserResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateUserResponseSchema.json"))
                .extract().as(UserResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(UserResponse::getName)
                .isEqualTo(rq.getName());

        assertThat(rs)
                .isNotNull()
                .extracting(UserResponse::getJob)
                .isEqualTo(rq.getJob());
    }

    @Test
    @DisplayName("Удалить пользователя")
    public void deleteUser() {
        given().
                when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void registerSuccessful() {
        LoginRegisterRequest rq =
                LoginRegisterRequest.builder()
                        .email("eve.holt@reqres.in")
                        .password("pistol")
                        .build();

        LoginRegisterResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("RegisterSuccessfulResponseSchema.json"))
                .extract().as(LoginRegisterResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(LoginRegisterResponse::getId)
                .isEqualTo(4);

        assertThat(rs)
                .isNotNull()
                .extracting(LoginRegisterResponse::getToken)
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    public void registerUnsuccessful() {
        LoginRegisterRequest rq =
                LoginRegisterRequest.builder()
                        .email("user@mail")
                        .build();

        LoginRegisterResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("RegisterLoginUnsuccessfulResponseSchema.json"))
                .extract().as(LoginRegisterResponse.class);
        assertThat(rs)
                .isNotNull()
                .extracting(LoginRegisterResponse::getError)
                .isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void loginSuccessful() {
        LoginRegisterRequest rq =
                LoginRegisterRequest.builder()
                        .email("eve.holt@reqres.in")
                        .password("cityslicka")
                        .build();

        LoginRegisterResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginSuccessfulResponseSchema.json"))
                .extract().as(LoginRegisterResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(LoginRegisterResponse::getToken)
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    public void loginUnsuccessful() {
        LoginRegisterRequest rq =
                LoginRegisterRequest.builder()
                        .email("peter@klaven")
                        .build();

        LoginRegisterResponse rs = given()
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("RegisterLoginUnsuccessfulResponseSchema.json"))
                .extract().as(LoginRegisterResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(LoginRegisterResponse::getError)
                .isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Получить список пользователей с временной задержкой")
    public void getUsersDelay() {
        List<UserData> users = given().
                when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(greaterThan(3000L)).and().time(lessThan(6000L))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserListSchema.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", UserData.class);
        assertThat(users).extracting(UserData::getId).isNotNull();
        assertThat(users).extracting(UserData::getFirst_name).contains("George");
        assertThat(users).extracting(UserData::getLast_name).contains("Bluth");
    }
}
