package unit.mock;

import mock.Division;
import mock.DivisionMock;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

//测试mockito的mock方法
public class MockTest {
    DivisionMock divi2 = new DivisionMock();
    Division divi1 = new Division();
    Division mock1 = mock(Division.class);//把Division类mock给mock1 类

    @Test
    public void testmock1(){
        when(mock1.divided(10,2)).thenReturn(12);
//        System.out.println(mock1.divided(10,2));
        assertThat(divi2.divid2(10,2,2,mock1),equalTo(null));
    }
    @Test //anyInt 任何参数都mock成10
    public void testmock2(){
        when(mock1.divided(anyInt(),anyInt())).thenReturn(10);
        assertThat(divi2.divid2(10,2,2,mock1),equalTo(5));
    }

}
