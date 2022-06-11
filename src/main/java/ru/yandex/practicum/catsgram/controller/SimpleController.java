package ru.yandex.practicum.catsgram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;

import java.util.Optional;

@Slf4j
@RestController
public class SimpleController {
    // создаём логер
    //private static final Logger log = LoggerFactory.getLogger(SimpleController.class);
    private final HackCatService hackCatService;

    // внедрите бин HackCatService
    @Autowired
    public SimpleController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

    @GetMapping("/do-hack")
    public Optional<String> doHack(){
        // хакните этих котиков
        //return hackCatService.doHackNow();
        return hackCatService.doHackNow()
                .map(password -> "Ура! Пароль подобран: " + password)
                .or(() -> Optional.of("Не удалось подобрать пароль. "
                        + " Проверьте состояние и настройки базы данных."));
    }

    @GetMapping("/home")
    public String homePage() {
        // логируем факт получения запроса
        log.debug("Запрос получен.");

        // возвращаем ответ в виде строки
        return "Котограм";
    }
}
