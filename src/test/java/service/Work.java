package service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Work {
    private static Work work;
    String token;

    //单例模式 防止并发出错
    public static Work getInstance() {
        if (work == null) {
            work = new Work();
        }
        return work;
    }


    public String getToken() {
        //需要改成自己的企业微信参数
        if (token == null) {
            token = given()
                    .param("corpid","ww69789e71d8507b96")
                    .param("corpsecret","36KC68MEb4HIhrsBxZx8xtAu06I5fya3RdnM8ZFjaf0")
                    .when()
                    .log().all()
                    .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                    .then()
                    .log().all()
                    .body("errcode", equalTo(0))
                    .extract()
                    .body().path("access_token");
            System.out.println(token);
        }

        return token;
    }
}
