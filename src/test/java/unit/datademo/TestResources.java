package unit.datademo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestResources {
    public String name;
    public int age;

    @Test
    public void readFile() throws IOException {
        System.out.println(this.getClass().getName());
        System.out.println(this.getClass().getCanonicalName());
        System.out.println(this.getClass().getResource("/"));//是文件被打包后的路径：斜杠/表示resource根目录
        System.out.println(this.getClass().getResource("/app/testcase/TestSearch.yaml"));
        System.out.println(
                //使用api读取目标文件的数据流
                FileUtils.readFileToString(
                        new File(
                                this.getClass().getResource("/app/testcase/TestSearch.yaml").getPath()), "UTF-8"));

    }

    @Test
    public void writeJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("demo.json"), this);

    }

    @Test
    public void readJson() throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        TestResources demo=mapper.readValue(new File("demo.json"), this.getClass());
        System.out.println(demo);
        System.out.println(demo.name);
    }

    @Test
    public void readYaml() throws IOException {
        ObjectMapper mapper=new ObjectMapper(new YAMLFactory());
        Object[][] demo=mapper.readValue(this.getClass().getResourceAsStream("/app/testcase/TestSearch.yaml"),
                Object[][].class);
        System.out.println(demo);

        System.out.println(demo.length);
        System.out.println(demo[0].length);
        System.out.println(demo[0]);
        System.out.println(demo[0][0]);

    }
}
