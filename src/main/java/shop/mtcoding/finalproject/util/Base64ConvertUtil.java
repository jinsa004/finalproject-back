package shop.mtcoding.finalproject.util;

import java.util.Base64;

public class Base64ConvertUtil {

    public static byte[] convertToByte(String image) {
        if (image.isEmpty() || image.equals(""))
            return null;

        byte[] decodingString = Base64.getDecoder().decode(image);
        return decodingString;
    }

    public static String convertToString(byte[] image) {
        if (image == null)
            return null;

        String decodedString = new String(image);
        return decodedString;
    }
}
