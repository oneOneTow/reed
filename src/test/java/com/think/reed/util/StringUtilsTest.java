package java.com.think.reed.util;

import com.think.reed.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @desc:
 * @author: zhiqing.lu
 * @version: v1.0.0
 * @date: 2020/4/19 18:35
 **/
public class StringUtilsTest {

    @Test
    public void When_Given_blank_str_Should_return_true() {
        boolean isBlank = StringUtils.isBlank("            ");
        Assert.assertTrue(isBlank);
    }
}
