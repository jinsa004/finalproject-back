package shop.mtcoding.finalproject.domain.store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s join fetch Like l on l.store.id = s.id join fetch User u on u.id = l.user,id where u.id = :userId")
    Optional<Store> findByUserIdToLikeStore(@Param("userId") Long userId);

    @Query("select s from Store s join fetch s.user u where s.user.id = :userId and s.isClosure = false")
    Optional<Store> findByUserId(@Param("userId") Long userId);

    @Query("select s from Store s where id = :id and s.isClosure = false")
    Optional<Store> updateById(@Param("id") Long id);

    @Query("select s from Store s where s.businessAddress = :address")
    List<Store> findByBusinessAddress(String address);

    @Query(value = "select avg(cr.star_point), s.name, s.thumbnail, s.intro, s.delivery_cost from stores s left join customer_reviews cr on cr.store_id = s.id", nativeQuery = true)
    List<Store> storeListAverageStarPoint();

}
