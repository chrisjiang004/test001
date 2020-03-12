package service.firstdemo;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestApiDemo {
    static String token;
    static int fdepartid = 1;

    @BeforeAll
    public static void gettoken(){
        token = given()
                .log().all().
                param("corpid","ww69789e71d8507b96").
                param("corpsecret","36KC68MEb4HIhrsBxZx8xtAu06I5fya3RdnM8ZFjaf0").
                when().
                log().all().
                get("https://qyapi.weixin.qq.com/cgi-bin/gettoken").
                then().
                log().all().
                assertThat().body("errcode",equalTo(0))
                .extract().body().path("access_token");
        System.out.println(token);

    }
    @Test
    public void creatDeparment(){
        //TODO:需要用下面的list接口来验证断言。如果在这里再写list请求就会造成代码冗余和维护难的问题。所以需要封装
        //定义hashmap的data
        HashMap<String,Object> data = new HashMap<>();
        data.put("name","myDepartment");
        data.put("parentid",fdepartid);
        data.put("order",522);
        given()
                .log().all()
                .queryParam("access_token",token)
                .contentType(ContentType.JSON)//声明json格式
                .body(data)//data是hashmap或者json格式
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then()
                .log().all()//这个会显示response
                .body("errcode",equalTo(0));


    }

    @Test
    public void depatLsit(){
        given()
                .log().all()//这个log只会显示head
                .param("access_token",token)
                .param("id",fdepartid)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .body("errcode",equalTo(0));
    }

}
