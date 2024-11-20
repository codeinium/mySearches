package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "12345";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);
        List<User> allUsers = userRepository.findAll();
        outputUsersInformation(allUsers);

        List<User> input_users = new ArrayList<>();
        System.out.print("Количество пользователей: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            input_users.add(createNewUser());
        }

        userRepository.addingMultipleUsers(input_users);

        System.out.print("Введите возраст: ");
        Integer age = scanner.nextInt();
        scanner.nextLine();
        List<User> userWithAge = userRepository.findAllByAge(age);
        outputUsersInformation(userWithAge);

        System.out.print("Введите стаж вождения: ");
        int experience = scanner.nextInt();
        scanner.nextLine();
        List<User> usersWithDrivingExperience = userRepository.findAllByDrivingExperience(experience);
        outputUsersInformation(usersWithDrivingExperience);

        System.out.print("Введите отчество: ");
        List<User> usersWithPatronymic = userRepository.findAllByPatronymic(scanner.nextLine());
        outputUsersInformation(usersWithPatronymic);

        System.out.print("Введите почту: ");
        List<User> usersWithEmail = userRepository.findAllByEmail(scanner.nextLine());
        outputUsersInformation(usersWithEmail);

        System.out.print("Введите ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        User new_user = createNewUser();
        new_user.setID(id);
        userRepository.save(new_user);
        User update_user = createNewUser();
        update_user.setID(id);
        userRepository.update(update_user);
        System.out.print("Checked? (y/n): ");
        if (!scanner.nextLine().equals("y")) {
            return;
        }
        userRepository.remove(update_user);

        System.out.print("Введите ID: ");
        id = scanner.nextLong();
        scanner.nextLine();
        userRepository.removeById(id);
    }

    static User createNewUser() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Фамилия: ");
        String surname = scanner.nextLine();
        System.out.print("Отчество: ");
        String patronymic = scanner.nextLine();
        System.out.print("Возраст: ");
        Integer age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Почта: ");
        String email = scanner.nextLine();
        System.out.print("Опыт вождения: ");
        Integer driving_experience = scanner.nextInt();
        scanner.nextLine();
        return new User(null, name, surname, patronymic, email, age, driving_experience);
    }

    static void outputUsersInformation(List<User> users) {
        users.forEach(user -> System.out.println(user.getFirstName() + "\t" + user.getLastName() + "\t" + user.getPatronymic()));
        System.out.println("________________________________");
    }
}
