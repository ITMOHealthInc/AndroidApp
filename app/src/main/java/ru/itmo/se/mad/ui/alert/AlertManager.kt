package ru.itmo.se.mad.ui.alert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.itmo.se.mad.exception.VisibleException

object AlertManager {
    var visible by mutableStateOf(false)
    var message by mutableStateOf("")
    var type by mutableStateOf(AlertType.INFO)

    fun show(msg: String, alertType: AlertType) {
        message = msg
        type = alertType
        visible = true
    }

    fun show(exception: VisibleException) {
        show(exception.message, exception.type)
    }

    fun hide() {
        visible = false
    }
}