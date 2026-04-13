package com.example.journalofteacher

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            val discipline: EditText = findViewById(R.id.discipline_title)
//            val button: Button = findViewById(R.id.button_create_discipline)
//            val
//            insets
//        }
        val student: EditText = findViewById(R.id.student_full_name)
        val button: Button = findViewById(R.id.button_create_student)
        val linearLayout: LinearLayout = findViewById(R.id.linearlayout)
        button.setOnClickListener {
            val text = student.text.toString()
            val textView = TextView(this)
            textView.setTextSize(18f)
            textView.setPadding(20, 0, 0, 0)
            if (student.text.isNotEmpty()){
                textView.text = text
                linearLayout.addView(textView)
                student.text.clear()
                Toast.makeText(this, "Вы добавили нового студента!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Поле для ФИО студента пусто", Toast.LENGTH_SHORT).show()
            }
        }

    }
}