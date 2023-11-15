package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("����", "��������", (byte) 10);
        userService.saveUser("����", "��������", (byte) 11);
        userService.saveUser("����", "��������", (byte) 12);
        userService.saveUser("����", "��������", (byte) 13);
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
