package shop.mtcoding.finalproject.util;

public class Base64ConvertUtil {

    public static byte[] convertToByte(String image) {
        try {
            byte[] bytes = image.getBytes();
            return bytes;

        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToString(byte[] image) {
        if (image == null)
            return "";

        String coverted = new String(image);
        return coverted;
    }
}
