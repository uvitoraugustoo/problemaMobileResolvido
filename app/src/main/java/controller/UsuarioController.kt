package controller



import android.content.Context
import db.DbUsuariosHelper
import model.Usuario

class UsuarioController(context: Context) {

    private val dbHelper = DbUsuariosHelper(context)

    fun inserir(usuario: Usuario): Boolean {
        val id = dbHelper.inserirUsuario(usuario)
        return id != -1L
    }

    fun listarTodos(): List<Usuario> {
        return dbHelper.getTodosUsuarios()
    }

    fun atualizar(usuario: Usuario): Boolean {
        return dbHelper.atualizarUsuario(usuario) > 0
    }

    fun deletar(id: Int): Boolean {
        return dbHelper.deletarUsuario(id) > 0
    }

    fun autenticar(email: String, senha: String): Boolean {
        return dbHelper.autenticar(email, senha)
    }
}
