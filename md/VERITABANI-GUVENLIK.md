# Veritabanı Güvenlik ve Kurtarma Kılavuzu

## Durum
Veritabanı hacklenmiş ve veriler silinmiş. MySQL root şifresi değiştirilmeli.

## Adım Adım Çözüm

### 1. MySQL Root Şifresini Değiştir (Sunucuda)

```bash
# Sunucuya bağlan
ssh -p 23422 root@178.157.15.185

# MySQL container'ına bağlan
docker exec -it renralski-db mysql -uroot -proot

# MySQL içinde şifreyi değiştir
ALTER USER 'root'@'%' IDENTIFIED BY 'YeniGüçlüŞifre123!';
ALTER USER 'root'@'localhost' IDENTIFIED BY 'YeniGüçlüŞifre123!';
FLUSH PRIVILEGES;
EXIT;
```

### 2. application-prod.yaml Dosyasını Güncelle

```yaml
spring:
    profiles:
        active: prod
    datasource:
        url: jdbc:mysql://db:3306/rentalski?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
        username: root
        password: YeniGüçlüŞifre123!  # <-- YENİ ŞİFRE
        driver-class-name: com.mysql.cj.jdbc.Driver
        initialization-mode: always
    # ... diğer ayarlar
```

### 3. docker-compose.yml Dosyasını Güncelle

```yaml
  db:
    image: mysql:8.0
    container_name: renralski-db
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: YeniGüçlüŞifre123!  # <-- YENİ ŞİFRE
      MYSQL_DATABASE: rentalski
      MYSQL_USER: rentalski
      MYSQL_PASSWORD: rentalski
      # ... diğer ayarlar
```

### 4. Deploy Et

```bash
# Değişiklikleri commit et
git add src/main/resources/application-prod.yaml docker-compose.yml
git commit -m "Security: Change MySQL root password"
git push origin master
```

GitHub Actions otomatik deploy edecek.

## Tablolar Otomatik Oluşacak mı?

**EVET!** Çünkü:

1. **JPA Auto DDL:** `ddl-auto: update` ayarı sayesinde tablolar otomatik oluşur
2. **SQL Init Scripts:** `insert_products.sql` ve `insert_user.sql` dosyaları otomatik çalışır
3. **Volume Korunur:** Eğer volume silinmezse, sadece veriler silinir, tablo yapısı korunur

## Kontrol

Deploy sonrası:

```bash
# Container'ları kontrol et
docker-compose ps

# Database'e bağlan
docker exec -it renralski-db mysql -uroot -pYeniGüçlüŞifre123!

# Veritabanını kontrol et
USE rentalski;
SHOW TABLES;
SELECT * FROM products;
SELECT * FROM users;
```

## Güvenlik Önerileri

1. **Güçlü Şifre Kullan:** En az 16 karakter, büyük/küçük harf, sayı, özel karakter
2. **Firewall:** Sadece gerekli IP'lerden MySQL portuna (3306) erişime izin ver
3. **Backup:** Düzenli yedek al
4. **Root Şifresini Sadece Container İçinde Kullan:** Dışarıdan erişimi kapat

## Eğer Volume da Silinmişse

```bash
# Volume'u sil ve yeniden oluştur
docker-compose down -v
docker volume rm renralski-db-data
docker-compose up -d
```

Bu durumda tüm veriler sıfırdan oluşur.

