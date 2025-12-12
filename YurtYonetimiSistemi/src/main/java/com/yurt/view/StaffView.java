package com.yurt.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import com.yurt.dao.PermissionDAO;
import com.yurt.model.Staff;
import com.yurt.patterns.Observer;
import com.yurt.patterns.SMSNotification;
import com.yurt.patterns.SortStrategy;
import com.yurt.patterns.SortAscending;
import com.yurt.patterns.SortDescending;

public class StaffView extends JFrame {

    private Staff staff;
    private DefaultListModel<String> listModel;
    private JList<String> listPermissions;
    private JButton btnApprove, btnReject, btnRefresh;
    private JComboBox<String> cmbSort; // Sıralama kutusu
    
    private PermissionDAO dao;
    private List<Observer> observers;
    
    // STRATEGY DESENİ: Şu anki sıralama stratejisi
    private SortStrategy currentSortStrategy; 

    public StaffView(Staff staff) {
        this.staff = staff;
        this.dao = new PermissionDAO();
        
        // Varsayılan sıralama stratejisi: A-Z
        this.currentSortStrategy = new SortAscending();

        // Observer Listesini Hazırla
        observers = new ArrayList<>();
        observers.add(new SMSNotification());

        // --- Pencere Ayarları ---
        setTitle("Yönetici Paneli - " + staff.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- ÜST PANEL (Başlık ve Sıralama) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JLabel lblTitle = new JLabel("Bekleyen İzin Talepleri", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        topPanel.add(lblTitle, BorderLayout.CENTER);
        
        // Sıralama Seçimi
        JPanel sortPanel = new JPanel();
        sortPanel.add(new JLabel("Sıralama: "));
        String[] sortOptions = {"A-Z Sırala", "Z-A Sırala"};
        cmbSort = new JComboBox<>(sortOptions);
        sortPanel.add(cmbSort);
        topPanel.add(sortPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);

        // --- LİSTE ---
        listModel = new DefaultListModel<>();
        listPermissions = new JList<>(listModel);
        listPermissions.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(listPermissions), BorderLayout.CENTER);

        // --- BUTONLAR ---
        JPanel panelButtons = new JPanel();
        btnRefresh = new JButton("Listeyi Yenile 🔄");
        btnApprove = new JButton("ONAYLA ✅");
        btnReject = new JButton("REDDET ❌");

        panelButtons.add(btnRefresh);
        panelButtons.add(Box.createHorizontalStrut(20));
        panelButtons.add(btnApprove);
        panelButtons.add(btnReject);
        add(panelButtons, BorderLayout.SOUTH);

        // --- OLAYLAR ---
        
        // 1. Sıralama Değişince
        cmbSort.addActionListener(e -> {
            if (cmbSort.getSelectedIndex() == 0) {
                currentSortStrategy = new SortAscending();
            } else {
                currentSortStrategy = new SortDescending();
            }
            loadPermissions(); // Listeyi yeni stratejiye göre tekrar yükle
        });

        // 2. Yenile
        btnRefresh.addActionListener(e -> loadPermissions());
        
        // 3. Onayla
        btnApprove.addActionListener(e -> processPermission("APPROVED"));
        
        // 4. Reddet
        btnReject.addActionListener(e -> processPermission("REJECTED"));

        // Açılışta yükle
        loadPermissions();
        setVisible(true);
    }

    private void loadPermissions() {
        listModel.clear();
        // 1. Veriyi Çek
        List<String> pending = dao.getPendingPermissions();
        
        // 2. STRATEGY DESENİ: Seçili stratejiye göre sırala
        currentSortStrategy.sort(pending);

        // 3. Ekrana Bas
        if (pending.isEmpty()) {
            listModel.addElement("Bekleyen izin talebi yok.");
        } else {
            for (String s : pending) {
                listModel.addElement(s);
            }
        }
    }

    private void processPermission(String newStatus) {
        String selected = listPermissions.getSelectedValue();
        if (selected == null || selected.equals("Bekleyen izin talebi yok.")) {
            JOptionPane.showMessageDialog(this, "Lütfen listeden geçerli bir talep seçin!");
            return;
        }

        try {
            int id = Integer.parseInt(selected.split(" - ")[0]);
            
            // DAO ile güncelle
            dao.updateStatus(id, newStatus);
            
            // Observer ile bildirim gönder
            String message = "İzin ID: " + id + " durumu güncellendi: " + newStatus;
            for (Observer obs : observers) {
                obs.update(message);
            }
            
            JOptionPane.showMessageDialog(this, "İşlem Başarılı: " + newStatus);
            loadPermissions();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage());
        }
    }
}