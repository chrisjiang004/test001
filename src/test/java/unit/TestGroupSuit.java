package unit;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(SlowGroup.class) //include只执行组
@Categories.ExcludeCategory(FastGroup.class) //exclude排除不执行fastgroup组
@Suite.SuiteClasses({
        TestGroup.class
})

public class TestGroupSuit {
}
