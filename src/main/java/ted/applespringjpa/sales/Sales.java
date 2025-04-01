package ted.applespringjpa.sales;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import ted.applespringjpa.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String itemName;
    Integer price;
    Integer count;
//    Long memberId;

    @CreationTimestamp
    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false,
                foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Member member;
}
