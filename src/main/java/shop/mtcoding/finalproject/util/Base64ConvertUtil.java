package shop.mtcoding.finalproject.util;

public class Base64ConvertUtil {

    public static byte[] convertToByte(String image) {
        if (image.isEmpty() || image.equals(""))
            return null;

        byte[] bytes = image.getBytes();
        return bytes;
    }

    public static String convertToString(byte[] image) {
        if (image == null)
            return "";

        String coverted = new String(image);
        return coverted;
    }
}
