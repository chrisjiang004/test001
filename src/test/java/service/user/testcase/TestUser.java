package service.user.testcase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import service.user.api.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestUser {

    @Test
    public void info() {
        User user = new User();
        user.get("seveniruby1564217505.480352").then()
                .body("name", equalTo("name for testing"));
    }

    @Test
    public void update() {
        //todo: add more testcase
        User user = new User();
        String userid = "seveniruby1564217505.480352";
        String nameNew = "name for testing";
//        user.update("xxx", "name", "ddddd");
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameNew);
        data.put("address", "address for testing");
        user.update(userid, data);
        user.get(userid).then().body("name", equalTo(nameNew));
    }

    @Test
    public void create() {
        String nameNew = "name for testing";
        String userid = "seveniruby_" + System.currentTimeMillis();


        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameNew);
        data.put("department", new int[]{1});
        data.put("mobile", String.valueOf(System.currentTimeMillis()).substring(0, 11));
        data.put("address", "address for testing");

        User user = new User();
        user.create(userid, data).then().body("errcode", equalTo(0));

        user.get(userid).then().body("name", equalTo(nameNew));
    }

    @Test
    public void cloneUser() {
        String nameNew = "name for testing";
        String userid = "seveniruby_" + System.currentTimeMillis();

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameNew);
//        data.put("department", new int[]{1});
        data.put("mobile", String.valueOf(System.currentTimeMillis()).substring(0, 11));

        User user = new User();
        user.clone(userid, data).then().body("errcode", equalTo(0));

        user.get(userid).then().body("name", equalTo(nameNew));
    }

    @Test
    public void delete() {
        String nameNew = "name for testing";
        String userid = "seveniruby_" + System.currentTimeMillis();

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameNew);
//        data.put("department", new int[]{1});
        data.put("mobile", String.valueOf(System.currentTimeMillis()).substring(0, 11));

        User user = new User();
        user.clone(userid, data).then().body("errcode", equalTo(0));

        user.delete(userid).then().body("errcode", equalTo(0));
        user.get(userid).then().body("errcode", not(equalTo(0)));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "TestUser.csv")
    //csv文件数据驱动是最常用的。简单数据类型适用
    public void deleteByParams(String name, String userid) {
        String nameNew = name;
        if (userid.isEmpty()) {
            userid = "seveniruby_" + System.currentTimeMillis();
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameNew);
        data.put("department", new int[]{1});//赋值[1]
        data.put("mobile", String.valueOf(System.currentTimeMillis()).substring(0, 11));

        User user = new User();
        user.create(userid, data).then().body("errcode", equalTo(0));

        user.delete(userid).then().body("errcode", equalTo(0));
        user.get(userid).then().body("errcode", not(equalTo(0)));
    }

    @ParameterizedTest
    @MethodSource("deleteByParamsFromYamlData")
    //从yaml文件中，yaml数据类型灵活，但是读取比较麻烦，如果是复杂类型就使用yaml来参数化
    public void deleteByParamsFromYaml(String name, String userid, List<Integer> departs) {
        String nameNew = name;
        if (userid.isEmpty()) {
            userid = "seveniruby_" + System.currentTimeMillis();
        }
        if(departs==null){
            departs=Arrays.asList(1);
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameNew);
        data.put("department", departs);
        data.put("mobile", String.valueOf(System.currentTimeMillis()).substring(0, 11));

        User user = new User();
        user.create(userid, data).then().body("errcode", equalTo(0));

        user.delete(userid).then().body("errcode", equalTo(0));
        user.get(userid).then().body("errcode", not(equalTo(0)));
    }

    static Stream<Arguments> deleteByParamsFromYamlData() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        //生成一个代表List<HashMap>的类型，用于传递给readValue。TypeReference代表复杂类型
        TypeReference<List<HashMap<String, Object>>> typeRef =
                new TypeReference<List<HashMap<String, Object>>>() {
                };
//        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        List<HashMap<String, Object>> data;
        try {
            data = mapper.readValue(
                    TestUser.class.getResourceAsStream("TestUser.yaml"),
                    typeRef);//"TestUser.yaml"resource下的相对路径下找该文件 把文件转成类或者类型对象
            ArrayList<Arguments> results = new ArrayList<>();
            data.forEach(map -> {
                results.add(arguments(
                        map.get("name").toString(),
                        map.get("userid").toString(),
                        map.get("departs")//departs的节点没有值就是null
                ));
            });

            return results.stream();//把2维数组result转成stream流对象
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of();//以流的方式循环输出String数组

    }

    //todo: 获取部门成员列表

    //todo: 批量删除成员

}
