package shop.mtcoding.finalproject.domain.store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s where userId = :userId")
    Optional<Store> findByUserId(@Param("userId") Long userId);

    @Query("select s from Store s where id = :id")
    Optional<Store> updateById(@Param("id") Long id);
}
