package com.yurt.patterns;

import com.yurt.model.Student;

public class StudentBuilder {
    private int id;
    private String tcNo;
    private String name;
    private String surname;
    private String password;

    // Her özellik için "Zincirleme" metodlar yazıyoruz
    public StudentBuilder setId(int id) {
        this.id = id;
        return this; // Nesnenin kendisini döndürür (Zincirleme için şart)
    }

    public StudentBuilder setTcNo(String tcNo) {
        this.tcNo = tcNo;
        return this;
    }

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public StudentBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    // SON ADIM: Nesneyi inşa et ve teslim et
    public Student build() {
        return new Student(id, tcNo, name, surname, password);
    }
}