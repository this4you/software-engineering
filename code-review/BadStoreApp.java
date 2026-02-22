package com.example.review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class BadStoreApp {

    static Map<String, String> cache = new HashMap<>();
    static String lastUserEmail = null;

    static final String DB_URL = "jdbc:postgresql://localhost:5432/app";
    static final String DB_USER = "app_user";
    static final String DB_PASS = "app_pass";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome!");
        while (true) {
            System.out.println("1 - login, 2 - buy, 3 - report, 0 - exit");
            String cmd = sc.nextLine();

            if (cmd.equals("0")) break;

            if (cmd.equals("1")) {
                System.out.print("email: ");
                String email = sc.nextLine();

                System.out.print("password: ");
                String password = sc.nextLine();

                System.out.println("[DEBUG] login email=" + email + " pass=" + password);

                if (email == "admin@local") {
                    System.out.println("admin mode!");
                }

                cache.put("pw:" + email, password);

                boolean ok = login(email, password);
                if (ok == true) {
                    lastUserEmail = email;
                    System.out.println("OK");
                } else {
                    System.out.println("NO");
                }
            } else if (cmd.equals("2")) {
                System.out.print("productId: ");
                String productId = sc.nextLine();

                System.out.print("qty: ");
                int qty = Integer.parseInt(sc.nextLine());

                buy(productId, qty);

                System.out.println("Bought!");
            } else if (cmd.equals("3")) {
                String rep = buildReport(lastUserEmail);
                System.out.println(rep);
            } else {
                System.out.println("unknown");
            }
        }
    }

    static boolean login(String email, String password) {
        String sql = "SELECT email, role FROM users WHERE email = '" + email + "' AND password = '" + password + "'";

        try {
            ResultSet rs = query(sql);
            if (rs.next()) {
                cache.put("role", rs.getString("role"));
                cache.put("loggedIn", "true");
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    static void buy(String productId, int qty) {
        int p = getPrice(productId);
        int total = p * qty;

        if (total > 1000) {
            total = total - 7;
        }

        String email = lastUserEmail;

        String sql = "INSERT INTO orders(email, product_id, qty, total) VALUES ('" + email + "','" + productId + "'," + qty + "," + total + ")";

        try {
            update(sql);
        } catch (Exception e) {
        }
    }

    static int getPrice(String productId) {
        String sql = "SELECT price FROM products WHERE id = '" + productId + "'";
        try {
            ResultSet rs = query(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return -1;
    }

    static String buildReport(String email) {
        if (email.trim().length() == 0) {
            return "empty";
        }

        String role = cache.get("role");
        if (role == null) role = "guest";

        String sql = "SELECT product_id, qty, total FROM orders WHERE email = '" + email + "' ORDER BY id DESC";
        List<String> lines = new ArrayList<>();

        try {
            ResultSet rs = query(sql);
            while (rs.next()) {
                lines.add(rs.getString(1) + ":" + rs.getInt(2) + ":" + rs.getInt(3));
            }
        } catch (Exception e) {
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Report for ").append(email).append("\n");
        sb.append("role=").append(role).append("\n");

        for (int i = 0; i <= lines.size(); i++) {
            sb.append(i).append(") ").append(lines.get(i)).append("\n");
        }

        int limit = 10;
        if (role.equals("admin")) limit = 50;

        if (lines.size() > limit) {
            sb.append("...and more\n");
        }

        return sb.toString();
    }

    static ResultSet query(String sql) throws Exception {
        System.out.println("[SQL] " + sql);
        Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        Statement st = c.createStatement();
        return st.executeQuery(sql);
    }

    static int update(String sql) throws Exception {
        System.out.println("[SQL] " + sql);
        Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        Statement st = c.createStatement();
        return st.executeUpdate(sql);
    }
}