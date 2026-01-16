# Portainer Environment Ayarlama

Portainer'da container'ları görmek için Docker environment'ını eklemeniz gerekiyor.

## Adım Adım Çözüm

### 1. Environments Sekmesine Gidin

Portainer arayüzünde:
- Sol menüden **"Environments"** sekmesine tıklayın
- Veya üstteki **"Environment: / None selected"** yazısına tıklayın

### 2. Yeni Environment Ekle

1. **"+ Add environment"** butonuna tıklayın
2. **"Docker"** seçeneğini seçin
3. **"Docker Standalone"** seçeneğini seçin
4. **"Start Wizard"** butonuna tıklayın

### 3. Docker Socket Ayarları

1. **Name:** `Local Docker` (veya istediğiniz bir isim)
2. **Docker API URL:** `unix:///var/run/docker.sock` (varsayılan olarak gelir)
3. **Public IP:** `178.157.15.185` (sunucu IP'niz)
4. **"Connect"** butonuna tıklayın

### 4. Environment'ı Seçin

1. Environment eklendikten sonra, sol üstteki **"Environment"** dropdown'ından yeni eklediğiniz environment'ı seçin
2. Artık container'larınızı görebilmelisiniz!

## Alternatif: Hızlı Çözüm

Eğer yukarıdaki adımlar çalışmazsa, Portainer'ı yeniden başlatıp ilk kurulumu tekrar yapın:

```bash
# Sunucuda çalıştırın
docker stop portainer
docker rm portainer

# Portainer'ı yeniden başlat
docker run -d \
  --name portainer \
  --restart=always \
  -p 9000:9000 \
  -p 9443:9443 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v portainer_data:/data \
  portainer/portainer-ce:latest
```

Sonra Portainer'a tekrar giriş yapın ve ilk kurulumda:
- Admin kullanıcısı oluşturun
- **"Docker"** seçeneğini seçin
- **"Connect"** butonuna tıklayın

## Kontrol

Environment eklendikten sonra:
- Sol menüden **"Home"** sekmesine gidin
- Container'larınızı görebilmelisiniz:
  - `renralski-app`
  - `renralski-db`
  - `portainer`

## Sorun Giderme

### Container'lar hala görünmüyor

1. **Docker socket kontrolü:**
```bash
# Sunucuda çalıştırın
ls -la /var/run/docker.sock
```

2. **Container'ların çalıştığını kontrol edin:**
```bash
docker ps
```

3. **Portainer loglarını kontrol edin:**
```bash
docker logs portainer
```

### Environment eklenemiyor

1. Portainer'ı yeniden başlatın
2. Tarayıcı cache'ini temizleyin
3. Farklı bir tarayıcı deneyin

