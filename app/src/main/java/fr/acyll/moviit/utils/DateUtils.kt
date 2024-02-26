package fr.acyll.moviit.utils

import android.text.format.DateUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import fr.acyll.moviit.R
import java.util.Date

object ComposableDateUtils {

    @Composable
    fun getLabelFromDate(date: Date): String {
        val today = remember { Date() }

        return if (today.time - date.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
            stringResource(id = R.string.just_now)
        } else if (date.time - today.time < DateUtils.DAY_IN_MILLIS) {
            DateUtils.getRelativeTimeSpanString(
                date.time, today.time, DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_SHOW_DATE
            ).toString()
        } else {
            ""
        }
    }
}