package hidn.navada.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long productId;

    private String productName;

    private int productCost;

    private int exchangeCostRange;

    private boolean hasRequestedToTheirProduct;

    public ProductDto(Product product, boolean hasRequestedToTheirProduct){
        productId=product.getProductId();
        productName = product.getProductName();
        productCost = product.getProductCost();
        exchangeCostRange = product.getExchangeCostRange();
        this.hasRequestedToTheirProduct = hasRequestedToTheirProduct;
    }
}
