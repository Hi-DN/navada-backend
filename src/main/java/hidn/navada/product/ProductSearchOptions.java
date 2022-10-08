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
}
