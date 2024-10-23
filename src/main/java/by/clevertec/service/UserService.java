package by.clevertec.service;

import by.clevertec.model.User;
import by.clevertec.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        return opUser.orElse(null);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}