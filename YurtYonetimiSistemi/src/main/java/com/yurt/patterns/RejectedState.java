package com.yurt.patterns;

public class RejectedState implements PermissionState {
    @Override
    public String getStateName() {
        return "REDDEDİLDİ ❌";
    }

    @Override
    public void handle() {
        System.out.println("Üzgünüz, izin talebiniz reddedildi. Yönetici ile görüşünüz.");
    }
}
