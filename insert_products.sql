-- Ürünleri products tablosuna ekleme
-- Her ürün için code, displayName ve order_number değerleri

INSERT INTO `rentalski`.`products` (`code`, `displayName`, `order_number`, `created_at`, `updated_at`) VALUES
('KAYAK_TAKIMI', 'KAYAK TAKIMI SKI EQUIPMENT(SKIS, BOOTS, POLES)', 1, NOW(), NOW()),
('SNOWBOARD_TAKIMI', 'SNOWBOARD TAKIMI SKI EQUIPMENT(BOARD, BOOTS)', 2, NOW(), NOW()),
('KAYAK_ELBISESI', 'KAYAK ELBİSESİ SKI SUIT (JACKET, PANTS)', 3, NOW(), NOW()),
('KASK', 'KASK SKI HELMET', 4, NOW(), NOW()),
('GOZLUK', 'GÖZLÜK SKI GOGGLES', 5, NOW(), NOW()),
('BATON', 'BATON SKI POLES', 6, NOW(), NOW()),
('PANTOLON', 'PANTOLON SKI PANTS', 7, NOW(), NOW()),
('MONT', 'MONT SKI JACKET', 8, NOW(), NOW()),
('KIZAK', 'KIZAK (BANT DAHİL) SLED (INCLUDE MOVING WALKWAY)', 9, NOW(), NOW()),
('GUNLUK_DOLAP', 'GÜNLÜK DOLAP CLOSET FOR DAILY RENT', 10, NOW(), NOW()),
('SEZONLUK_DOLAP', 'SEZONLUK DOLAP CLOSET FOR SEASON RENT', 11, NOW(), NOW());

-- Fiyatları prices tablosuna ekleme
-- Her ürün için 4 farklı price type (FULL, STUDENT, GUEST, SPORTMAN) ekleniyor
-- Şimdilik tüm price type'lar için aynı fiyat kullanılıyor
-- product_id değerleri yukarıdaki INSERT'lerin id'lerine göre ayarlanmalı
-- Eğer id'ler otomatik artıyorsa, aşağıdaki sorguları çalıştırmadan önce üstteki INSERT'leri çalıştırın

-- KAYAK_TAKIMI - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_TAKIMI'), 500.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_TAKIMI'), 500.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_TAKIMI'), 500.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_TAKIMI'), 500.00, 'SPORTMAN', NOW(), NOW());

-- SNOWBOARD_TAKIMI - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SNOWBOARD_TAKIMI'), 650.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SNOWBOARD_TAKIMI'), 650.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SNOWBOARD_TAKIMI'), 650.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SNOWBOARD_TAKIMI'), 650.00, 'SPORTMAN', NOW(), NOW());

-- KAYAK_ELBISESI - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_ELBISESI'), 350.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_ELBISESI'), 350.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_ELBISESI'), 350.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KAYAK_ELBISESI'), 350.00, 'SPORTMAN', NOW(), NOW());

-- KASK - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KASK'), 175.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KASK'), 175.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KASK'), 175.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KASK'), 175.00, 'SPORTMAN', NOW(), NOW());

-- GOZLUK - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GOZLUK'), 125.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GOZLUK'), 125.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GOZLUK'), 125.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GOZLUK'), 125.00, 'SPORTMAN', NOW(), NOW());

-- BATON - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'BATON'), 125.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'BATON'), 125.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'BATON'), 125.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'BATON'), 125.00, 'SPORTMAN', NOW(), NOW());

-- PANTOLON - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'PANTOLON'), 200.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'PANTOLON'), 200.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'PANTOLON'), 200.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'PANTOLON'), 200.00, 'SPORTMAN', NOW(), NOW());

-- MONT - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'MONT'), 200.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'MONT'), 200.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'MONT'), 200.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'MONT'), 200.00, 'SPORTMAN', NOW(), NOW());

-- KIZAK - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KIZAK'), 150.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KIZAK'), 150.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KIZAK'), 150.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'KIZAK'), 150.00, 'SPORTMAN', NOW(), NOW());

-- GUNLUK_DOLAP - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GUNLUK_DOLAP'), 200.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GUNLUK_DOLAP'), 200.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GUNLUK_DOLAP'), 200.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'GUNLUK_DOLAP'), 200.00, 'SPORTMAN', NOW(), NOW());

-- SEZONLUK_DOLAP - 4 fiyat tipi
INSERT INTO `rentalski`.`prices` (`product_id`, `price`, `priceType`, `created_at`, `updated_at`) VALUES
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SEZONLUK_DOLAP'), 2500.00, 'FULL', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SEZONLUK_DOLAP'), 2500.00, 'STUDENT', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SEZONLUK_DOLAP'), 2500.00, 'GUEST', NOW(), NOW()),
((SELECT `id` FROM `rentalski`.`products` WHERE `code` = 'SEZONLUK_DOLAP'), 2500.00, 'SPORTMAN', NOW(), NOW());

