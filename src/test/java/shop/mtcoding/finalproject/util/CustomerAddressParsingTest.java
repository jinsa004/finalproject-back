package shop.mtcoding.finalproject.util;

import org.junit.jupiter.api.Test;

public class CustomerAddressParsingTest {

    @Test
    public void addressParsing_test() throws Exception {
        // given
        String address = "부산시 진구 서면 17번 길";

        // when
        String result = CustomAddressParsingUtil.AddressParsingToArea(address);

        // then
        if (result.equals("부산시 진구")) {
            System.out.println("parsing OK");
        }
    }
}
