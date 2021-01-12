package me.miguelos.punk_api_client.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)

data class BeerEntity(
    @JsonProperty(Properties.ID) var id: Long = -1L,
    @JsonProperty(Properties.NAME) var name: String = "",
    @JsonProperty(Properties.DESCRIPTION) var description: String? = "",
    @JsonProperty(Properties.ABV) var abv: Double? = null,
    @JsonProperty(Properties.THUMBNAIL) var thumbnail: String? = "",
) {

    object Properties {
        const val ID = "id"
        const val NAME = "name"
        const val ABV = "abv"
        const val DESCRIPTION = "description"
        const val THUMBNAIL = "image_url"
    }
}
