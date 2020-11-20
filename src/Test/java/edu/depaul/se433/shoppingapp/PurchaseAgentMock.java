package edu.depaul.se433.shoppingapp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Script;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class PurchaseAgentMock {
    private static String jdbcUrl;
    private Jdbi jdbi;
    private LocalDate date = LocalDate.now();

    private void savePurchase(String name, LocalDate purchaseDate, double cost, String state, String shipping) {

        jdbi.useHandle(handle -> {
            handle.createUpdate("INSERT INTO purchases(name, pdate, cost, state, shipping_type) VALUES (?, ?, ?, ?, ?)")
                    .bind(0, name)
                    .bind(1, purchaseDate)
                    .bind(2, cost)
                    .bind(3, state)
                    .bind(4, shipping)
                    .execute();
        });
    }

    private void deletePurchase(String name, LocalDate purchaseDate, double cost, String state, String shipping) {

        jdbi.useHandle(handle -> {
            handle.createUpdate("DELETE FROM purchases where name = ?, pdate = ?, cost = ?, state = ?, shipping_type = ?")
                    .bind(0, name)
                    .bind(1, purchaseDate)
                    .bind(2, cost)
                    .bind(3, state)
                    .bind(4, shipping)
                    .execute();
        });
    }

    private List<Purchase> getPurchases(String name, LocalDate purchaseDate, double cost, String state, String shipping) {
        List<Purchase> purchases = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM purchases where where name = ?, pdate = ?, cost = ?, state = ?, shipping_type = ? ORDER BY id_num")
                    .mapToBean(Purchase.class)
                    .list();

        });
        return purchases;
    }

    @Test
    void testAdd(){
        savePurchase("Ali", date, 10.0, "IL", "standard");
        deletePurchase("Ali", date, 10.0, "IL", "standard");
        }

    }

