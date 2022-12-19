package shop.mtcoding.finalproject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAddressParsingUtil {

    private final static Logger log = LoggerFactory.getLogger(CustomAddressParsingUtil.class);

    public static String AddressParsingToArea(String address) {

        String result;
        String city = address.substring(0, 4);
        result = city.trim();

        if (city.equals("경상남도")) {
            String ward = address.substring(5, 7);
            result = result + " " + ward.trim();
        } else {
            String ward = address.substring(4, 6);
            result = result + " " + ward.trim();
        }

        return result + '%';
    }
}
