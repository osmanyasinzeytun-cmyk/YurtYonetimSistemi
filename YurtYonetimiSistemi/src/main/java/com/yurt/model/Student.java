package com.yurt.model;

import com.yurt.view.StudentView; // Bunu import etmeyi unutma!

public class Student extends User {

    public Student(int id, String tcNo, String name, String surname, String password) {
        super(id, tcNo, name, surname, password, "STUDENT");
    }
   
    
    @Override
    public void showDashboard() {
        // Artık konsola yazı yazmak yerine PENCEREYİ açıyoruz
        new StudentView(this);
    }
}