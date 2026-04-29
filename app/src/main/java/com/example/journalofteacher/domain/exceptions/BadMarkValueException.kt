package com.example.journalofteacher.domain.exceptions

import java.lang.IllegalArgumentException

class BadMarkValueException(
    message: String = "Некорректное значение оценки"
) : IllegalArgumentException(message)