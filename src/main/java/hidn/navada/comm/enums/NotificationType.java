package hidn.navada.comm.enums;


public enum NotificationType {
    ACCEPTED_NOTI, // 교환 수락 알림
    DENIED_NOTI,  // 교환 거절 알림
    COMPLETE_NOTI, // 교환 완료 요청(1회)
    PERIODIC_COMPLETE_NOTI, // 교환 완료 요청(주기)
    PRODUCT_DELETION_NOTI, // 상품 삭제 알림
    EXCHANGE_CANCELED_NOTI // 교환 취소 알림
}
