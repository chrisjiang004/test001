package unit;

import org.junit.*;
import org.junit.experimental.categories.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGroup {
    @Category(SlowGroup.class) //需要一个标记对象 类或者接口 不需要实现任何方法
    @Test
    public void test001(){
        assertTrue(true);
    }

    @Category(FastGroup.class) //fast分组标签
    @Test
    public void test002(){
        assertEquals("message",10,10);
        assertTrue(true);
    }

    @Category({FastGroup.class, SlowGroup.class}) //fast和slow2个分组标签
    @Test
    public void test003(){
        assertTrue(true);
        assertThat("等于10",10,equalTo(10));//等于10 hamcrest风格的断言
        assertThat("接近10 closeto匹配器",10.1,closeTo(10,02));
        assertThat("期望值是多个使用anyof",9.8,anyOf(closeTo(10,0.2),equalTo(9.7)));
    }

    @Category(SlowGroup.class)
    @Test
    public void test004(){
        assertTrue(true);
    }

}
