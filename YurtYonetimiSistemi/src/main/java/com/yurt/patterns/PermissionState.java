package com.yurt.patterns;

public interface PermissionState {
    // Durumun adını döndürür (Örn: "Beklemede")
    String getStateName();
    
    // Duruma göre mesaj verir
    void handle();
}