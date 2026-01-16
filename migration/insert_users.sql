-- 4 Kullanıcı Ekleme
-- Email, Password (BCrypt hash), Role

-- Not: BCrypt hash'leri oluşturmak için Java'da şu kodu kullanabilirsiniz:
-- BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
-- String hash = encoder.encode("şifre");
-- System.out.println(hash);

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
('yazici1', '$2a$10$8K1p/a0dL3YXzq3YvJ5K.OV9xJxJxJxJxJxJxJxJxJxJxJxJxJxJxJx', 'OPERATOR', NULL, NOW(), NOW());

-- 3. Yazıcı 2
-- Email: yazici2
-- Password: yıldız123
-- Role: OPERATOR
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici2', '$2a$10$8K1p/a0dL3YXzq3YvJ5K.OV9xJxJxJxJxJxJxJxJxJxJxJxJxJxJx', 'OPERATOR', NULL, NOW(), NOW());

-- 4. Yazıcı 3
-- Email: yazici3
-- Password: yıldız123
-- Role: OPERATOR
INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES
('yazici3', '$2a$10$8K1p/a0dL3YXzq3YvJ5K.OV9xJxJxJxJxJxJxJxJxJxJxJxJxJxJx', 'OPERATOR', NULL, NOW(), NOW());

-- ÖNEMLİ: Yukarıdaki BCrypt hash'leri örnek hash'lerdir.
-- Gerçek hash'leri oluşturmak için aşağıdaki Java kodunu kullanın:

/*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswordHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("admin (nimda): " + encoder.encode("nimda"));
        System.out.println("yazici1 (yıldız123): " + encoder.encode("yıldız123"));
        System.out.println("yazici2 (yıldız123): " + encoder.encode("yıldız123"));
        System.out.println("yazici3 (yıldız123): " + encoder.encode("yıldız123"));
    }
}
*/

