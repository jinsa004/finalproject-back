package shop.mtcoding.finalproject.util;

public class CustomBase64ConvertUtil {

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

        String converted = new String(image);
        return converted;
    }
}
