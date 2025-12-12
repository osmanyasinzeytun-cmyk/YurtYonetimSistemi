package com.yurt.view;

import javax.swing.*;
import java.awt.*;
import com.yurt.dao.PermissionDAO;
import com.yurt.model.Permission;
import com.yurt.model.Student;

public class StudentView extends JFrame {

    private Student student;
    private JLabel lblWelcome;
    private JLabel lblRoomInfo;
    private JButton btnRequestPermission;
    private JTextArea txtStatus;

    public StudentView(Student student) {
        this.student = student;

        // --- Pencere Ayarları ---
        setTitle("Öğrenci Paneli - " + student.getName());
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında açılır
        setLayout(new BorderLayout());

        // --- ÜST PANEL (Bilgiler) ---
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setBorder(BorderFactory.createTitledBorder("Öğrenci Bilgileri"));
        topPanel.setBackground(new Color(230, 240, 255)); // Hafif mavi arka plan

        // User sınıfına eklediğimiz getSurname() metodunu kullanıyoruz
        lblWelcome = new JLabel(" Hoşgeldin, " + student.getName() + " " + student.getSurname());
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblRoomInfo = new JLabel(" Oda: 101 (Örnek)"); 
        
        topPanel.add(lblWelcome);
        topPanel.add(lblRoomInfo);
        add(topPanel, BorderLayout.NORTH);

        // --- ORTA PANEL (Durum Ekranı) ---
        txtStatus = new JTextArea();
        txtStatus.setEditable(false); // Kullanıcı buraya yazı yazamaz
        txtStatus.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtStatus.setText("Yükleniyor...\n");
        
        JScrollPane scrollPane = new JScrollPane(txtStatus);
        scrollPane.setBorder(BorderFactory.createTitledBorder("İzin Durumu"));
        add(scrollPane, BorderLayout.CENTER);

        // --- ALT PANEL (Butonlar) ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnRequestPermission = new JButton("YENİ İZİN TALEBİ OLUŞTUR");
        btnRequestPermission.setFont(new Font("Arial", Font.BOLD, 12));
        btnRequestPermission.setBackground(new Color(50, 150, 50)); // Yeşil buton
        btnRequestPermission.setForeground(Color.WHITE);
        
        // --- BUTON TIKLAMA OLAYI (İzin İste ve Kaydet) ---
        btnRequestPermission.addActionListener(e -> {
            String reason = JOptionPane.showInputDialog(this, "İzin nedeninizi giriniz:");
            
            if (reason != null && !reason.trim().isEmpty()) {
                try {
                    // 1. İzin nesnesini oluştur (Otomatik 'Beklemede' başlar)
                    Permission perm = new Permission(student.getId(), reason);
                    
                    // 2. Veritabanına Kaydet
                    PermissionDAO dao = new PermissionDAO();
                    dao.addPermission(perm); 

                    // 3. Ekrana Bilgi Yaz ve Durumu Güncelle
                    loadCurrentStatus();
                    
                    JOptionPane.showMessageDialog(this, "Talep başarıyla yetkiliye iletildi! ✅");
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        bottomPanel.add(btnRequestPermission);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- AÇILIŞTA SON DURUMU KONTROL ET ---
        loadCurrentStatus(); 

        setVisible(true);
    }

    // Veritabanından son durumu çekip ekrana yazan metod
 // Veritabanından son durumu çekip ekrana yazan metod (GÜNCELLENDİ)
    private void loadCurrentStatus() {
        try {
            PermissionDAO dao = new PermissionDAO();
            Permission lastPerm = dao.getLatestPermission(student.getId());

            // Her durumda butonu açık tutalım
            btnRequestPermission.setEnabled(true);
            btnRequestPermission.setText("YENİ İZİN TALEBİ OLUŞTUR");
            btnRequestPermission.setBackground(new Color(50, 150, 50)); // Yeşil

            if (lastPerm != null) {
                txtStatus.setText("----------------------------------\n" +
                                  "Son Talep Nedeni: " + lastPerm.getReason() + "\n" +
                                  "Güncel Durum: " + lastPerm.getStatusText() + "\n" +
                                  "----------------------------------");
            } else {
                txtStatus.setText("Henüz bir izin talebiniz bulunmamaktadır.");
            }
        } catch (Exception e) {
            txtStatus.setText("Veri çekilirken hata oluştu.");
            e.printStackTrace();
        }
    }
}