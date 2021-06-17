package de.rohitmisra.muenchen.ausweisstatus.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ausweis-status")
class FinderProperties {
    var documents: List<Map<String, String>> = ArrayList()
}