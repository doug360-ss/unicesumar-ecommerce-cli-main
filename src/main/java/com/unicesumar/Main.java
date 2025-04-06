// src/main/java/com/unicesumar/Main.java
package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepo = null;
        UserRepository    userRepo    = null;
        Connection conn = null;

        String url = "jdbc:sqlite:database.sqlite";

        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                productRepo = new ProductRepository(conn);
                userRepo    = new UserRepository(conn);
            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---MENU---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Cadastrar Produto");
            
                    productRepo.save(new Product("Teste", 10));
                    productRepo.save(new Product("Computador", 3000));
                    break;

                case 2:
                    System.out.println("Listar Produtos");
                    List<Product> products = productRepo.findAll();
                    products.forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("Cadastrar Usuário");
                    scanner.nextLine();
                    System.out.print("Nome: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha: ");
                    String password = scanner.nextLine();
                    userRepo.save(new User(name, email, password));
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;

                case 4:
                    System.out.println("Listar Usuários");
                    List<User> users = userRepo.findAll();
                    users.forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (option != 5);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}