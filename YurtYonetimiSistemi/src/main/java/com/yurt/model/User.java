package com.yurt.model;

// Bu sınıf ABSTRACT olduğu için "new User()" diyerek oluşturulamaz.
// Sadece kalıtım (extends) alınarak kullanılabilir. (Hocanın isteği 1)
public abstract class User {
    protected int id;
    protected String tcNo;
    protected String name;
    protected String surname;
    protected String password;
    protected String role;

    public User(int id, String tcNo, String name, String surname, String password, String role) {
        this.id = id;
        this.tcNo = tcNo;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
    }

    // Her kullanıcının bir paneli vardır ama öğrencininki farklı, personelin farklıdır.
    // Bu yüzden bu metodu abstract yaptık. Alt sınıflar bunu doldurmak ZORUNDA.
    public abstract void showDashboard();

    // Getter metodları
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getId() { return id; }
    public String getTcNo() { return tcNo; }
    public String getSurname() { 
        return surname; 
    }
}