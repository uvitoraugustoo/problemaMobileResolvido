package view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kleberson.problemamobile.R
import model.Usuario
import controller.UsuarioController


class RegisterActivity : AppCompatActivity() {

    private lateinit var usuarioController: UsuarioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usuarioController = UsuarioController(this)

        val name = findViewById<EditText>(R.id.editTextNameRegister)
        val email = findViewById<EditText>(R.id.editTextEmailRegister)
        val password = findViewById<EditText>(R.id.editTextPasswordRegister)
        val confirmPassword = findViewById<EditText>(R.id.editTextConfirmRegister)
        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val linkLogin = findViewById<TextView>(R.id.textViewLinkLogin)

        registerButton.setOnClickListener {
            val nameText = name.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()
            val confirmPasswordText = confirmPassword.text.toString().trim()

            if (nameText.isEmpty()) {
                name.error = "Nome é obrigatório"
                return@setOnClickListener
            }
            if (emailText.isEmpty()) {
                email.error = "Email é obrigatório"
                return@setOnClickListener
            }
            if (passwordText.isEmpty()) {
                password.error = "Senha é obrigatória"
                return@setOnClickListener
            }
            if (confirmPasswordText.isEmpty()) {
                confirmPassword.error = "Confirme sua senha"
                return@setOnClickListener
            }
            if (passwordText != confirmPasswordText) {
                confirmPassword.error = "As senhas não coincidem"
                return@setOnClickListener
            }

            val usuario = Usuario(0, nameText, emailText, passwordText)
            val sucesso = usuarioController.inserir(usuario)

            if (sucesso) {
                Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro ao registrar usuário", Toast.LENGTH_SHORT).show()
            }
        }

        linkLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
