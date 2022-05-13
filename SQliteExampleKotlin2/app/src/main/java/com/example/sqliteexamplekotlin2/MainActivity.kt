package com.example.sqliteexamplekotlin2

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
   lateinit var name: EditText

    lateinit  var surname: EditText

    lateinit  var marks: EditText
    lateinit   var idedit: EditText
    lateinit  var insertbtn: Button
    lateinit var showbtn: Button
    lateinit var deletebtn: Button
    lateinit var updatebtn: Button
    private lateinit var sqliteHelperClass: SQliteHelperClass
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindview()
        sqliteHelperClass = SQliteHelperClass(applicationContext)
        insertbtn.setOnClickListener {
            val name_text = name.text.toString()
            val surnamet_text = surname.text.toString()
            val marks_text = marks.text.toString()
            insertvalues(name_text, surnamet_text, marks_text)
        }
        showbtn!!.setOnClickListener { showData() }
        updatebtn!!.setOnClickListener {
            val name_text = name.text.toString()
            val surnamet_text = surname.text.toString()
            val marks_text = marks.text.toString()
            val userid = idedit.text.toString()
            updatevalues(name_text, surnamet_text, marks_text, userid)
        }
        deletebtn!!.setOnClickListener {
            val userid = idedit.text.toString()
            deletemethod(userid)
        }
    }

    private fun deletemethod(userid: String) {
        val result: Int = sqliteHelperClass.deletedata(userid)
        if (result > 0) {
            Toast.makeText(applicationContext, "value deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "value deletion failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatevalues(
        name_text: String,
        surnamet_text: String,
        marks_text: String,
        userid: String
    ) {
        val res: Boolean =
            sqliteHelperClass.updatetable(userid, name_text, surnamet_text, marks_text)
        if (res) {
            Toast.makeText(applicationContext, "value updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "value updation failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showData() {
        val cursor: Cursor = sqliteHelperClass.showdata()
        if (cursor.count == 0) {
            showwmessage("NOthing", "No data present")
        } else {
            val stringBuffer = StringBuffer() //" "
            while (cursor.moveToNext()) {
                stringBuffer.append(" ID: ${cursor.getString(0)}")



                stringBuffer.append("NAME: ${cursor.getString(1)}")
                stringBuffer.append("SURNAME: ${cursor.getString(2)}")
                stringBuffer.append(" MARKS: ${cursor.getString(3)}")
            }
            textView.text = stringBuffer.toString()
        }
    }

    private fun showwmessage(title: String, body: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setCancelable(true)
        builder.show()
    }

    private fun insertvalues(name_text: String, surnamet_text: String, marks_text: String) {
        val res: Boolean = sqliteHelperClass.insertvalues(name_text, surnamet_text, marks_text)
        if (res) {
            Toast.makeText(applicationContext, "value inserted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "value insertion failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindview() {
        name = findViewById(R.id.student_name)
        surname = findViewById(R.id.student_surnmae)
        marks = findViewById(R.id.student_marks)
        insertbtn = findViewById(R.id.insert_btn)
        showbtn = findViewById(R.id.Show_btn)
        idedit = findViewById(R.id.student_id)
        updatebtn = findViewById(R.id.updatebtn)
        deletebtn = findViewById(R.id.deletebtn)
        textView = findViewById(R.id.textview)
    }
}