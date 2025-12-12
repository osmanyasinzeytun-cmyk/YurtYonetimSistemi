package com.yurt.model;

public class Staff extends User {

    public Staff(int id, String tcNo, String name, String surname, String password) {
        super(id, tcNo, name, surname, password, "STAFF");
    }

    @Override
    public void showDashboard() {
        // Eski kod: System.out.println(...);
        // YENİ KOD:
        new com.yurt.view.StaffView(this);
    }
}