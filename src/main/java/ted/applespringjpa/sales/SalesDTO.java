package ted.applespringjpa.sales;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SalesDTO {
    private String itemName;
    private Integer price;
    private String displayName;
}
