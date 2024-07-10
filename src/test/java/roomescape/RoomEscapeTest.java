package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoomEscapeTest {
    @Test
    @DisplayName("홈 화면 출력 테스트")
    void homePageTest() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 목록 화면 출력 테스트")
    void reservationListPageTest() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 추가 테스트")
    void createReservationTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "bang");
        params.put("date", "2024-07-01");
        params.put("time", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "12:00"))
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", matchesPattern("/times/\\d+"))
                .extract().response();

        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", matchesPattern("/reservations/\\d+"))
                .extract().response();

        // Location 헤더에서 ID 추출
        String locationHeader = response.getHeader("Location");
        String extractedId = locationHeader.replaceAll(".*/reservations/", "");

        // Location 헤더에 있는 ID와 응답 본문의 ID가 일치하는지 확인
        response.then()
                .body("id", is(Integer.parseInt(extractedId)))
                .body("name", is("bang"))
                .body("date", is("2024-07-01"))
                .body("timeId", is(1));
    }

    @Test
    @DisplayName("예약 취소 테스트")
    void cancelReservationTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "bang");
        params.put("date", "2024-07-01");
        params.put("time", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "12:00"))
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", matchesPattern("/times/\\d+"))
                .extract().response();

        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", matchesPattern("/reservations/\\d+"))
                .extract().response();

        // Location 헤더에서 ID 추출
        String locationHeader = response.getHeader("Location");
        String extractedId = locationHeader.replaceAll(".*/reservations/", "");

        RestAssured.given().log().all()
                .when().delete("/reservations/" + extractedId)
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("예약 시 하나의 필드라도 null이면 상태 코드 400 반환")
    void createReservationWithNullFieldTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", null);
        params.put("date", "2024-07-01");
        params.put("time", "15:00");

        // name 필드가 null인 경우
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        // date 필드가 null인 경우
        params.put("name", "bang");
        params.put("date", null);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        // time 필드가 null인 경우
        params.put("date", "2024-07-01");
        params.put("time", null);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 취소 시 존재하지 않는 예약 ID인 경우 상태 코드 400 반환")
    void cancelNonExistentReservationTest() {
        RestAssured.given().log().all()
                .when().delete("/reservations/999")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 목록 조회 테스트")
    void getReservationsTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 목록 조회 테스트")
    void getTimesTest() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 추가 테스트")
    void addTimeTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "12:00"))
                .when().post("/times")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("시간 삭제 테스트")
    void deleteTimeTest() {
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "12:00"))
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", matchesPattern("/times/\\d+"))
                .extract().response();

        RestAssured.given().log().all()
                .when().delete("/times/" + response.path("id"))
                .then().log().all()
                .statusCode(204);
    }
}
