package unit;

import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJunit4Demo {
    @BeforeClass
    public static void testBeforAll(){
        System.out.println("beforeAllTestCase");
    }
    @Before
    public void testbefor(){
        System.out.println("i am @befor");
    }

    @Test
    public void testDemo1(){
        assertTrue( true);
        System.out.println("testdemo1");
    }
    @Test
    public void testDemo2(){
        assertTrue(false);
        System.out.println("testdemo2");
    }

    @After
    public void testAfter(){
        System.out.println("I am @After");
    }
    @AfterClass
    public static void testAfterAll(){
        System.out.println("AfterAllTestCase");
    }

}
