package ru.javawebinar.topjavagraduation.config;

import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static org.slf4j.LoggerFactory.getLogger;


@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    private static final Logger log = getLogger(CacheConfig.class);

    @Scheduled(cron="0 0 0 * * ?")
    @CacheEvict(value = "menus", allEntries = true)
    void clearMenuCache() {
        log.info("Clear menu cache");
    }
}
