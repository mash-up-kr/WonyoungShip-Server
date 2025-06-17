package wyship.doong2.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["wyship.doong2"])
class WonyoungshipApplication

fun main(args: Array<String>) {
    runApplication<WonyoungshipApplication>(*args)
}
