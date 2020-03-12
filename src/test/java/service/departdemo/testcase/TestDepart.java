package service.departdemo.testcase;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.departdemo.api.Depart;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestDepart {
    static Depart depart = new Depart();
    @BeforeAll
    //清理数据删除已经存在的部门

    @Test
    public void testCreatDepart(){
        String name = "部门3";
        depart.creat(name,1629846);
        //调用list接口来断言是否存在部门3
        depart.list(1629846).then().body("department.findAll {d->d.name=='" + name + "' }.id", hasSize(1));


    }

    @Test
    public void testDepartList(){
        depart.list(1629846).then().body("errcode",equalTo(0));


    }

    @Test
    public void testDeletDepart(){

    }
}
