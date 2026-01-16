-- 4 Kullanıcı Ekleme SQL
-- Tüm hash'ler BCrypt ile oluşturulmuştur

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
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici1', '$2a$10$rK9V8mN5pL2xY3zW4vB6cOuPxQyRzS1tU2vW3xY4zA5bC6dE7fG8hI9j', 'OPERATOR', NULL, NOW(), NOW());

-- 3. Yazıcı 2
-- Email: yazici2
-- Password: yıldız123
-- Role: OPERATOR
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici2', '$2a$10$rK9V8mN5pL2xY3zW4vB6cOuPxQyRzS1tU2vW3xY4zA5bC6dE7fG8hI9j', 'OPERATOR', NULL, NOW(), NOW());

-- 4. Yazıcı 3
-- Email: yazici3
-- Password: yıldız123
-- Role: OPERATOR
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici3', '$2a$10$rK9V8mN5pL2xY3zW4vB6cOuPxQyRzS1tU2vW3xY4zA5bC6dE7fG8hI9j', 'OPERATOR', NULL, NOW(), NOW());

-- NOT: BCrypt hash'leri her seferinde farklı olur (salt nedeniyle).
-- Yukarıdaki hash'ler örnek hash'lerdir. Gerçek hash'leri oluşturmak için:
-- 1. GeneratePasswordHash.java dosyasını çalıştırın, veya
-- 2. Projenizde bir test sınıfı oluşturup BCryptPasswordEncoder kullanın

