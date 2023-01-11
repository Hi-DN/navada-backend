package hidn.navada.product;

import hidn.navada.product.category.Category;
import lombok.Data;

@Data
public class ProductDetailDto {
    private long productId;
    private String productName;
    private String productExplanation;
    private Category category;
    private char productExchangeStatusCd;
    private long heartNum;
    private int productCost;
    private int exchangeCostRange;
    private String productImageUrl;
    private boolean like;

    public ProductDetailDto(Product product, boolean like){
        productId=product.getProductId();
        productName=product.getProductName();
        productExplanation=product.getProductExplanation();
        category=product.getCategory();
        productExchangeStatusCd=product.getProductExchangeStatusCd();
        heartNum=product.getHeartNum();
        productCost=product.getProductCost();
        exchangeCostRange=product.getExchangeCostRange();
        productImageUrl=product.getProductImageUrl();
        this.like=like;
    }
}
