package shop.mtcoding.finalproject.domain.store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s join fetch s.user u where s.user.id = :userId")
    Optional<Store> findByUserId(@Param("userId") Long userId);

    @Query("select s from Store s where id = :id")
    Optional<Store> updateById(@Param("id") Long id);

    @Query("select s from Store s join fetch s.user u")
    List<Store> findAllTest();
}
