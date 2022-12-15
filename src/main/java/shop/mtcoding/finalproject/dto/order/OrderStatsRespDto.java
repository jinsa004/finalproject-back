package shop.mtcoding.finalproject.dto.order;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderStatsRespDto {

    private BigInteger orderCount;
    private BigInteger orderAmount;
    private BigInteger deliveryCount;
    private BigInteger deliveryAmount;
    private BigInteger takeOutCount;
    private BigInteger takeOutAmount;

}
