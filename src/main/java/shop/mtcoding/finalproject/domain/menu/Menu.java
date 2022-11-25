package shop.mtcoding.finalproject.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.domain.AudingTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "menus")
@Entity
public class Menu extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = true)
    private String thumbnail; // 사진타입?

    @Column(nullable = true, length = 100)
    private String intro;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = true, length = 6)
    private String price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean isClosure;

    @Builder
    public Menu(Long id, String name, String thumbnail, String intro, Long storeId, String price, String category,
            boolean isClosure) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.intro = intro;
        this.storeId = storeId;
        this.price = price;
        this.category = category;
        this.isClosure = isClosure;
    }

}
