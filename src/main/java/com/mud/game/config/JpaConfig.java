package com.mud.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * springboot 引入两个数据库的时候对数据库的仓库包地址进行分别配置避免混淆
 * */

@EnableJpaRepositories(basePackages = "com.mud.game.worlddata.db.repository")
@EnableMongoRepositories(basePackages = "com.mud.game.worldrun.db.repository")
@Configuration
public class JpaConfig {
}
