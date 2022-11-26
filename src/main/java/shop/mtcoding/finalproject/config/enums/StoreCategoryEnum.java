package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreCategoryEnum {
    JOKBALBOSSAM("족발·보쌈"), JJIMTANGJJIGAE("찜·탕·찌개"), DONGGASSHAEILSIC("돈까스·회·일식"),
    PIZZA("피자"), GOGIGUI("고기·구이"), YASIC(""), YANGSIC("양식"), CHICKEN("치킨"), JUNGSIC("중식"),
    ASIAN("아시안"), BAKBANJUKGUKSU("백반·죽·국수"), DOSILAK("도시락"), BUNSIC("분식"), CAFEDESSERT("카페·디저트"), FASTFOOD("패스트푸드");

    private String category;
}
