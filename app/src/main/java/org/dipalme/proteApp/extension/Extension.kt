package org.dipalme.proteApp.extension

import android.content.Context
import com.github.sundeepk.compactcalendarview.domain.Event
import org.dipalme.proteApp.R
import org.jetbrains.anko.alert
import java.text.DateFormat
import java.util.regex.Pattern

object Extension {
    const val MIN_PASS_LENGTH = 10
    const val MAX_PASS_LENGTH = 20
    const val INDICATIVE_LENGTH = 5
}

fun isIndicativeValid(indicative: String): Boolean {
    if (indicative.length != Extension.INDICATIVE_LENGTH) {
        return false
    }

    val p = Pattern.compile("V-[0-9]{3}")
    val m = p.matcher(indicative)
    return m.find()
}

fun String.isPasswordValid(): Boolean =
    length in Extension.MIN_PASS_LENGTH..Extension.MAX_PASS_LENGTH

fun Context.showErrorDialog(error: String) {
    alert {
        message(error)
        positiveButton(R.string.accept) {}
    }.show()
}

fun Context.showCalendarDialog(events: List<Event>) {
    alert {
        var text = ""
        val formatter = DateFormat.getTimeInstance()
        for (event in events) {
            val date = formatter.format(event.timeInMillis)
            text += date + " - " + event.data.toString() + "\n"
        }
        message(text)
    }.show()
}