package service.restassured;

import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.Base64;

//继承子类来重写filter方法。把base64加密的response解密成正常的response
public class Base64DecodeFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext filterContext) {
        Response responseOrigin = filterContext.next(requestSpec,responseSpec);//next方法发起请求
        ResponseBuilder responseBuilder = new ResponseBuilder().clone(responseOrigin);//得到原response
        //把原内容base64解密 赋给decode
        String decode = new String(
                Base64.getDecoder().decode(
                        responseOrigin.body().asString().trim()//trim方法用于删除字符串的头尾空白符
                )//array.asStrin()得到body的字符串
        );
        responseBuilder.setBody(decode);//把修改后的内容放入body
        return responseBuilder.build();//需要执行build才返回response
    }
}
