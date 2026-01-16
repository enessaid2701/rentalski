-- 4 Kullanıcı Ekleme SQL
-- 
-- ÖNEMLİ: Bu dosyadaki hash'ler örnek hash'lerdir!
-- Gerçek hash'leri oluşturmak için:
-- 1. GenerateUserPasswords.java dosyasını çalıştırın (mvn test-compile exec:java)
-- 2. Çıktıdaki SQL'leri kopyalayın
-- 3. Bu dosyaya yapıştırın veya direkt veritabanında çalıştırın

-- 1. Admin kullanıcısı
-- Email: admin
-- Password: nimda
-- Role: ADMIN
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NULL, NOW(), NOW());

-- 2. Yazıcı 1
-- Email: yazici1
-- Password: yıldız123
-- Role: OPERATOR
-- NOT: Aşağıdaki hash örnektir, GenerateUserPasswords.java ile gerçek hash oluşturun
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici1', 'HASH_BURAYA_GELECEK', 'OPERATOR', NULL, NOW(), NOW());

-- 3. Yazıcı 2
-- Email: yazici2
-- Password: yıldız123
-- Role: OPERATOR
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici2', 'HASH_BURAYA_GELECEK', 'OPERATOR', NULL, NOW(), NOW());

-- 4. Yazıcı 3
-- Email: yazici3
-- Password: yıldız123
-- Role: OPERATOR
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici3', 'HASH_BURAYA_GELECEK', 'OPERATOR', NULL, NOW(), NOW());

