package ru.javawebinar.topjava.service.jdbc;

import org.junit.Before;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

import java.net.URI;
import java.util.Properties;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
@Ignore
public class JdbcUserServiceTest extends AbstractUserServiceTest {

}