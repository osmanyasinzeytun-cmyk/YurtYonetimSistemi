package com.yurt.model;

import com.yurt.patterns.PermissionState;
import com.yurt.patterns.PendingState;

public class Permission {
    private int id;
    private int studentId;
    private String reason;
    
    // STATE DESENİ: Durumu bir string değil, Sınıf olarak tutuyoruz!
    private PermissionState state;

    public Permission(int studentId, String reason) {
        this.studentId = studentId;
        this.reason = reason;
        // Yeni oluşturulan izin varsayılan olarak "Beklemede" başlar
        this.state = new PendingState(); 
    }

    // Durumu değiştiren metod (Personel onaylayınca burası çalışacak)
    public void setState(PermissionState newState) {
        this.state = newState;
        this.state.handle(); // Yeni durumun mesajını yazdır
    }

    public String getStatusText() {
        return state.getStateName();
    }
    
    // Getterlar
    public int getStudentId() {
        return studentId;
    }
    public String getReason() { return reason; }
}
