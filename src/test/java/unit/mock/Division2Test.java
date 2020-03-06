package unit.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Division2Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void divid() {
    }

    @Test
    public void divid2() {
        Division2 mock2 = mock(Division2.class);
        when(mock2.divid2(anyInt(),anyInt(),anyInt())).thenCallRealMethod();//mock2.divid2走原实际代码方法
        when(mock2.divid(anyInt(),anyInt())).thenReturn(10);//只mock了divid这个方法
        assertThat(mock2.divid2(1,1,2),equalTo(5));//10除以2等5

    }
}