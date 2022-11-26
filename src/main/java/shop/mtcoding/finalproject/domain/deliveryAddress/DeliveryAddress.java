package shop.mtcoding.finalproject.domain.deliveryAddress;

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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "delivery_address")
@Entity
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String address;

    @Column(nullable = false)
    private Long userId;

    @Builder
    public DeliveryAddress(Long id, String address, Long userId) {
        this.id = id;
        this.address = address;
        this.userId = userId;
    }

}
