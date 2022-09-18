package dev.nhonnq.test.errors.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.nhonnq.test.R
import dev.nhonnq.test.errors.*
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(DEFAULT_ERROR, getErrorString(R.string.common_error_msg)),
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet_msg)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(LOAD_UPCOMING_ERROR, getErrorString(R.string.no_data_found_here)),
        ).withDefault { getErrorString(R.string.network_error) }
}
