# Portainer Kurulum Kılavuzu

Portainer, Docker container'larınızı web arayüzü üzerinden yönetmenizi sağlayan bir araçtır.

## Hızlı Kurulum

### Sunucuya Bağlanın
```bash
ssh -p 23422 root@178.157.15.185
```

### Portainer'ı Kurun

#### Yöntem 1: Script ile (Önerilen)
```bash
# Script'i sunucuya kopyalayın (lokal bilgisayarınızdan)
scp -P 23422 portainer-kurulum.sh root@178.157.15.185:/root/

# Sunucuda çalıştırın
ssh -p 23422 root@178.157.15.185
chmod +x /root/portainer-kurulum.sh
/root/portainer-kurulum.sh
```

#### Yöntem 2: Manuel Kurulum
```bash
# Portainer volume oluştur
docker volume create portainer_data

# Portainer container'ını başlat
docker run -d \
  --name portainer \
  --restart=always \
  -p 9000:9000 \
  -p 9443:9443 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v portainer_data:/data \
  portainer/portainer-ce:latest
```

## Portainer'a Erişim

Kurulum tamamlandıktan sonra:

1. **HTTP:** http://178.157.15.185:9000
2. **HTTPS:** https://178.157.15.185:9443

## İlk Kurulum

1. Portainer'a ilk kez giriş yaptığınızda admin kullanıcısı oluşturmanız istenir
2. Kullanıcı adı ve şifre belirleyin (en az 8 karakter)
3. "Get Started" butonuna tıklayın
4. "Docker" seçeneğini seçin
5. "Connect" butonuna tıklayın

## Portainer Özellikleri

- ✅ Container'ları görüntüleme ve yönetme
- ✅ Image'ları görüntüleme ve silme
- ✅ Volume'ları yönetme
- ✅ Network'leri görüntüleme
- ✅ Container loglarını görüntüleme
- ✅ Container'ları başlatma/durdurma/restart etme
- ✅ Yeni container oluşturma
- ✅ Docker Compose dosyalarını yönetme

## Firewall Ayarları

Portainer'ın çalışması için port 9000 ve 9443'ün açık olması gerekir:

```bash
# UFW kullanıyorsanız
ufw allow 9000/tcp
ufw allow 9443/tcp
ufw reload

# veya iptables
iptables -A INPUT -p tcp --dport 9000 -j ACCEPT
iptables -A INPUT -p tcp --dport 9443 -j ACCEPT
```

## Portainer Yönetimi

### Container Durumunu Kontrol Et
```bash
docker ps | grep portainer
```

### Portainer Logları
```bash
docker logs portainer -f
```

### Portainer'ı Yeniden Başlat
```bash
docker restart portainer
```

### Portainer'ı Durdur
```bash
docker stop portainer
```

### Portainer'ı Başlat
```bash
docker start portainer
```

### Portainer'ı Kaldır
```bash
docker stop portainer
docker rm portainer
docker volume rm portainer_data
```

## Güvenlik Notları

1. **HTTPS Kullanın:** Mümkünse HTTPS (9443) portunu kullanın
2. **Güçlü Şifre:** Admin şifresini güçlü tutun
3. **Firewall:** Sadece gerekli IP'lerden erişime izin verin
4. **Düzenli Güncelleme:** Portainer'ı düzenli olarak güncelleyin

## Portainer Güncelleme

```bash
# Eski container'ı durdur ve kaldır
docker stop portainer
docker rm portainer

# Yeni versiyonu çek ve başlat
docker pull portainer/portainer-ce:latest
docker run -d \
  --name portainer \
  --restart=always \
  -p 9000:9000 \
  -p 9443:9443 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v portainer_data:/data \
  portainer/portainer-ce:latest
```

## Sorun Giderme

### Portainer başlamıyor
```bash
# Logları kontrol et
docker logs portainer

# Container durumunu kontrol et
docker ps -a | grep portainer
```

### Port erişilemiyor
```bash
# Port'ların açık olduğunu kontrol et
netstat -tulpn | grep 9000
netstat -tulpn | grep 9443

# Firewall kurallarını kontrol et
ufw status
# veya
iptables -L -n | grep 9000
```

### Docker socket hatası
```bash
# Docker socket izinlerini kontrol et
ls -la /var/run/docker.sock

# Gerekirse izinleri düzelt
chmod 666 /var/run/docker.sock
```

## Docker Compose ile Portainer

Alternatif olarak, Portainer'ı docker-compose.yml dosyasına ekleyebilirsiniz:

```yaml
  portainer:
    image: portainer/portainer-ce:latest
    container_name: portainer
    restart: unless-stopped
    ports:
      - "9000:9000"
      - "9443:9443"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - portainer_data:/data

volumes:
  portainer_data:
```

Sonra:
```bash
docker-compose up -d portainer
```

