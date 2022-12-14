package shop.mtcoding.finalproject.domain.store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 카테고리별 가게목록보기
    @Query("select s from Store s where category = :category and isClosure = false and isAccept = true")
    List<Store> findAllByCategory(@Param("category") StoreCategoryEnum category);

    @Query("select s from Store s join fetch s.user u where s.user.id = :userId and s.isClosure = false and isAccept = true")
    Optional<Store> findByUserId(@Param("userId") Long userId);

    @Query("select s from Store s where id = :id and s.isClosure = false")
    Optional<Store> updateById(@Param("id") Long id);

    @Query("select s from Store s where s.businessAddress = :address")
    List<Store> findByBusinessAddress(String address);

}
