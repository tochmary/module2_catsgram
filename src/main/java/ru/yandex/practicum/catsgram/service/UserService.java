package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    public User create(User user) throws InvalidEmailException, UserAlreadyExistException {
        String email = user.getEmail();
        checkEmail(email);
        checkUser(user);
        users.put(email, user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    public User update(User user) throws InvalidEmailException {
        String email = user.getEmail();
        checkEmail(user.getEmail());
        users.put(email, user);
        log.debug("Обновлен пользователь: {}", user);
        return user;
    }

    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        return users.get(email);
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
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с указанным адресом электронной почты уже был добавлен ранее!");
        }
    }
}
