package com.example.trabalho3unidade.ui.login.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho3unidade.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegistrar.setOnClickListener {
            salvar()
        }
    }

    fun salvar(){
        var email = txtEmail.text
        var senha = txtSenha.text
        if(email.isNullOrEmpty() || senha.isNullOrEmpty()){
            Toast.makeText(this, "Campos inválidos!", Toast.LENGTH_SHORT).show()
        } else {
            val pref = getSharedPreferences("config_login", 0)

            val edit = pref.edit()
            edit.putString("email", email.toString())
            edit.putString("senha", senha.toString())
            edit.commit()

            finish()
        }
    }
}