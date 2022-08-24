package hidn.navada.product;

import lombok.Data;

@Data
public class ProductSearchDto {
    private long productId;
//    private String productImageUrl;
    private String productName;
    private String userNickname;
    private int productCost;
    private int exchangeCostRange;
    private int productStatusCd;
    private boolean like;

    ProductSearchDto(Product product, boolean like){
        productId=product.getProductId();
        productName=product.getProductName();
        userNickname=product.getUserNickname();
        productCost=product.getProductCost();
        exchangeCostRange=product.getExchangeCostRange();
        productStatusCd=product.getProductStatusCd();
        this.like=like;
    }
}