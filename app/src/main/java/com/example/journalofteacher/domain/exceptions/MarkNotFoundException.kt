package com.example.journalofteacher.domain.exceptions

import java.lang.IllegalArgumentException

class MarkNotFoundException(
    message: String = "Такой отметки не существует"
) : IllegalArgumentException(message)