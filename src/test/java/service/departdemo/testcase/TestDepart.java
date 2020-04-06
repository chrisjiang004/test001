package service.departdemo.testcase;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.departdemo.api.Depart;
import java.util.ArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestDepart {
    //在哪个子部门下测试 需要参数化
    static int parentDepartId = 1629845;
    static Depart depart = new Depart();
    @BeforeAll
    //清理数据删除已经存在的部门
    public static void beforall(){
        ArrayList<Integer> ids = depart.list(parentDepartId).then()
                .extract().body().path("department.findAll {d->d.parentid==" + parentDepartId + " }.id");
        System.out.println(ids);
        ids.forEach(id -> depart.delet(id));
    }


    @Test
    public void testCreatDepart(){
        String name = "部门3";
        depart.creat(name,parentDepartId);
        //调用list接口来断言是否存在部门3
        //断言添加的部门的id是否存在，使用jsonpath
        depart.list(parentDepartId).then().body("department.findAll {d->d.name=='" + name + "' }.id", hasSize(1));


    }

    @Test
    public void testDepartList(){
        depart.list(parentDepartId).then().body("errcode",equalTo(0));


    }

    @Test
    public void testDeletDepart(){
        String name = "部门4";
        int id =depart.creat(name,parentDepartId).then().extract().body().path("id");
        depart.delet(id).then().body("errmsg", equalTo("deleted"));
    }
}
