package com.yurt.patterns;

import com.yurt.model.Staff;
import com.yurt.model.Student;
import com.yurt.model.User;

public class UserFactory {

    public static User createUser(String role, int id, String tcNo, String name, String surname, String password) {
        if (role == null) {
            return null;
        }

        if (role.equalsIgnoreCase("STUDENT")) {
            // Builder Deseni ile nesne üretimi
            return new StudentBuilder()
                    .setId(id)
                    .setTcNo(tcNo)
                    .setName(name)
                    .setSurname(surname)
                    .setPassword(password)
                    .build();
        } else if (role.equalsIgnoreCase("STAFF")) {
            // Staff için standart üretim
            return new Staff(id, tcNo, name, surname, password);
        }
        
        return null;
    }
}