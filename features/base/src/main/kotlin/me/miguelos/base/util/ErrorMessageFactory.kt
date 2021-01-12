package me.miguelos.base.util

import android.content.Context
import me.miguelos.base.R
import timber.log.Timber
import java.net.UnknownHostException

/**
 * Factory used to create error messages from an Exception as a condition.
 */
object ErrorMessageFactory {

    fun create(context: Context, exception: Throwable) =
        with(context) {
            logger
.e(exception)
            when (exception) {
                is UnknownHostException -> getString(R.string.exception_no_connection)
                else -> getString(R.string.exception_generic) + ":" + exception.localizedMessage
            }
        }
}
