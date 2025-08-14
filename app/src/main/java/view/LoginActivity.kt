package view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kleberson.problemamobile.R
import controller.UsuarioController


class LoginActivity : AppCompatActivity() {

    private lateinit var usuarioController: UsuarioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioController = UsuarioController(this)

        val email = findViewById<EditText>(R.id.editTextInputEmail)
        val password = findViewById<EditText>(R.id.editTextInputPassword)
        val loginButton = findViewById<Button>(R.id.buttonEnter)
        val linkRegister = findViewById<TextView>(R.id.textViewLinkRegister)

        loginButton.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (emailText.isEmpty()) {
                email.error = "Email não pode ser vazio"
                return@setOnClickListener
            }
            if (passwordText.isEmpty()) {
                password.error = "Senha não pode ser vazia"
                return@setOnClickListener
            }

            val autenticado = usuarioController.autenticar(emailText, passwordText)

            if (autenticado) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        linkRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}
