package de.rohitmisra.muenchen.ausweisstatus.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "slack-bot")
class SlackBotProperties {
    var botConfig: Map<String, String> = HashMap()
}