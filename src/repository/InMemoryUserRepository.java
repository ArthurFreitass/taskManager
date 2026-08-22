package repository;

import model.entities.User;
import model.exceptions.DomainException;

import java.util.*;

public class InMemoryUserRepository implements Repository<User> {

    private Map<String, User> userMap;

    public InMemoryUserRepository() {
        userMap = new HashMap<>();
    }

    @Override
    public void addItem(User user) {
        if (user == null) {
            throw new DomainException("New user in memory user repository cannot be null");
        }
        userMap.put(user.getName(), user);
    }

    @Override
    public User findById(String name) {
        for (String key : userMap.keySet()) {
            User u = userMap.get(key);
            if (u.getName().equals(name)) return u;
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>(userMap.values());
        return list;
    }

    @Override
    public void delete(String name) {
        if (userMap.remove(name) == null) {
            throw new DomainException("User not found in memory user!");
        }
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
