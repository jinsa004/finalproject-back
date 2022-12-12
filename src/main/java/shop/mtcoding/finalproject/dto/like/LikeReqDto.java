package shop.mtcoding.finalproject.dto.like;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;

@Getter
@Setter
public class LikeReqDto {

    private Long userId;
    private Long storeId;

    public Like toEntity(User user, Store store) {
        return Like.builder()
                .user(user)
                .store(store)
                .build();
    }

}
