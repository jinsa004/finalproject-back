package shop.mtcoding.finalproject.service;

import java.util.Optional;

import javax.persistence.NoResultException;

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
import shop.mtcoding.finalproject.dto.user.UserRespDto.DetailUserRespDto;
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
    public JoinRespDto join(JoinReqDto joinReqDto) {
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
    public UpdateUserRespDto updateUser(UpdateUserReqDto updateUserReqDto, Long userId) {
        // 1. 값이 있는지 검증
        Optional<User> userOP = userRepository.findById(userId);
        if (!userOP.isPresent()) {
            userRepository.findById(userId)
                    .orElseThrow(() -> (new CustomApiException("해당 id의 유저 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)));
        }
        // 2. 현재 비밀번호가 맞는지 검증하기 위해 받아온 비밀번호를 인코딩하여 비교하는 로직
        String rawPassword = updateUserReqDto.getOldPassword();
        String checkPassword = passwordEncoder.encode(rawPassword);
        if (userOP.get().getPassword() != checkPassword) {
            throw new CustomApiException("비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST);
        }
        // 3. 새로운 비밀번호 암호화
        String correctPassword = updateUserReqDto.getNewPassword();
        String encPassword = passwordEncoder.encode(correctPassword);
        updateUserReqDto.setNewPassword(encPassword);
        // 4. 수정하기 핵심로직
        User userPS = userOP.get();
        userPS.회원수정(updateUserReqDto);
        return new UpdateUserRespDto(userPS);
    }

    public DetailUserRespDto detailUser(Long userId) {
        // 1. 값이 있는지 검증
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("회원정보가 없습니다", HttpStatus.BAD_REQUEST));
        // 2. DTO 응답
        DetailUserRespDto detailUserRespDto = new DetailUserRespDto(userPS);
        try {
            return detailUserRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        // 1. 회원 값이 존재하는지 검증
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("회원정보가 없습니다.", HttpStatus.BAD_REQUEST));
        // 2. isActive false (계정 비활성화 로직)
        userPS.회원비활성화();
    }
}
