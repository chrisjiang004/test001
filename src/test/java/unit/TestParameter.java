package unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TestParameter {
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {1,2},
                {2,2},
                {3,4},
                {4,4}
        });
    }
    @Parameterized.Parameter(0)
    public int actual;
    @Parameterized.Parameter(1)
    public  int expection;

    @Test
    public void testParaDemo(){
        assertThat("参数化断言",actual,equalTo(expection));
    }

}








