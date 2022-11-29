package shop.mtcoding.finalproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.finalproject.dto.user.UserReqDto.UpdateUserReqDto;
import shop.mtcoding.finalproject.dto.user.UserRespDto.JoinRespDto;
import shop.mtcoding.finalproject.dto.user.UserRespDto.UpdateUserRespDto;
import shop.mtcoding.finalproject.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinReqDto joinReqDto) {
        log.debug("디버그 : UserApiController join 실행됨");
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>("회원가입성공", joinRespDto), HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateByUserId(@RequestBody UpdateUserReqDto updateUserReqDto,
            @PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        // 요청 userId값과 세션 값 비교 후 검증
        if (userId != loginUser.getUser().getId()) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        // 핵심로직
        updateUserReqDto.setId(userId);
        UpdateUserRespDto UpdateUserRespDto = userService.회원수정(updateUserReqDto);
        return new ResponseEntity<>(new ResponseDto<>("회원수정성공", UpdateUserRespDto), HttpStatus.OK);
    }

}
