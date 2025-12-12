package com.yurt.patterns;

public class PendingState implements PermissionState {
    @Override
    public String getStateName() {
        return "BEKLEMEDE ⏳";
    }

    @Override
    public void handle() {
        System.out.println("İzin talebiniz alındı, yöneticinin onayı bekleniyor.");
    }
}