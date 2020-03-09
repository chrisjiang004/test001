package unit.datademo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

//测试  读写json和yaml文件的方法
public class TestResources {
    public String name;
    public int age;

    @Test
    public void readFile() throws IOException {
        System.out.println(this.getClass().getName());//获取当前测试类的路径和类名unit.datademo.TestResources
        System.out.println(this.getClass().getCanonicalName());//unit.datademo.TestResources
        System.out.println(this.getClass().getResource("/"));//是文件被打包后的路径：斜杠/表示resource根目录
        System.out.println(this.getClass().getResource("/app/testcase/TestSearch.yaml"));
        System.out.println(
                //使用自带库FileUtils的api读取目标文件的数据流
                FileUtils.readFileToString(
                        new File(
                                this.getClass().getResource("/app/testcase/TestSearch.yaml").getPath()), "UTF-8"));

    }

    @Test
    //在当前目录(resource根目录)中写入json格式的demo.json文件
    public void writeJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("demo.json"), this);

    }

    @Test
    //resource跟目录读取存在的demo.json文件内容
    public void readJson() throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        TestResources demo=mapper.readValue(new File("demo.json"), this.getClass());
        System.out.println(demo);
        System.out.println(demo.name);
    }

    @Test
    //读取/app/testcase/TestSearch.yaml的yaml文件内容
    public void readYaml() throws IOException {
        ObjectMapper mapper=new ObjectMapper(new YAMLFactory());
        //2维数据类型
        Object[][] demo=mapper.readValue(this.getClass().getResourceAsStream("/app/testcase/TestSearch.yaml"),
                Object[][].class);
        System.out.println(demo);//对象地址
        System.out.println(demo.length);//外围数组长度
        System.out.println(demo[0].length);//第一个内数组的长度
        System.out.println(demo[0]);//第一个数组的地址
        System.out.println(demo[0][0]);//第一个数组中的第一个值

    }
}
