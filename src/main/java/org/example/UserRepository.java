package org.example;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends CrudRepository<User>{
    List<User> findAllByAge(Integer age) throws SQLException;
    void addingMultipleUsers(List<User> users) throws SQLException;
    List<User> findAllByDrivingExperience(Integer experience) throws SQLException;
    List<User> findAllByPatronymic(String patronymic) throws SQLException;
    List<User> findAllByEmail(String email) throws SQLException;
}
