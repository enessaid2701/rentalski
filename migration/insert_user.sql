-- Admin kullanıcısı ekleme
-- Email: admin
-- Password: nimda (BCrypt hash ile)
-- Role: ADMIN

-- Not: "nimda" için BCrypt hash'i oluşturmak için Java'da şu kodu kullanabilirsiniz:
-- BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
-- String hash = encoder.encode("nimda");
-- System.out.println(hash);

-- Aşağıdaki hash "nimda" için oluşturulmuş bir BCrypt hash'idir
-- Eğer bu hash çalışmazsa, yukarıdaki Java kodu ile yeni bir hash oluşturun

INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NULL, NOW(), NOW());

