package unit.mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

//测试覆盖率
public class TestDivision {
    Division division = new Division();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDivided() {
        assertThat(division.divided(4,2),equalTo(2));
    }

    @Test
    public void dividedbyZero() {
        assertThat(division.divided(1,0),equalTo(0));
    }

    @Test
    public void dividedbyException() {
        assertThat(division.divided(1,4),equalTo(0));
    }
}