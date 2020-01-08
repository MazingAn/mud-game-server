package com.mud.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaRepositories(basePackages = "com.mud.game.worlddata.db.repository")
@EnableMongoRepositories(basePackages = "com.mud.game.worldrun.db.repository")
@Configuration
public class JpaConfig {
}
