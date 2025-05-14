package ru.itmo.se.mad.exception

import ru.itmo.se.mad.ui.alert.AlertType

class VisibleException(val type: AlertType, override val message: String) : Exception(message)