# Sunucu Kurulum Kılavuzu

Sunucu: 178.157.15.185:23422
User: root
Password: 4B7SWKpt6NkaFym

## 1. Sunucuya Bağlanma

```bash
ssh -p 23422 root@178.157.15.185
# Şifre: 4B7SWKpt6NkaFym
```

## 2. Docker Kurulumu

### Ubuntu/Debian için:

```bash
# Sistem güncellemesi
apt-get update
apt-get upgrade -y

# Gerekli paketleri yükle
apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

# Docker'ın resmi GPG anahtarını ekle
mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Docker repository ekle
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Docker'ı yükle
apt-get update
apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Docker servisini başlat
systemctl start docker
systemctl enable docker

# Docker'ın çalıştığını kontrol et
docker version
docker compose version
```

### CentOS/RHEL için:

```bash
# Sistem güncellemesi
yum update -y

# Gerekli paketleri yükle
yum install -y yum-utils

# Docker repository ekle
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# Docker'ı yükle
yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Docker servisini başlat
systemctl start docker
systemctl enable docker

# Docker'ın çalıştığını kontrol et
docker --version
docker-compose --version
```

## 3. Proje Dizini Oluşturma

```bash
# Dizin oluştur
mkdir -p /root/renralski
cd /root/renralski
```

## 4. docker-compose.yml Dosyası Oluşturma

```bash
nano docker-compose.yml
```

Aşağıdaki içeriği yapıştırın:

```yaml
version: '3.8'

services:
  app:
    image: enessaid2701/renralski:latest
    container_name: renralski-app
    restart: unless-stopped
    ports:
      - "80:80"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - TZ=Europe/Istanbul
    networks:
      - renralski-network
    depends_on:
      - db
    volumes:
      - /tmp:/tmp

  db:
    image: mysql:8.0
    container_name: renralski-db
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: rentalski
      MYSQL_USER: rentalski
      MYSQL_PASSWORD: rentalski
      TZ: Europe/Istanbul
    ports:
      - "3306:3306"
    volumes:
      - renralski-db-data:/var/lib/mysql
      - ./insert_products.sql:/docker-entrypoint-initdb.d/01-products.sql
      - ./insert_user.sql:/docker-entrypoint-initdb.d/02-user.sql
    networks:
      - renralski-network
    command: --default-authentication-plugin=mysql_native_password --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

networks:
  renralski-network:
    driver: bridge

volumes:
  renralski-db-data:
```

Kaydetmek için: `Ctrl+O`, Enter, `Ctrl+X`

## 5. SQL Dosyalarını Oluşturma

### insert_products.sql dosyası:

```bash
nano insert_products.sql
```

Bu dosyanın içeriğini projenizdeki `insert_products.sql` dosyasından kopyalayın.

### insert_user.sql dosyası:

```bash
nano insert_user.sql
```

Bu dosyanın içeriğini projenizdeki `insert_user.sql` dosyasından kopyalayın.

## 6. Docker Hub'a Login

```bash
docker login -u enessaid2701
# Şifre: EnesSaid-2701.
```

## 7. İlk Deploy

```bash
# Docker Compose ile container'ları başlat
docker-compose up -d

# Container durumlarını kontrol et
docker-compose ps

# Logları izle
docker-compose logs -f app
```

## 8. Kontrol ve Test

```bash
# Container'ların çalıştığını kontrol et
docker ps

# Application logları
docker logs renralski-app -f

# Database logları
docker logs renralski-db -f

# Uygulamayı test et
curl http://localhost
# veya tarayıcıdan: http://178.157.15.185
```

## 9. Firewall Ayarları (Gerekirse)

```bash
# UFW kullanıyorsanız
ufw allow 80/tcp
ufw allow 3306/tcp
ufw allow 23422/tcp
ufw reload

# veya iptables
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -A INPUT -p tcp --dport 3306 -j ACCEPT
iptables -A INPUT -p tcp --dport 23422 -j ACCEPT
```

## 10. Otomatik Başlatma

Docker Compose zaten `restart: unless-stopped` ile ayarlı, yani sunucu yeniden başladığında otomatik başlayacak.

## Sorun Giderme

### Docker kurulumu başarısız olursa:
```bash
# Docker servisini kontrol et
systemctl status docker

# Docker loglarını kontrol et
journalctl -u docker
```

### Port çakışması:
```bash
# Port 80 kullanımda mı kontrol et
netstat -tulpn | grep :80

# Eğer kullanılıyorsa, docker-compose.yml'de portu değiştir
# Örnek: "8080:80"
```

### Container başlamıyorsa:
```bash
# Detaylı logları kontrol et
docker-compose logs app
docker-compose logs db

# Container'ı yeniden başlat
docker-compose restart app
```

### Database bağlantı hatası:
```bash
# Database container'ın çalıştığını kontrol et
docker ps | grep renralski-db

# Database'e bağlan
docker exec -it renralski-db mysql -uroot -proot

# Veritabanını kontrol et
SHOW DATABASES;
USE rentalski;
SHOW TABLES;
```

## Sonraki Adımlar

1. GitHub Actions ile otomatik deploy için repository'ye push yapın
2. Her push'ta otomatik olarak yeni versiyon deploy edilecek
3. Manuel güncelleme için:
   ```bash
   cd /root/renralski
   docker-compose pull
   docker-compose up -d --force-recreate
   ```

