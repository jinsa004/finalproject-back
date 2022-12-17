package shop.mtcoding.finalproject.domain.customerReview;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {

        @Query("select c from CustomerReview c where c.order.id = :orderId")
        Optional<CustomerReview> findByOrderId(@Param("orderId") Long orderId);

        @Query(value = "select * from customer_reviews cr left join ceo_reviews cor on cr.order_id = cor.order_id where cr.user_id = :userId", nativeQuery = true)
        List<CustomerReview> findReviewListByUserId(@Param("userId") Long userId);

        @Query(value = "select * from customer_reviews cr left outer join ceo_reviews cor on cor.id = cr.ceo_review_id left outer join orders o on o.id = cr.order_id left outer join stores s on s.id = o.store_id where s.id = :storeId", nativeQuery = true)
        List<CustomerReview> findAllByStoreId(@Param("storeId") Long storeId);

        @Query(value = "update customer_reviews cr set ceo_review_id = :ceoReviewId", nativeQuery = true)
        void updateToCeoReviewId(@Param("ceoReviewId") Long ceoReviewId);

        @Query(value = "select * from customer_reviews cr left join stores s on s.id = cr.store_id", nativeQuery = true)
        List<CustomerReview> starPointAverageToStore();

        // 별점평균, 리뷰갯수 연산데이터 쿼리(가게 상세보기에 사용)
        @Query(value = "select count(cr.id) count, avg(cr.star_point) starPoint from customer_reviews cr where cr.store_id =:storeId group by cr.store_id", nativeQuery = true)
        CustomerReviewInterface findByStoreId(@Param("storeId") Long storeId);

        // 별점평균, 리뷰갯수 연산데이터 쿼리(가게 목록보기에 사용)
        @Query(value = "select count(cr.id) count, avg(cr.star_point) starPoint, s.id storeId, cr.id reviewId from customer_reviews cr right outer join stores s on cr.store_id =s.id group by s.id", nativeQuery = true)
        List<CustomerReviewInterface> findAllByStoreReviewToStarPoint();

        // 가게 상세보기 리뷰 탭에서 뿌려지는 리뷰 데이터
        @Query(value = "select cru.nickname nickname, cru.uPhoto uPhoto, cru.crPhoto crPhoto, cru.content content, cru.star_point starPoint, cor.content comment, cru.order_id orderId "
                        +
                        "from ceo_reviews cor right join (select cr.order_id, cr.photo crphoto, cr.content, cr.star_point, u.nickname, u.photo uphoto from customer_reviews cr "
                        +
                        "right join users u on cr.user_id = u.id where cr.store_id = :storeId) cru on cor.order_id = cru.order_id", nativeQuery = true)
        List<CustomerReviewInterface> findByCustomerReviewToStoreId(@Param("storeId") Long storeId);

        // 가게 리뷰목록보기에서 오더 디테일 안의 메뉴명을 가져오는 것
        @Query(value = "select cr.order_id orderId, om.name menuName from customer_reviews cr right join "
                        + "(select m.name, o.order_id from order_details o inner join menus m on o.menu_id = m.id) "
                        + "om on cr.order_id = om.order_id where cr.store_id = :storeId", nativeQuery = true)
        List<CustomerMenuInterface> findByMenuNameToStoreId(@Param("storeId") Long storeId);

        @Query("select cr from CustomerReview cr join fetch cr.ceoReview c join fetch cr.store s join fetch cr.order o join fetch cr.user u where c.id = :ceoReviewId")
        Optional<CustomerReview> findByCeoReviewId(@Param("ceoReviewId") Long ceoReviewId);

}
