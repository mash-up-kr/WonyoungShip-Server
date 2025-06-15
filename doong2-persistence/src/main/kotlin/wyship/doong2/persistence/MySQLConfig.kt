package wyship.doong2.persistence

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["wyship.doong2.persistence"])
@EnableJpaRepositories(basePackages = ["wyship.doong2.persistence"])
class MySQLConfig
