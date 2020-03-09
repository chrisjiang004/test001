package app.testcase;

import app.page.App;
import app.page.StockPage;
import org.apache.dubbo.config.support.Parameter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.MalformedURLException;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStock {
    private static StockPage stockPage;
    @BeforeAll
    public static void beforeAll() throws MalformedURLException {
        App.getInstance().start();
        stockPage=App.getInstance().toStocks();

    }
    @BeforeEach
    public void beforeEach(){

    }
    @Order(100)
    @Test
    public void addDefaultSelectedStocks(){
        //先判断是否有自选股 有则删除全部
        if(stockPage.getAllStocks().size()>=1){
            stockPage.deleteAll();//删除已有自选股
        }
        //使用 [一键添加自选股] 按钮6个自选股后,再获取自选股列表，是否是6
        assertThat(stockPage.addDefaultSelectedStocks().getAllStocks().size(), greaterThanOrEqualTo(6));
    }

    //访问csv来数据驱动case数据
    @Order(120)
    @ParameterizedTest
    @CsvFileSource(resources = "/app/testcase/TestStock.csv",numLinesToSkip = 1)
    public void addStock1(String code, String name){
        stockPage.toSearch().search(code).select().cancel();
        assertThat(stockPage.getAllStocks(), hasItem(name));//是否有name变量股票

    }


    @Order(200)
    @ParameterizedTest //参数化case就不能再加@Test
//    @ValueSource(strings = { "pdd", "xiaomi"})
    @MethodSource("data") //这个方法的data可以使用Stream流数据
    public void addStock(String code, String name){
        stockPage.toSearch().search(code).select().cancel();
        assertThat(stockPage.getAllStocks(), hasItem(name));//是否有name变量股票

    }

    public static Stream<Arguments> data(){
        return Stream.of(
                arguments("pdd", "拼多多"),
                arguments("jd", "京东")
        );
    }

    //修改成从yaml中获取数据例子
//    @ParameterizedTest
//    @MethodSource("YamlData")
//    public void 搜索股票2(String searchInfo,String exceptName)   {
//        String name = searchpage.inputSearchInfo(searchInfo).getAll().get(0);
//        assertThat(name,equalTo(exceptName));
//
//    }

//    static Stream<Arguments> YamlData(){
//        GlobalConfig config =GlobalConfig.load("/data/globalConfig.yaml");
//        List<Arguments> list = new ArrayList<>();
//        Arguments arguments = null;
//        for (String key : config.xqDemoConfig.testdata.keySet()) {
//            Object value = config.xqDemoConfig.testdata.get(key);
//            arguments = arguments(key, value);
//            list.add(arguments);
//        }
//        return Stream.of(list.get(0),list.get(1),list.get(2));
//    }


}
