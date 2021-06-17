package de.rohitmisra.muenchen.ausweisstatus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = ["de.rohitmisra.muenchen.ausweisstatus"])
class Application
fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
