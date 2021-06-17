package de.rohitmisra.muenchen.ausweisstatus.component

import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import de.rohitmisra.muenchen.ausweisstatus.model.DocumentInfo
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct


@Component
@EnableScheduling
class Finder(
    @Autowired
    private val notifier: Notifier,
    @Autowired
    private val finderProperties: FinderProperties
) {
    private val log = LoggerFactory.getLogger(Finder::class.java)

    private val client = OkHttpClient()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val documentInfoList = mutableListOf<DocumentInfo>()

    @PostConstruct
    fun setup() {
        finderProperties.documents.map {
            documentInfoList.add(DocumentInfo(
                tag = it["tag"]!!,
                documentId = it["document_id"]!!,
                documentType = it["document_type"]!!,
                holder = it["holder"]!!
            ))
        }
    }

    @Scheduled(fixedDelay = 1 * 60 * 1000)
    fun scheduleFixedDelayTask() {
        log.info("job triggered, checking for availabilities")
        try {
            documentInfoList.map {
                checkStatus(
                    "https://www17.muenchen.de/Passverfolgung/",
                    it
                )
            }
        } catch (ex: Exception) {
            log.error("unexpected exception while running job: ", ex)
        }

    }

    fun checkStatus(url: String, documentInfo: DocumentInfo) {
        val mediaType: MediaType = MediaType.parse("application/x-www-form-urlencoded")
        val requestBody = RequestBody.create(
            mediaType,
            "ifNummer=${documentInfo.documentId}&pbAbfragen=Abfragen"
        )
        val request = Request.Builder()
            .url(url)
            .method("POST", requestBody)
            .build()
        val body = client.newCall(request).execute().body().string()
        val doc: Document = Jsoup.parse(body)
        doc.body().text()?.takeIf {
            it.contains(documentInfo.documentId) && !it.contains("liegt noch nicht zur Abholung bereit.")
        }?.let {
            log.info("${documentInfo.holder}'s ${documentInfo.documentType} is available")
            notifier.notify("${documentInfo.holder}'s ${documentInfo.documentType} is available")
        }
    }
}