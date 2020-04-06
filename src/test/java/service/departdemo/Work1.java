package service.departdemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Work1 {
    private String token;
    //单例模式 防止并发出错
    private static Work1 work;
    public static Work1 getInstance(){
        if (work==null){
            work = new Work1();
        }
        return work;
    }
    public String getTocken(){
        if (token == null){
            token = given()
//                    .log().all()
                            .param("corpid","ww69789e71d8507b96").
                            param("corpsecret","36KC68MEb4HIhrsBxZx8xtAu06I5fya3RdnM8ZFjaf0").
                            when()
//                            .log().all()
                            .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken").
                            then().
                            log().all().
                            assertThat().body("errcode",equalTo(0))
                    .extract().body().path("access_token");
            System.out.println(token);
        }
       return token;


    }
}
