package controller



import android.content.Context
import com.kleberson.problemamobile.controller.DbProdutosHelper
import model.Produto

class DbProdutosController(context: Context) {

    private val dbHelper = DbProdutosHelper(context)

    // Inserir produto
    fun inserir(produto: Produto): Boolean {
        val id = dbHelper.inserirProduto(produto)
        return id != -1L
    }

    // Obter todos os produtos
    fun listarTodos(): List<Produto> {
        return dbHelper.getTodosProdutos()
    }

    // Atualizar produto
    fun atualizar(produto: Produto): Boolean {
        return dbHelper.atualizarProduto(produto) > 0
    }

    // Deletar produto
    fun deletar(id: Int): Boolean {
        return dbHelper.deletarProduto(id) > 0
    }
}
