package com.yurt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.yurt.config.DatabaseConnection;
import com.yurt.model.User;
import com.yurt.patterns.UserFactory;

public class UserDAO {

    // Kullanıcı Giriş Kontrolü
    public User login(String tcNo, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE tc_no = ? AND password = ?";

        try {
            // Singleton bağlantımızı alıyoruz
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, tcNo);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Veritabanından gelen veriler
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String role = rs.getString("role");

                // FACTORY DESENİ: Rolüne göre (Student/Staff) nesne üretiyor
                user = UserFactory.createUser(role, id, tcNo, name, surname, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
