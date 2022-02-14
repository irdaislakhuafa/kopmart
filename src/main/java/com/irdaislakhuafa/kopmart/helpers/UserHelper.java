package com.irdaislakhuafa.kopmart.helpers;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.Keranjang;
import com.irdaislakhuafa.kopmart.models.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
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
        System.out.println("=".repeat(30));
        System.out.println(s.toUpperCase() + "!!!");
        System.out.println("=".repeat(30));
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
