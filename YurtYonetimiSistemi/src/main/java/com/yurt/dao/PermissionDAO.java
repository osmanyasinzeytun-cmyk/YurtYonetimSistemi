package com.yurt.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.yurt.config.DatabaseConnection;
import com.yurt.model.Permission;

public class PermissionDAO {

    // 1. İzni Veritabanına Ekle (Öğrenci için)
    public void addPermission(Permission perm) {
        String query = "INSERT INTO permissions (student_id, reason, status, start_date, end_date) VALUES (?, ?, ?, CURDATE(), CURDATE())";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, perm.getStudentId());
            ps.setString(2, perm.getReason());
            ps.setString(3, "PENDING"); 
            
            ps.executeUpdate();
            System.out.println("İzin başarıyla kaydedildi.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Bekleyen İzinleri Listele (Personel için - YENİ EKLENDİ)
    public List<String> getPendingPermissions() {
        List<String> list = new ArrayList<>();
        // İzni isteyen öğrencinin adını da çekmek için JOIN işlemi yapıyoruz
        String query = "SELECT p.id, u.name, u.surname, p.reason FROM permissions p " +
                       "JOIN users u ON p.student_id = u.id " +
                       "WHERE p.status = 'PENDING'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Listeye şu formatta ekliyoruz: "5 - Ahmet Yılmaz (Hastane)"
                String info = rs.getInt("id") + " - " + 
                              rs.getString("name") + " " + rs.getString("surname") + 
                              " (" + rs.getString("reason") + ")";
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 3. İzin Durumunu Güncelle (Onayla/Reddet - YENİ EKLENDİ)
    public void updateStatus(int permId, String newStatus) {
        String query = "UPDATE permissions SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, newStatus);
            ps.setInt(2, permId);
            ps.executeUpdate();
            System.out.println("İzin durumu güncellendi: " + newStatus);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // --- YENİ EKLENEN METOD: Öğrencinin Son İznini Getir ---
    public Permission getLatestPermission(int studentId) {
        Permission perm = null;
        // En son eklenen izni bulmak için ID'ye göre tersten sıralayıp ilkini alıyoruz
        String query = "SELECT * FROM permissions WHERE student_id = ? ORDER BY id DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String reason = rs.getString("reason");
                String statusStr = rs.getString("status"); // "APPROVED", "REJECTED" veya "PENDING"
                
                // Nesneyi oluştur (Varsayılan olarak PENDING başlar)
                perm = new Permission(studentId, reason);
                
                // --- STRATEJİ: Veritabanındaki yazıyı Java State Sınıfına Çeviriyoruz ---
                if (statusStr.equals("APPROVED")) {
                    perm.setState(new com.yurt.patterns.ApprovedState());
                } else if (statusStr.equals("REJECTED")) {
                    perm.setState(new com.yurt.patterns.RejectedState());
                } 
                // Eğer PENDING ise zaten varsayılan olarak öyle, dokunmaya gerek yok.
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perm; // İzin varsa nesne döner, yoksa null döner
    }
}