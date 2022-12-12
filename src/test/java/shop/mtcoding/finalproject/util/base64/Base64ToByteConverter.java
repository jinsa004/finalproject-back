package shop.mtcoding.finalproject.util.base64;

import java.util.Base64;

import org.junit.jupiter.api.Test;

import shop.mtcoding.finalproject.util.Base64ConvertUtil;

public class Base64ToByteConverter {

    @Test
    public void convert_test() throws Exception {

        // 프론트에서 이걸 진행 (결과값 : String)
        String test = "1234567tre";

        // 디비에 넣을 때 (결과값 : byte[])
        byte[] resultByte = Base64ConvertUtil.convertToByte(test);
        System.out.println("테스트 : test : " + resultByte);

        // 프론트한테 줄 때 (결과값 : String)
        String resultString = Base64ConvertUtil.convertToString(resultByte);
        System.out.println("테스트 : test : " + resultString);
    }

}
