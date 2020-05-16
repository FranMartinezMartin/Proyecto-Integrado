package org.dipalme.proteApp.extension

import android.content.Context
import org.dipalme.proteApp.R
import org.jetbrains.anko.alert
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

    var p = Pattern.compile("V-[0-9]{3}")
    var m = p.matcher(indicative)
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