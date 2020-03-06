package unit;

import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJunit4Demo1 extends TestJunit4Demo {
    @BeforeClass
    public static void testBeforAllChildren(){
        System.out.println("chrldren_beforeAllTestCase");
    }
    @Before
    public void testbeforChildren(){
        System.out.println("TestJUnit4DemoChildren i am @befor");
    }
    @Test
    public void testDemo1Children(){
        assertTrue( true);
        System.out.println("TestJUnit4DemoChildren testdemo1");
    }
    @Test
    public void testDemo2Children(){
        System.out.println("TestJUnit4DemoChildren testdemo2");
        assertTrue(true);
    }
    @After
    public void testAfterchildren(){
        System.out.println("TestJUnit4DemoChildren i am @After");
    }
    @AfterClass
    public static void testAfterAllchildren(){
        System.out.println("children_AfterAllTestCase");
    }

}
