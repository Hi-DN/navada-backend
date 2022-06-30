package hidn.navada.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    GIFTICON("GIFTICON"),               // 기프티콘
    ELECTRONICS("ELECTRONICS"),         // 전자기기
    FURNITURE("FURNITURE"),             // 가구
    BABY_SUPPLIES("BABY_SUPPLIES"),     // 유아용품
    SPORTS("SPORTS"),                   // 스포츠
    FOOD("FOOD"),                       // 식품
    HOBBY("HOBBY"),                     // 취미용품
    BEAUTY_SUPPLIES("BEAUTY_SUPPLIES"), // 미용
    FEMALE_CLOTHES("FEMALE_CLOTHES"),   // 여성의류
    MALE_CLOTHES("MALE_CLOTHES"),       // 남성의류
    PET_SUPPLIES("PET_SUPPLIES"),       // 반려동물용품
    BOOKS("BOOKS"),                     // 도서
    TOYS("TOYS"),                       // 장난감
    PLANTS("PLANTS"),                   // 식물
    ETC("ETC");                         // 기타용품
    private final String value;
}
