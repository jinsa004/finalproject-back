package shop.mtcoding.finalproject.domain.menu;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.store.Store;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "menus")
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] thumbnail;

    @Column(nullable = true, length = 100)
    private String intro;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuCategoryEnum category;

    @Column(nullable = false)
    public boolean isClosure;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @Builder
    public Menu(Long id, String name, byte[] thumbnail, String intro, int price,
            MenuCategoryEnum category, boolean isClosure, Store store) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.intro = intro;
        this.price = price;
        this.category = category;
        this.isClosure = isClosure;
        this.store = store;
    }

    public void putStore(Store store) {
        this.store = store;
    }

    public Menu update(Menu menu) {
        return Menu.builder()
                .id(id)
                .name(menu.getName())
                .thumbnail(menu.getThumbnail())
                .intro(menu.getIntro())
                .price(menu.getPrice())
                .category(menu.getCategory())
                .isClosure(isClosure)
                .store(store)
                .build();
    }

    public Menu close(Menu menu) {
        return Menu.builder()
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .intro(intro)
                .price(price)
                .category(category)
                .isClosure(menu.isClosure)
                .store(store)
                .build();
    }

    public void checkClosure() {
        if (this.isClosure) {
            throw new CustomApiException("????????? ????????????", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkWriter(Long userId) {
        if (!this.store.getUser().getId().equals(userId)) {
            throw new CustomApiException("????????? ????????????", HttpStatus.BAD_REQUEST);
        }
    }

    public void updateClosure(Boolean closure) {
        this.isClosure = closure;
    }

}
