package shop.mtcoding.finalproject.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.finalproject.dto.user.UserReqDto.UpdateUserReqDto;
import shop.mtcoding.finalproject.dto.user.UserRespDto.JoinRespDto;
import shop.mtcoding.finalproject.dto.user.UserRespDto.UpdateUserRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
        log.debug("디버그 : 서비스 회원가입 실행됨");

        // 1. 비밀번호 암호화
        String rawPassword = joinReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        joinReqDto.setPassword(encPassword);
        // 2. 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity());
        // 3. DTO 응답
        return new JoinRespDto(userPS);
    }

    @Transactional
    public UpdateUserRespDto 회원수정(UpdateUserReqDto updateUserReqDto) {
        Optional<User> userOP = userRepository.findById(updateUserReqDto.getId());
        if (!userOP.isPresent()) {
            userRepository.findById(updateUserReqDto.getId())
                    .orElseThrow(() -> (new CustomApiException("해당 id의 유저 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)));
        }
        User userPS = userRepository.save(updateUserReqDto.toEntity());
        return new UpdateUserRespDto(userPS);
    }

}
