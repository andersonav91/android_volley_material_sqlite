package com.allcode.starter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import db.SQLiteHandler
import db.model.Contact
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    protected lateinit var fullName: TextInputEditText;
    protected lateinit var email: TextInputEditText;
    protected lateinit var phone: TextInputEditText;
    protected lateinit var subject: TextInputEditText;

    var button: MaterialButton? = null;

    var title: TextView? = null;

    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        this.fullName = findViewById(R.id.fullName);
        this.email = findViewById(R.id.email);
        this.phone = findViewById(R.id.phone);
        this.subject = findViewById(R.id.subject);
        this.title = findViewById(R.id.title);

        this.button = findViewById(R.id.save_button);

        this.button?.setOnClickListener {
            System.out.println(this.fullName.text);
            System.out.println(this.email.text);
            System.out.println(this.phone.text);
            System.out.println(this.subject.text);
            this.volleyTest();
            this.saveContact(Integer.parseInt(this.phone.text.toString()),
                this.fullName.text.toString(), this.subject.text.toString(), this.email.text.toString());
        }
    }

    private fun volleyTest() {
        val url = "https://jsonplaceholder.typicode.com/todos/1"
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            val title = response.optString("title");
            this.title?.text = title;
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    fun saveContact(phone: Int, fullName: String, subject: String, email: String){
        val databaseHandler: SQLiteHandler = SQLiteHandler(this)
        if(phone.toString().trim() != "" && subject.trim() != "" &&
            fullName.trim() != "" && email.trim() != ""){
            val status = databaseHandler.addContact(Contact(1, phone, email, subject, fullName))
            if(status > -1){
                Toast.makeText(applicationContext,"record save", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(applicationContext,"Invalid fields", Toast.LENGTH_LONG).show()
        }

    }
}
