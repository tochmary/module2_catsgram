package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;
import java.util.Set;

/*
Добавьте в приложение класс UserController. Добавьте в него следующие эндпоинты.
GET /users — для получения списка пользователей.
POST /users — для добавления нового пользователя в список.
При этом нужно учесть исключения:
Если пользователь с указанным адресом электронной почты уже был добавлен ранее, то генерируется исключение UserAlreadyExistException.
Если в переданных данных отсутствует адрес электронной почты (например, равен null или пустой строке), то генерируется исключение InvalidEmailException.
Оба исключения UserAlreadyExistException и InvalidEmailException нужно создать самостоятельно.
PUT /users — для добавления нового пользователя или обновления значения полей существующего.
Учтите, что если в переданных данных отсутствует адрес электронной почты, то генерируется исключение InvalidEmailException.
Используйте аннотацию @RequestMapping на уровне класса UserController,
чтобы задать единый путь ко всем эндпоинтам.
В эндпоинтах используйте аннотации @GetMapping, @PostMapping и @PutMapping.
Также вам понадобится аннотация @RequestBody.
Для хранения данных используйте HashSet или HashMap.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //GET /users — для получения списка пользователей.
    @GetMapping
    public Set<User> findAll() {
        return userService.findAll();
    }

    //POST /users — для добавления нового пользователя в список.
    @PostMapping
    public User create(@RequestBody User user) throws InvalidEmailException, UserAlreadyExistException {
        return userService.create(user);
    }

    //PUT /users — для добавления нового пользователя или обновления значения полей существующего.
    @PutMapping
    public User update(@RequestBody User user) throws InvalidEmailException {
        return userService.update(user);
    }
}
