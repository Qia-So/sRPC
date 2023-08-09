package interfaces.user;

import bean.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Integer id);

    void updateUserStatusById(Integer id);
}
