package raf.vp.jmijatovic11421rn.repositories.user;

import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.entities.User;

import java.util.List;

public interface UserRepository {

    User findUser(String email);

    List<User> getAllUsers();

    List<User> getUsersByPage(Integer page);

    void editUser(String email, User user);

    void addUser(User user);

    void setUserActive(Boolean active, String email);
}

