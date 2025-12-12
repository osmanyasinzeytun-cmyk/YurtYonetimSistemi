package com.yurt.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // 1. Singleton Deseni: Uygulama genelinde tek bir nesne (instance) olacak.
    private static DatabaseConnection instance;
    private Connection connection;

    // Veritabanı ayarları
    private String url = "jdbc:mysql://localhost:3306/yurt_yonetim";
    private String username = "root"; 
    private String password = "123456"; // MySQL şifren (Yoksa tırnak içini boş bırak: "")

    // 2. Private Constructor: Başka sınıfların 'new' ile nesne üretmesini engelleriz.
    private DatabaseConnection() {
        try {
            // MySQL sürücüsünü yükle
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Bağlantıyı aç
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Veritabanı bağlantısı başarılı! ✅");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Veritabanı bağlantısı başarısız! ❌");
            e.printStackTrace();
        }
    }

    // 3. Global Erişim Noktası: Nesneye sadece buradan ulaşılır.
    public static DatabaseConnection getInstance() {
        try {
            // Eğer nesne daha önce oluşturulmadıysa veya bağlantı koptuysa yeniden oluştur.
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    // Bağlantı nesnesini dışarıya verir
    public Connection getConnection() {
        return connection;
    }
}