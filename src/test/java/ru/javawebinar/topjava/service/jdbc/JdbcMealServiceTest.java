package ru.javawebinar.topjava.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private Environment environment;

    @Override
    public void createWithException() throws Exception {
        if (!environment.acceptsProfiles(Profiles.of("jdbc"))) {
            super.createWithException();
        }
    }
}