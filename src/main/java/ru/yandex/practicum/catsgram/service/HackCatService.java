package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HackCatService {
    //private Logger log = LoggerFactory.getLogger(getClass());

    public static final String JDBC_URL="jdbc:postgresql://127.0.0.1:5432/cats";
    public static final String JDBC_USERNAME="kitty";
    public static final String JDBC_DRIVER="org.postgresql.Driver";

    public void tryPassword(String jdbcPassword) {
        DriverManagerDataSource dataSourceConst = new DriverManagerDataSource();
        // проверьте подключение с паролем и параметрами БД
        dataSourceConst.setDriverClassName(JDBC_DRIVER);
        dataSourceConst.setUrl(JDBC_URL);
        dataSourceConst.setUsername(JDBC_USERNAME);
        dataSourceConst.setPassword(jdbcPassword);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceConst);
        jdbcTemplate.execute("SELECT 1;");
    }

    public Optional<String> doHackNow(){
        List<String> catWordList = Arrays.asList("meow", "purr", "purrrrrr", "zzz");
        // проверьте пароли из списка
        for (String password : catWordList) {
            try {
                tryPassword(password);
                log.info(password + " - правильный пароль");
                return Optional.of(password);
            } catch (Exception e) {
                log.info(password + " - не правильный пароль");
            }
        }
        return Optional.empty();
    }
}