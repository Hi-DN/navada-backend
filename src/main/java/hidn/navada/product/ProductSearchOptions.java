package hidn.navada.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchOptions {
    private String productName;
    private List<Long> categoryIds;
    private Integer lowerCostBound;
    private Integer upperCostBound;
    private Boolean isMyProductIncluded;    // 내 상품 포함해서 보기
    private List<Long> productExchangeStatusCds;    // 상태
}
