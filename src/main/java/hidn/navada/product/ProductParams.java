package hidn.navada.product;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductParams {
    @Size(min = 1,max = 20,message = "상품명은 1~20 글자여야 합니다.")
    private String productName;
    @Size(max = 200, message = "상품 설명은 200글자 이내여야 합니다.")
    private String productExplanation;
    @NotNull(message = "카테고리는 필수값입니다.")
    private Long categoryId;
    @NotNull(message = "상품 원가는 필수값입니다.")
    private int productCost;
    @NotNull(message = "교환 희망 가격범위는 필수값입니다.")
    private int exchangeCostRange;
}
