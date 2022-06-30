package hidn.navada.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    GIFTICON("GIFTICON"),               // 기프티콘
    ELECTRONICS("ELECTRONICS"),         // 전자기기
    FEMALE_CLOTHES("FEMALE_CLOTHES"),   // 여성의류
    MALE_CLOTHES("MALE_CLOTHES"),       // 남성의류
    PET_SUPPLIES("PET_SUPPLIES"),       // 반려동물용품
    BOOKS("BOOKS"),                     // 도서
    ETC("ETC");                         // 기타용품
    private final String value;
}
