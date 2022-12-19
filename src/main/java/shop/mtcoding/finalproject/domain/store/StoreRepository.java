package shop.mtcoding.finalproject.domain.store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s join fetch s.user u where s.user.id = :userId")
    Optional<Store> findByUserIdToStoreCheck(@Param("userId") Long userId);

    // 실제 사용자가 가게 목록보기에 사용되는 가게 목록보기 기능 (가게 셀렉)
    @Query("select s from Store s where isClosure = false and isAccept = true and isOpend = true")
    List<Store> findAllToAcceptStoreList();

    // 카테고리별 가게목록보기 - 원본
    // @Query("select s from Store s where category = :category and isClosure =
    // false and isAccept = true")
    // List<Store> findAllByCategory(@Param("category") StoreCategoryEnum category);

    // 카테고리별 가게목록보기 - 원본 + 주소파싱
    @Query("select s from Store s where category = :category and isClosure = false and isAccept = true and s.businessAddress like :address")
    List<Store> findAllByCategory(@Param("category") StoreCategoryEnum category, @Param("address") String adress);

    @Query("select s from Store s join fetch s.user u where s.user.id = :userId and s.isClosure = false")
    Optional<Store> findByUserId(@Param("userId") Long userId);

    @Query("select s from Store s where id = :id and s.isClosure = false")
    Optional<Store> updateById(@Param("id") Long id);

    @Query("select s from Store s where s.businessAddress = :address")
    List<Store> findByBusinessAddress(String address);

}
