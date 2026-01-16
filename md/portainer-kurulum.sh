#!/bin/bash

# Portainer Kurulum Scripti
# Portainer, Docker container'larını yönetmek için web arayüzü

set -e

echo "=========================================="
echo "Portainer Kurulumu Başlatılıyor..."
echo "=========================================="

# Portainer volume oluştur (verileri saklamak için)
echo "1. Portainer volume oluşturuluyor..."
docker volume create portainer_data

# Portainer container'ını başlat
echo "2. Portainer container başlatılıyor..."
docker run -d \
  --name portainer \
  --restart=always \
  -p 9000:9000 \
  -p 9443:9443 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v portainer_data:/data \
  portainer/portainer-ce:latest

echo ""
echo "=========================================="
echo "✅ Portainer başarıyla kuruldu!"
echo "=========================================="
echo ""
echo "Portainer'a erişim:"
echo "  HTTP:  http://178.157.15.185:9000"
echo "  HTTPS: https://178.157.15.185:9443"
echo ""
echo "⚠️  ÖNEMLİ: İlk kurulum adımları:"
echo "  1. Portainer'a giriş yapın"
echo "  2. Admin kullanıcısı oluşturun"
echo "  3. 'Docker' seçeneğini seçin"
echo "  4. 'Connect' butonuna tıklayın"
echo "  5. Container'ları görmek için 'Home' sekmesine gidin"
echo ""
echo "Eğer container'lar görünmüyorsa:"
echo "  - Sol üstteki 'Environment' dropdown'ından environment seçin"
echo "  - Veya 'Environments' sekmesinden yeni environment ekleyin"
echo ""
echo "Kullanışlı komutlar:"
echo "  docker logs portainer        - Portainer logları"
echo "  docker restart portainer     - Portainer'ı yeniden başlat"
echo "  docker stop portainer        - Portainer'ı durdur"
echo "  docker start portainer       - Portainer'ı başlat"
echo "=========================================="

