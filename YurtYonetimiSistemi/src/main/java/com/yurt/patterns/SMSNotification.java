package com.yurt.patterns;

public class SMSNotification implements Observer {
    @Override
    public void update(String message) {
        // Gerçek hayatta burada Twilio veya bir GSM API'si olurdu.
        // Biz simüle ediyoruz.
        System.out.println("--------------------------------------------------");
        System.out.println("📧 [SMS SERVİSİ]: " + message);
        System.out.println("--------------------------------------------------");
    }
}