package shop.mtcoding.finalproject.domain.menu;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m join fetch m.store s join fetch s.user u where m.id = :menuId")
    Optional<Menu> findByMenuId(@Param("menuId") Long menuId);

    @Query("select m from Menu m join fetch m.store s join fetch s.user u")
    List<Menu> findAll();
}
