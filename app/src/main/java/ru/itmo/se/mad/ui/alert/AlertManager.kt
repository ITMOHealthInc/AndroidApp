package ru.itmo.se.mad.ui.alert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AlertManager {
    var visible by mutableStateOf(false)
    var message by mutableStateOf("")
    var type by mutableStateOf(AlertType.INFO)

    fun show(msg: String, alertType: AlertType) {
        message = msg
        type = alertType
        visible = true
    }

    fun warn(msg: String) {
        show(msg, AlertType.WARNING)
    }

    fun error(msg: String) {
        show(msg, AlertType.ERROR)
    }

    fun success(msg: String) {
        show(msg, AlertType.SUCCESS)
    }

    fun info(msg: String) {
        show(msg, AlertType.INFO)
    }

    fun hide() {
        visible = false
    }
}