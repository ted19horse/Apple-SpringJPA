package ted.applespringjpa.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    // @Query(value = "SELECT s FROM Sales s JOIN FETCH s.member")
    // List<Sales> customFindAll();

    @Query(value = "SELECT new ted.applespringjpa.sales.SalesDTO(s.itemName, s.price, s.member.displayName) FROM Sales s JOIN s.member")
    List<SalesDTO> customFindAll();
}
