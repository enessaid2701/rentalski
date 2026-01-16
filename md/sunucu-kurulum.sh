#!/bin/bash

# Renralski Sunucu Kurulum Scripti
# Sunucu: 178.157.15.185:23422

set -e

echo "=========================================="
echo "Renralski Sunucu Kurulumu Başlatılıyor..."
echo "=========================================="

# Renk kodları
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Sistem bilgisi
echo -e "${GREEN}1. Sistem bilgisi kontrol ediliyor...${NC}"
OS=$(cat /etc/os-release | grep "^ID=" | cut -d'=' -f2 | tr -d '"')
echo "İşletim Sistemi: $OS"

# Docker kurulumu kontrolü
if command -v docker &> /dev/null; then
    echo -e "${GREEN}Docker zaten yüklü: $(docker --version)${NC}"
else
    echo -e "${YELLOW}Docker yüklü değil, kurulum başlatılıyor...${NC}"
    
    if [ "$OS" = "ubuntu" ] || [ "$OS" = "debian" ]; then
        echo -e "${GREEN}Ubuntu/Debian için Docker kurulumu...${NC}"
        apt-get update
        apt-get install -y \
            ca-certificates \
            curl \
            gnupg \
            lsb-release
        
        mkdir -p /etc/apt/keyrings
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
        
        echo \
          "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
          $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
        
        apt-get update
        apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
        
    elif [ "$OS" = "centos" ] || [ "$OS" = "rhel" ]; then
        echo -e "${GREEN}CentOS/RHEL için Docker kurulumu...${NC}"
        yum update -y
        yum install -y yum-utils
        yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
        yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    else
        echo -e "${RED}Desteklenmeyen işletim sistemi: $OS${NC}"
        exit 1
    fi
    
    systemctl start docker
    systemctl enable docker
    echo -e "${GREEN}Docker başarıyla yüklendi: $(docker --version)${NC}"
fi

# Docker Compose kontrolü
if command -v docker-compose &> /dev/null; then
    echo -e "${GREEN}Docker Compose zaten yüklü: $(docker-compose --version)${NC}"
else
    echo -e "${YELLOW}Docker Compose yüklü değil, kurulum başlatılıyor...${NC}"
    # Docker Compose plugin olarak yüklü olmalı
    docker compose version
fi

# Proje dizini oluştur
echo -e "${GREEN}2. Proje dizini oluşturuluyor...${NC}"
mkdir -p /root/renralski
cd /root/renralski

# docker-compose.yml kontrolü
if [ ! -f "docker-compose.yml" ]; then
    echo -e "${YELLOW}docker-compose.yml dosyası bulunamadı. Lütfen manuel olarak oluşturun.${NC}"
    echo "Dosya yolu: /root/renralski/docker-compose.yml"
else
    echo -e "${GREEN}docker-compose.yml dosyası mevcut.${NC}"
fi

# SQL dosyaları kontrolü
if [ ! -f "insert_products.sql" ]; then
    echo -e "${YELLOW}insert_products.sql dosyası bulunamadı. Lütfen manuel olarak oluşturun.${NC}"
fi

if [ ! -f "insert_user.sql" ]; then
    echo -e "${YELLOW}insert_user.sql dosyası bulunamadı. Lütfen manuel olarak oluşturun.${NC}"
fi

# Docker Hub login
echo -e "${GREEN}3. Docker Hub'a login olunuyor...${NC}"
echo "Kullanıcı adı: enessaid2701"
echo "Şifre: EnesSaid-2701."
docker login -u enessaid2701 -p EnesSaid-2701. || {
    echo -e "${YELLOW}Docker login başarısız, manuel login gerekebilir.${NC}"
    docker login -u enessaid2701
}

# Container'ları başlat
echo -e "${GREEN}4. Container'lar başlatılıyor...${NC}"
if [ -f "docker-compose.yml" ]; then
    docker-compose pull
    docker-compose up -d
    echo -e "${GREEN}Container'lar başlatıldı!${NC}"
    echo ""
    echo -e "${GREEN}Container durumları:${NC}"
    docker-compose ps
else
    echo -e "${RED}docker-compose.yml dosyası bulunamadı!${NC}"
    echo "Lütfen önce docker-compose.yml dosyasını oluşturun."
    exit 1
fi

echo ""
echo "=========================================="
echo -e "${GREEN}Kurulum tamamlandı!${NC}"
echo "=========================================="
echo "Uygulama: http://178.157.15.185"
echo ""
echo "Kullanışlı komutlar:"
echo "  docker-compose ps          - Container durumları"
echo "  docker-compose logs -f app - Application logları"
echo "  docker-compose logs -f db  - Database logları"
echo "  docker-compose restart app - App'i yeniden başlat"
echo "=========================================="

