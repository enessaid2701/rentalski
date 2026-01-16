#!/bin/bash

echo "=========================================="
echo "Docker Kurulum Kontrolü"
echo "=========================================="

# Docker kontrolü
echo "1. Docker kontrolü..."
if command -v docker &> /dev/null; then
    echo "✅ Docker kurulu:"
    docker version --format '{{.Server.Version}}'
else
    echo "❌ Docker kurulu değil!"
    exit 1
fi

# Docker Compose kontrolü
echo ""
echo "2. Docker Compose kontrolü..."
if docker compose version &> /dev/null; then
    echo "✅ Docker Compose kurulu:"
    docker compose version
elif command -v docker-compose &> /dev/null; then
    echo "✅ Docker Compose (eski versiyon) kurulu:"
    docker-compose --version
else
    echo "❌ Docker Compose kurulu değil!"
    echo "Kurulum için:"
    echo "  apt-get install -y docker-compose-plugin"
    exit 1
fi

# Docker servis kontrolü
echo ""
echo "3. Docker servis kontrolü..."
if systemctl is-active --quiet docker; then
    echo "✅ Docker servisi çalışıyor"
else
    echo "❌ Docker servisi çalışmıyor!"
    echo "Başlatmak için: systemctl start docker"
    exit 1
fi

# Test container çalıştırma
echo ""
echo "4. Test container çalıştırma..."
if docker run --rm hello-world &> /dev/null; then
    echo "✅ Docker çalışıyor ve container çalıştırabiliyor"
else
    echo "⚠️  Docker container çalıştırma testi başarısız"
fi

echo ""
echo "=========================================="
echo "Kurulum kontrolü tamamlandı!"
echo "=========================================="
echo ""
echo "Kullanışlı komutlar:"
echo "  docker version              - Docker versiyonu"
echo "  docker compose version      - Docker Compose versiyonu"
echo "  docker ps                   - Çalışan container'lar"
echo "  docker images               - Image'lar"
echo "  systemctl status docker     - Docker servis durumu"

