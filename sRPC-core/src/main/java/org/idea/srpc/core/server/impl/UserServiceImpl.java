package org.idea.srpc.core.server.impl;

import bean.User;
import interfaces.user.UserService;
import org.idea.srpc.core.server.UserData;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return UserData.getUsers();
    }

    @Override
    public User getUserById(Integer id) {
        List<User> users = UserData.getUsers().stream().filter(user -> user.getId() == id).collect(Collectors.toList());
        if (users.size() > 0) return users.get(0);
        return null;
    }

    @Override
    public void updateUserStatusById(Integer id) {
        UserData.getUsers().forEach(user -> {
            if (user.getId().equals(id)) {
                user.setStatus((user.getStatus() + 1) % 2);
            }
        });
    }

}
