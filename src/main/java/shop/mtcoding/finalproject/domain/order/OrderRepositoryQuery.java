package shop.mtcoding.finalproject.domain.order;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.finalproject.dto.order.OrderStatsRespDto;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.FindOrderStatsReqDto;

@Repository
public class OrderRepositoryQuery {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EntityManager em;

    public OrderStatsRespDto findAllOrderStatsByStoreId(FindOrderStatsReqDto findOrderStatsReqDto) {

        StringBuffer sb = new StringBuffer();
        sb.append("select count(o.id) order_count, ");
        sb.append("(select sum(convert(m.price, int)) from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id where o.store_id = :storeId) order_amount, ");
        sb.append("(select sum(convert(m.price, int)) / 10 from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id where o.store_id = :storeId) order_expense_amount, ");
        sb.append("(select count(id) from orders where delivery_state_enum = 'DELIVERY' ");
        sb.append("and store_id = :storeId) delivery_count, ");
        sb.append("(select sum(convert(m.price, int)) from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id where o.store_id = :storeId and ");
        sb.append("o.delivery_state_enum = 'DELIVERY') delivery_amount, ");
        sb.append("(select count(id) from orders where delivery_state_enum = 'TAKEOUT' ");
        sb.append("and store_id = :storeId) take_out_count, ");
        sb.append("(select sum(convert(m.price, int)) from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id where o.store_id = :storeId and ");
        sb.append("o.delivery_state_enum = 'TAKEOUT') take_out_amount ");
        sb.append("from orders o left outer join stores s on s.id = o.store_id where s.id = :storeId");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter("storeId", findOrderStatsReqDto.getStoreId());
        // .setParameter("startTime", findOrderStatsReqDto.getStartTime())
        // .setParameter("endTime", findOrderStatsReqDto.getEndTime());

        JpaResultMapper result = new JpaResultMapper();

        OrderStatsRespDto orderStatsRespDto = result.uniqueResult(query, OrderStatsRespDto.class);
        return orderStatsRespDto;
    }
}
