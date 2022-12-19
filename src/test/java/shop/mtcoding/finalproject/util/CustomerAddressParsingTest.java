package shop.mtcoding.finalproject.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerAddressParsingTest {

    private final Logger log = LoggerFactory.getLogger(CustomerAddressParsingTest.class);

    @Test
    public void addressParsing_test() throws Exception {
        // given
        String address = "부산시 진구 서면 17번 길";

        // when
        String result = CustomAddressParsingUtil.AddressParsingToArea(address);

        // then
        log.debug("디버그 : " + result);
        if (result.equals("부산시 진구%")) {
            System.out.println("parsing OK");
        }
    }
}
