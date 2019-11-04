package com.example.trabalho3unidade.ui.login.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.trabalho3unidade.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            goMainActivity()
        }

        registrar.setOnClickListener {
            goRegisterActivity()
        }
    }

    override fun onResume() {
        super.onResume()

        val pref = getSharedPreferences("config_login", 0)
        val email = pref.getString("email", null)
        val senha = pref.getString("senha", null)

        if(!(email.isNullOrEmpty() || senha.isNullOrEmpty())){
            username.text.clear()
            username.text.append(email)

            password.text.clear()
            password.text.append(senha)
        }
    }

    fun testFields(username: String, password: String): Boolean{
        val pref = getSharedPreferences("config_login", 0)
        val email = pref.getString("email", null)
        val senha = pref.getString("senha", null)

        return email.equals(username) && senha.equals(password)
    }

    fun goMainActivity(){
        if(testFields(username.text.toString(), password.text.toString())){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else{
            Toast.makeText(this, "Email ou senha incorreto.", Toast.LENGTH_SHORT).show()
        }
    }

    fun goRegisterActivity(){
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
