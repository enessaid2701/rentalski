package com.library;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateUserPasswords {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("==========================================");
        System.out.println("BCrypt Password Hash Generator");
        System.out.println("==========================================");
        System.out.println();
        
        // Admin
        String adminHash = encoder.encode("nimda");
        System.out.println("-- 1. Admin (nimda) --");
        System.out.println("INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES");
        System.out.println("('admin', '" + adminHash + "', 'ADMIN', NULL, NOW(), NOW());");
        System.out.println();
        
        // Yazıcı 1
        String yazici1Hash = encoder.encode("yıldız123");
        System.out.println("-- 2. Yazıcı 1 (yıldız123) --");
        System.out.println("INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES");
        System.out.println("('yazici1', '" + yazici1Hash + "', 'OPERATOR', NULL, NOW(), NOW());");
        System.out.println();
        
        // Yazıcı 2
        String yazici2Hash = encoder.encode("yıldız123");
        System.out.println("-- 3. Yazıcı 2 (yıldız123) --");
        System.out.println("INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES");
        System.out.println("('yazici2', '" + yazici2Hash + "', 'OPERATOR', NULL, NOW(), NOW());");
        System.out.println();
        
        // Yazıcı 3
        String yazici3Hash = encoder.encode("yıldız123");
        System.out.println("-- 4. Yazıcı 3 (yıldız123) --");
        System.out.println("INSERT INTO `rentalski`.`users` (`email`, `password`, `role`, `title`, `created_at`, `updated_at`) VALUES");
        System.out.println("('yazici3', '" + yazici3Hash + "', 'OPERATOR', NULL, NOW(), NOW());");
        System.out.println();
        
        System.out.println("==========================================");
        System.out.println("Hash'ler oluşturuldu! Yukarıdaki SQL'leri kopyalayıp çalıştırabilirsiniz.");
        System.out.println("==========================================");
    }
}

