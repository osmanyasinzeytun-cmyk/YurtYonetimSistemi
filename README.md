# YurtYonetimSistemi
# Yurt Yönetim Sistemi (PRJ-2)

Bu proje, Kırklareli Üniversitesi Yazılım Mimarisi ve Tasarımı dersi kapsamında geliştirilmiş bir Yurt Yönetim Sistemi otomasyonudur.

##  Proje Ekibi
* *Adı Soyadı:* [Osman Yasin Zeytun]
* *Öğrenci No:* [1230505030]
* *GitHub Profili:* [https://github.com/osmanyasinzeytun-cmyk]

* * *Adı Soyadı:* [Burak çavuşoğlu]
* *Öğrenci No:* [5230505068]
* *GitHub Profili:* [https://github.com/0burakcavusoglu]

* * *Adı Soyadı:* [Muhammed Algil]
* *Öğrenci No:* [1230505042]
* *GitHub Profili:* [https://github.com/muhammedalgil]

##  Kullanılan Teknolojiler
* *Dil:* Java (JDK 21)
* *Arayüz:* Java Swing
* *Veritabanı:* MySQL
* *Yapı:* Maven

## Kullanılan Tasarım Desenleri (Design Patterns)
Projede toplam 6 adet tasarım deseni kullanılmıştır:

1.  *Singleton:* Veritabanı bağlantısının tek bir nesne üzerinden yönetilmesi için kullanıldı (DatabaseConnection.java).
2.  *Factory Method:* Kullanıcı girişinde role göre (Öğrenci/Personel) nesne üretimi için kullanıldı (UserFactory.java).
3.  *State:* Öğrenci izin taleplerinin durumlarını (Beklemede, Onaylandı, Reddedildi) yönetmek için kullanıldı (PermissionState.java).
4.  *Observer:* İzin durumu değiştiğinde personele bildirim simülasyonu yapmak için kullanıldı (Observer.java).
5.  *Builder:* Öğrenci nesnesi oluşturulurken kod karmaşasını önlemek için kullanıldı (StudentBuilder.java).
6.  *Strategy:* Personel ekranında izin listesini (A-Z veya Z-A) sıralamak için kullanıldı (SortStrategy.java).

