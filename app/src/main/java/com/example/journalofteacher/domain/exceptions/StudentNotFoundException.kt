package com.example.journalofteacher.domain.exceptions

import java.lang.IllegalArgumentException

class StudentNotFoundException(
    message: String = "Такого студента не существует"
) : IllegalArgumentException(message)