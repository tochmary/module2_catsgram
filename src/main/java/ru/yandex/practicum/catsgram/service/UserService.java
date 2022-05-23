package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final Set<User> users = new HashSet<>();

    public Set<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users;
    }

    public User create(User user) throws InvalidEmailException, UserAlreadyExistException {
        checkEmail(user.getEmail());
        checkUser(user);
        users.add(user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    public User update(User user) throws InvalidEmailException {
        checkEmail(user.getEmail());
        if (users.contains(user)) {
            for (User u : users) {
                if (u.equals(user)) {
                    u.setNickname(user.getNickname());
                    u.setBirthdate(user.getBirthdate());
                }
            }
            log.debug("Обновлен пользователь: {}", user);
        } else {
            users.add(user);
            log.debug("Добавлен пользователь: {}", user);
        }
        return user;
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    private void checkEmail(String email) throws InvalidEmailException {
        //Если в переданных данных отсутствует адрес электронной почты (например, равен null или пустой строке),
        // то генерируется исключение InvalidEmailException.
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым!");
        }
    }

    private void checkUser(User user) throws UserAlreadyExistException {
        //Если пользователь с указанным адресом электронной почты уже был добавлен ранее,
        // то генерируется исключение UserAlreadyExistException.
        if (users.contains(user)) {
            throw new UserAlreadyExistException("Пользователь с указанным адресом электронной почты уже был добавлен ранее!");
        }
    }
}
