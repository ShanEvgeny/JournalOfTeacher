package com.example.journalofteacher

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var editTextStudent: EditText
    private lateinit var editTextSubject: EditText
    private lateinit var editTextMark: EditText
    private lateinit var linearLayout: LinearLayout
    private lateinit var buttonAddMark: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar = findViewById(R.id.toolbar)
        editTextStudent = findViewById(R.id.editTextStudent)
        editTextSubject = findViewById(R.id.editTextSubject)
        editTextMark = findViewById(R.id.editTextMark)
        buttonAddMark = findViewById(R.id.buttonAddMark)
        linearLayout = findViewById(R.id.linearlayout)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        buttonAddMark.setOnClickListener {
            val textStudent = editTextStudent.text.toString()
            val textSubject = editTextSubject.text.toString()
            val textMark = editTextMark.text.toString()
            val textViewMarkResult = TextView(this)
            textViewMarkResult.setTextSize(18f)
            textViewMarkResult.setPadding(20, 20, 20, 20)
            if (textStudent.isBlank() ||
                textSubject.isBlank() ||
                textMark.isBlank()
                ){
                Toast.makeText(this, "Некоторые данные не заполнены", Toast.LENGTH_SHORT).show()
            }
            else if (textMark.toInt() !in 2..5){
                Toast.makeText(this, "Некорректное значение оценки", Toast.LENGTH_SHORT).show()
            }
            else {
                textViewMarkResult.text = "Студент: $textStudent\nПредмет: $textSubject\nОценка: $textMark"
                linearLayout.addView(textViewMarkResult)
                Toast.makeText(this, "Вы добавили новую оценку!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}