package hidn.navada.product;

import lombok.Data;

@Data
public class ProductParams {
    private String productName;
    private String productExplanation;
    private String category;
    private int productCost;
    private int exchangeCostRange;
}
