package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {
    private final Connection connection;
    private static final String SQL_SELECT_FROM_DRIVER = "select * from driver";
    private static final String SQL_SELECT_BY_ID_FROM_DRIVER = "select * from driver where id = ?";
    private static final String SQL_INSERT_TO_DRIVER = "insert into driver(name, surname, patronymic, §, " +
            "age, driving_experience) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_DRIVER = "update driver set name = ?, surname = ?, patronymic = ?, " +
            "email = ?, age = ?, driving_experience = ? where id = ?";
    private static final String SQL_DELETE_FROM_DRIVER = "delete from driver where id = ?";
    private static final String SQL_SELECT_BY_AGE_FROM_DRIVER = "select * from driver where age = ?";
    private static final String SQL_SELECT_BY_DRIVING_EXPERIENCE = "select * from driver where driving_experience = ?";
    private static final String SQL_SELECT_BY_PATRONYMIC = "select * from driver where patronymic = ?";
    private static final String SQL_SELECT_BY_EMAIL = "select * from driver where email = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVER);
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("surname"),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt("age"),
                    resultSet.getInt(5)
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID_FROM_DRIVER);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("surname"),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt("age"),
                    resultSet.getInt(9)
            );
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void save(User entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TO_DRIVER);
        formingRequestWithData(entity, preparedStatement);
    }

    @Override
    public void update(User entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DRIVER);
        preparedStatement.setLong(7, entity.getId());
        formingRequestWithData(entity, preparedStatement);
    }

    @Override
    public void remove(User entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FROM_DRIVER);
        preparedStatement.setLong(1, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FROM_DRIVER);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_AGE_FROM_DRIVER);
        preparedStatement.setInt(1, age);
        return selectRequest(preparedStatement);
    }

    @Override
    public void addingMultipleUsers(List<User> users) throws SQLException {
        PreparedStatement preparedStatement;
        for (User user : users) {
            preparedStatement = connection.prepareStatement(SQL_INSERT_TO_DRIVER);
            formingRequestWithData(user, preparedStatement);
        }
    }

    @Override
    public List<User> findAllByDrivingExperience(Integer experience) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_DRIVING_EXPERIENCE);
        preparedStatement.setInt(1, experience);
        return selectRequest(preparedStatement);
    }

    @Override
    public List<User> findAllByPatronymic(String patronymic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_PATRONYMIC);
        preparedStatement.setString(1, patronymic);
        return selectRequest(preparedStatement);
    }

    @Override
    public List<User> findAllByEmail(String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL);
        preparedStatement.setString(1, email);
        return selectRequest(preparedStatement);
    }

    private List<User> selectRequest(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt(4),
                    resultSet.getInt(5)
            );
            users.add(user);
        }
        return users;
    }

    private void formingRequestWithData(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getPatronymic());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setInt(5, user.getAge());
        preparedStatement.setInt(6, user.getDrivingExperience());
        preparedStatement.execute();
    }
}