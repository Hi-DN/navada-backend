package hidn.navada.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserLevel {
    LV1_OUTSIDER("LV1_OUTSIDER"),
    LV2_RESIDENT("LV2_RESIDENT"),
    LV3_NATIVE("LV3_NATIVE"),
    LV4_HEADMAN("LV4_HEADMAN");
    private final String value;
}
