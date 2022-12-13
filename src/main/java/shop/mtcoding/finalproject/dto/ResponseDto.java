package shop.mtcoding.finalproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// http code = 200(get, delete, put), 201(post)
@RequiredArgsConstructor
@Setter
@Getter
public class ResponseDto<T> {
    private final int code;
    private final String msg;
    private final T data;
}
