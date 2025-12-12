package com.yurt.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.yurt.dao.UserDAO;
import com.yurt.model.User;

public class LoginView extends JFrame {
    
    private JTextField txtTcNo;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        // Pencere Ayarları
        setTitle("Yurt Otomasyonu - Giriş");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında açılır
        setLayout(new GridLayout(4, 2, 10, 10)); // Izgara düzeni

        // Bileşenler
        add(new JLabel("   TC Kimlik No:"));
        txtTcNo = new JTextField();
        add(txtTcNo);

        add(new JLabel("   Şifre:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("")); // Boşluk
        btnLogin = new JButton("GİRİŞ YAP");
        add(btnLogin);

        // Buton Tıklama Olayı
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        setVisible(true);
    }

    private void handleLogin() {
        String tc = txtTcNo.getText();
        String pwd = new String(txtPassword.getPassword());

        UserDAO dao = new UserDAO();
        // Veritabanından kullanıcıyı sorgula
        User user = dao.login(tc, pwd);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Hoşgeldin " + user.getName() + " (" + user.getRole() + ")");
            this.dispose(); // Giriş ekranını kapat
            user.showDashboard(); // Öğrenciyse öğrenci, personelse personel panelini aç (Polimorfizm)
        } else {
            JOptionPane.showMessageDialog(this, "Hatalı TC veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}