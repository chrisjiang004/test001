package service.departdemo.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.departdemo.Work1;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Depart {
    //public int fdepartid = 1 ;
    public Response creat(String name,int parentid){
        HashMap<String,Object> data = new HashMap<>();
        data.put("name",name);
        data.put("parentid",parentid);
        return given()
                .log().all()
                .queryParam("access_token",Work1.getInstance().getTocken())
                .contentType(ContentType.JSON)//声明json格式
                .body(data)//data是hashmap或者json格式
                .when()
                .log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then()
                .log().all()//这个会显示response
                .extract().response();
    }

    public Response list(int id){
        return given()
                //.log().all()//这个log只会显示head
                .param("access_token",Work1.getInstance().getTocken())
                .param("id",id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log().all()
//                .body("errcode",equalTo(0))
                .extract().response();

    }

    public Response delet(int id){
        return given()
                .param("access_token",Work1.getInstance().getTocken())
                .param("id",id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log().all()
                .extract().response();
    }

}
