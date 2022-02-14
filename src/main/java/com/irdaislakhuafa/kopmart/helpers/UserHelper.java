package com.irdaislakhuafa.kopmart.helpers;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
    private static boolean enableMyLog = true;

    public static Optional<User> getCurrentUser() {
        User currentUser = null;
        try {
            currentUser = (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("gagal mendapatkan user saat ini, anonymouse user aktif!".toUpperCase());
            currentUser = new User() {
                {
                    setNama("Anonymouse User");
                    setEmail("anonymouse@user.com");
                }
            };
        }

        return Optional.of(currentUser);
    }

    public static void errorLog(String s) {
        if (!enableMyLog) {
            System.out.println("=".repeat(30));
            System.out.println(s.toUpperCase() + "!!!");
            System.out.println("=".repeat(30));
        }
    }

    public static void errorLog(String s, Object object) {
        if (!enableMyLog) {
            System.out.println("=".repeat(30));
            System.out.println(s.toUpperCase() + "!!! => (" + String.valueOf(Object.class.getName()) + ")");
            System.out.println("=".repeat(30));
        }
    }

    public static void print(Object object) {
        System.out.println((enableMyLog) ? object : "");
    }

    // public static Keranjang getCurrentKeranjang() {
    // Keranjang keranjang = null;
    // try {
    // keranjang = UserHelper.getCurrentUser().get().getKeranjang();
    // } catch (Exception e) {
    // // TODO: handle exception
    // System.out.println("Gagal mendapatkan keranjang saat ini!");
    // }

    // return keranjang;
    // }
}
