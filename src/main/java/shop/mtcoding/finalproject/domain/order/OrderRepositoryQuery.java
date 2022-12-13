package shop.mtcoding.finalproject.domain.order;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.finalproject.dto.order.OrderReqDto.FindStatsReqDto;
import shop.mtcoding.finalproject.dto.order.OrderStatsRespDto;

@Repository
public class OrderRepositoryQuery {

    @Autowired
    private EntityManager em;

    public OrderStatsRespDto findAllOrderStatsByStoreId(FindStatsReqDto findStatsReqDto) {

        StringBuffer sb = new StringBuffer();
        sb.append("select count(o.id) order_count, ");
        sb.append("(select sum(m.price) from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id ");
        sb.append("where o.store_id = :storeId ");
        sb.append("and o.state = 'COMPLETE' ");
        sb.append("and o.created_at >= :startTime ");
        sb.append("and o.created_at <= :endTime) order_amount, ");
        sb.append("(select sum(m.price) / 10 from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id ");
        sb.append("where o.store_id = :storeId ");
        sb.append("and o.state = 'COMPLETE' ");
        sb.append("and o.created_at >= :startTime ");
        sb.append("and o.created_at <= :endTime) order_expense_amount, ");
        sb.append("(select count(id) from orders ");
        sb.append("where delivery_state_enum = 'DELIVERY' and store_id = :storeId ");
        sb.append("and state = 'COMPLETE' ");
        sb.append("and created_at >= :startTime ");
        sb.append("and created_at <= :endTime) delivery_count, ");
        sb.append("(select sum(m.price) from order_details od ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id ");
        sb.append("where o.store_id = :storeId and o.delivery_state_enum = 'DELIVERY' ");
        sb.append("and o.state = 'COMPLETE' ");
        sb.append("and o.created_at >= :startTime ");
        sb.append("and o.created_at <= :endTime) delivery_amount, ");
        sb.append("(select count(id) from orders ");
        sb.append("where delivery_state_enum = 'TAKEOUT' and store_id = :storeId ");
        sb.append("and state = 'COMPLETE' ");
        sb.append("and created_at >= :startTime ");
        sb.append("and created_at <= :endTime) take_out_count, ");
        sb.append("(select sum(m.price) from order_details od  ");
        sb.append("left outer join menus m on m.id = od.menu_id ");
        sb.append("left outer join orders o on o.id = od.order_id where o.store_id = :storeId ");
        sb.append("and o.state = 'COMPLETE' ");
        sb.append("and o.created_at >= :startTime ");
        sb.append("and o.created_at <= :endTime) take_out_amount ");
        sb.append("from orders o left outer join stores s on s.id = o.store_id ");
        sb.append("where s.id = :storeId ");
        sb.append("and o.state = 'COMPLETE' ");
        sb.append("and o.created_at >= :startTime ");
        sb.append("and o.created_at <= :endTime ");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter("storeId", findStatsReqDto.getStoreId())
                .setParameter("startTime", findStatsReqDto.getStartTime() + " 00:00:00")
                .setParameter("endTime", findStatsReqDto.getEndTime() + " 23:59:59");

        JpaResultMapper result = new JpaResultMapper();

        OrderStatsRespDto orderStatsRespDto = result.uniqueResult(query, OrderStatsRespDto.class);
        return orderStatsRespDto;
    }
}
