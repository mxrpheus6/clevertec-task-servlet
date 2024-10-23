package by.clevertec.repository;

import by.clevertec.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
    private static long idCounter = 1;

    static {
        users.put(idCounter++, 
                User.builder()
                        .id(idCounter)
                        .username("Steve")
                        .email("steve@gmail.com")
                        .password("qwerty1234")
                        .registeredAt(LocalDate.now())
                        .build()
        );

        users.put(idCounter++,
                User.builder()
                        .id(idCounter)
                        .username("Tim")
                        .email("tim@gmail.com")
                        .password("qwerty4321")
                        .registeredAt(LocalDate.now())
                        .build()
        );
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(Long id) {
        User user = users.get(id);

        return user == null ? Optional.empty() : Optional.of(user);
    }

    public User save(User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public void delete(Long id) {
        users.remove(id);
    }
}
