package shop.mtcoding.bank.domain.delivery.menu;

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
import shop.mtcoding.bank.domain.delivery.AudingTime;

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
    private Long store_id;

    @Column(nullable = true, length = 6)
    private String price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean is_closure;

    @Builder
    public Menu(Long id, String name, String thumbnail, String intro, Long store_id, String price, String category,
            boolean is_closure) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.intro = intro;
        this.store_id = store_id;
        this.price = price;
        this.category = category;
        this.is_closure = is_closure;
    }

}
