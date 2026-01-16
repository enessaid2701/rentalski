# MySQL Root Şifresi Değiştirme - Adım Adım

## Sunucuya Bağlanma

### 1. SSH ile Sunucuya Bağlan
```bash
ssh -p 23422 root@178.157.15.185
# Şifre: 4B7SWKpt6NkaFym
```

## MySQL Container'ına Giriş

### 2. MySQL Container'ının Çalıştığını Kontrol Et
```bash
docker ps | grep renralski-db
```

Eğer container çalışmıyorsa:
```bash
cd /root/renralski
docker-compose up -d db
```

### 3. MySQL Container'ına Bağlan
```bash
docker exec -it renralski-db mysql -uroot -proot
```

Bu komut:
- `docker exec -it` → Container içinde komut çalıştır
- `renralski-db` → Container adı
- `mysql -uroot -proot` → MySQL'e root kullanıcısı ile bağlan (şifre: root)

## MySQL İçinde Şifre Değiştirme

### 4. MySQL Komut Satırında Şifreyi Değiştir

MySQL'e girdikten sonra (mysql> prompt'u görünce):

```sql
-- 1. Root kullanıcısının şifresini değiştir (tüm host'lar için)
ALTER USER 'root'@'%' IDENTIFIED BY 'Renralski2024!Secure#Pass';

-- 2. Localhost için de değiştir
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Renralski2024!Secure#Pass';

-- 3. Değişiklikleri uygula
FLUSH PRIVILEGES;

-- 4. Çıkış yap
EXIT;
```

**Not:** `Renralski2024!Secure#Pass` yerine kendi güçlü şifrenizi yazın.

## Şifrenin Değiştiğini Test Et

### 5. Yeni Şifre ile Bağlanmayı Dene
```bash
docker exec -it renralski-db mysql -uroot -pRenralski2024!Secure#Pass
```

Eğer bağlanabiliyorsanız şifre başarıyla değişti demektir.

## Docker Compose ve Application Dosyalarını Güncelle

### 6. application-prod.yaml Dosyasını Güncelle

Lokal bilgisayarınızda `src/main/resources/application-prod.yaml` dosyasını açın:

```yaml
spring:
    profiles:
        active: prod
    datasource:
        url: jdbc:mysql://db:3306/rentalski?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
        username: root
        password: Renralski2024!Secure#Pass  # <-- YENİ ŞİFRE
```

### 7. docker-compose.yml Dosyasını Güncelle

`docker-compose.yml` dosyasını açın:

```yaml
  db:
    image: mysql:8.0
    container_name: renralski-db
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: Renralski2024!Secure#Pass  # <-- YENİ ŞİFRE
      # ... diğer ayarlar
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-pRenralski2024!Secure#Pass"]  # <-- YENİ ŞİFRE
```

## Deploy Et

### 8. Değişiklikleri Commit ve Push Et
```bash
git add src/main/resources/application-prod.yaml docker-compose.yml
git commit -m "Security: Change MySQL root password"
git push origin master
```

GitHub Actions otomatik deploy edecek.

## Özet Komutlar (Kopyala-Yapıştır)

```bash
# 1. Sunucuya bağlan
ssh -p 23422 root@178.157.15.185

# 2. MySQL container'ına gir
docker exec -it renralski-db mysql -uroot -proot

# 3. MySQL içinde (mysql> prompt'unda):
ALTER USER 'root'@'%' IDENTIFIED BY 'Renralski2024!Secure#Pass';
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Renralski2024!Secure#Pass';
FLUSH PRIVILEGES;
EXIT;

# 4. Yeni şifre ile test et
docker exec -it renralski-db mysql -uroot -pRenralski2024!Secure#Pass
```

## Sorun Giderme

### MySQL'e bağlanamıyorum
```bash
# Container'ın çalıştığını kontrol et
docker ps -a | grep renralski-db

# Container çalışmıyorsa başlat
docker start renralski-db
```

### Şifre değişikliği çalışmıyor
```bash
# Container'ı yeniden başlat
docker restart renralski-db

# Tekrar dene
docker exec -it renralski-db mysql -uroot -pYeniŞifre
```

### Eski şifre ile hala giriş yapabiliyorum
```bash
# Container'ı tamamen yeniden başlat
docker-compose restart db
```

