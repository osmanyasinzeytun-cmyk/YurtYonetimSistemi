package com.yurt.patterns;

public class ApprovedState implements PermissionState {
    @Override
    public String getStateName() {
        return "ONAYLANDI ✅";
    }

    @Override
    public void handle() {
        System.out.println("Tebrikler! İzin talebiniz onaylandı. Çıkış yapabilirsiniz.");
    }
}