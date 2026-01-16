# Güvenli Şifre Değiştirme ve Kurtarma

## ⚠️ ÖNEMLİ: Güçlü Şifre Seçin!

Güçlü bir şifre örneği: `Renralski2024!Secure#Pass`

## Adım Adım

### 1. Yeni Güçlü Şifre Belirleyin
Örnek: `Renralski2024!Secure#Pass` (en az 16 karakter, büyük/küçük harf, sayı, özel karakter)

### 2. Dosyaları Güncelleyin

**application-prod.yaml:**
```yaml
password: Renralski2024!Secure#Pass  # Yeni şifreniz
```

**docker-compose.yml:**
```yaml
MYSQL_ROOT_PASSWORD: Renralski2024!Secure#Pass  # Yeni şifreniz
```

**healthcheck'te de:**
```yaml
test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-pRenralski2024!Secure#Pass"]
```

### 3. Sunucuda Container'ı Yeniden Başlatın

```bash
# Sunucuda
cd /root/renralski

# Eski container'ı durdur
docker-compose down

# Volume'u sil (tamamen temiz başlangıç için)
docker volume rm renralski-db-data

# Yeni docker-compose.yml'i kopyalayın (GitHub Actions yapacak)
# Sonra yeniden başlat
docker-compose up -d
```

### 4. Deploy Edin

```bash
git add src/main/resources/application-prod.yaml docker-compose.yml
git commit -m "Security: Change MySQL root password to secure password"
git push origin master
```

## Tablolar Otomatik Oluşacak mı?

**EVET!** Çünkü:

✅ `ddl-auto: update` → Tablolar otomatik oluşur
✅ `insert_products.sql` → Ürünler otomatik eklenir
✅ `insert_user.sql` → Kullanıcılar otomatik eklenir
✅ Volume silinirse → Init script'ler çalışır

## Kontrol

```bash
# Database'e bağlan
docker exec -it renralski-db mysql -uroot -pRenralski2024!Secure#Pass

# Kontrol et
USE rentalski;
SHOW TABLES;
SELECT COUNT(*) FROM products;
SELECT COUNT(*) FROM users;
```

