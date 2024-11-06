package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static  void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from driver where age > 25;"); // sql запрос на вывод водителей возраст которых больше 25

        System.out.println();

        while(result.next()){
            System.out.println(result.getInt("id") + " " + result.getString("name") + " " + result.getString("age")); // вывод этого запроса
        }


        String sqlInsertUser = "insert into driver(name, surname, age) values (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);

        for (int i = 0; i < 6; i++) {
            Scanner scanner = new Scanner(System.in);
            String firstName = scanner.nextLine();
            String lastName = scanner.nextLine();
            int age = scanner.nextInt();
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            /*  для того чтобы добавлять сразу несколько строк за 1 запрос я использовал batch*/
            preparedStatement.addBatch();
        }

        System.out.println(sqlInsertUser);

        int[] affectedRows = preparedStatement.executeBatch();
        System.out.println("Было добавлено " + affectedRows.length + " строк");
    }
}