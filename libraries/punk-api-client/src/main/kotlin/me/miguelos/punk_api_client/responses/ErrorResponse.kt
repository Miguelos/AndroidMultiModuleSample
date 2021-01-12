package me.miguelos.punk_api_client.responses

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
abstract class ErrorResponse(
    @JsonProperty(Properties.CODE) var code: Int = 0,
    @JsonProperty(Properties.MESSAGE) var message: String? = null,
    @JsonProperty(Properties.ERROR) var error: String? = null
) {

    @get:JsonIgnore
    val hasError: Boolean
        get() = error?.isNotEmpty() ?: false


    object Properties {
        const val CODE = "statusCode"
        const val MESSAGE = "message"
        const val ERROR = "error"
    }
}
