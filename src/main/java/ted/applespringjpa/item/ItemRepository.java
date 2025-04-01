package ted.applespringjpa.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable pageable);
    // List<Item> findAllByTitleContains(String title);

    @Query(value = "SELECT * FROM shop.item WHERE MATCH(title) AGAINST(?1)",  nativeQuery = true)
    Page<Item> fullTextSearch(String searchText, Pageable pageable);
}
