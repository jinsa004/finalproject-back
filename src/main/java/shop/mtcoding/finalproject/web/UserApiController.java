package shop.mtcoding.finalproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.finalproject.dto.user.UserReqDto.UpdateUserReqDto;
import shop.mtcoding.finalproject.dto.user.UserRespDto.DetailUserRespDto;
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
        JoinRespDto joinRespDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입성공", joinRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/detail")
    public ResponseEntity<?> userDetail(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        DetailUserRespDto detailUserRespDto = userService.detailUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 상세보기 완료", detailUserRespDto), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/update")
    public ResponseEntity<?> updateByUserId(@RequestBody UpdateUserReqDto updateUserReqDto,
            @PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        // 핵심로직
        UpdateUserRespDto UpdateUserRespDto = userService.updateUser(updateUserReqDto, userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원수정성공", UpdateUserRespDto), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/delete")
    public ResponseEntity<?> deleteByUserId(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 비활성화 완료", null), HttpStatus.OK);
    }

}
