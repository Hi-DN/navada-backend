package hidn.navada.product;

import hidn.navada.comm.enums.Category;
import lombok.Data;

@Data
public class ProductParams {
    private String productName;
    private String productExplanation;
    private Category category;
    private int productCost;
    private int exchangeCostRange;
}
