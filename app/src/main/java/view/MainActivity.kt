package view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.problemamobile.R
import controller.DbProdutosController
import model.Produto
import adapter.ProdutoAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var dbController: DbProdutosController
    private lateinit var adapter: ProdutoAdapter
    private val produtos = mutableListOf<Produto>()
    private var produtoEditando: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbController = DbProdutosController(this)

        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val etNome = findViewById<EditText>(R.id.etNome)
        val etPreco = findViewById<EditText>(R.id.etPreco)
        val etQuantidade = findViewById<EditText>(R.id.etQuantidade)
        val rvProdutos = findViewById<RecyclerView>(R.id.rvProdutos)


        produtos.addAll(dbController.listarTodos())

        adapter = ProdutoAdapter(produtos, this::editarProduto, this::removerProduto)
        rvProdutos.layoutManager = LinearLayoutManager(this)
        rvProdutos.adapter = adapter

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString()
            val preco = etPreco.text.toString().toDoubleOrNull()
            val quantidade = etQuantidade.text.toString().toIntOrNull()

            if (nome.isBlank() || preco == null || quantidade == null) {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (produtoEditando == null) {
                // Inserir novo produto
                val novoProduto = Produto(0, nome, preco, quantidade)
                val sucesso = dbController.inserir(novoProduto)
                if (sucesso) {
                    produtos.add(novoProduto)
                    adapter.notifyItemInserted(produtos.size - 1)
                }
            } else {
                // Atualizar produto existente
                produtoEditando?.apply {
                    this.nome = nome
                    this.preco = preco
                    this.quantidade = quantidade
                }
                val sucesso = dbController.atualizar(produtoEditando!!)
                if (sucesso) {
                    val index = produtos.indexOf(produtoEditando!!)
                    adapter.notifyItemChanged(index)
                }
                produtoEditando = null
                btnSalvar.text = "Salvar"
            }

            limparCampos()
        }
    }

    private fun editarProduto(produto: Produto) {
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val etNome = findViewById<EditText>(R.id.etNome)
        val etPreco = findViewById<EditText>(R.id.etPreco)
        val etQuantidade = findViewById<EditText>(R.id.etQuantidade)

        produtoEditando = produto
        etNome.setText(produto.nome)
        etPreco.setText(produto.preco.toString())
        etQuantidade.setText(produto.quantidade.toString())
        btnSalvar.text = "Atualizar"
    }

    private fun removerProduto(produto: Produto) {
        val sucesso = dbController.deletar(produto.id)
        if (sucesso) {
            val index = produtos.indexOf(produto)
            produtos.remove(produto)
            adapter.notifyItemRemoved(index)
        }
    }

    private fun limparCampos() {
        findViewById<EditText>(R.id.etNome).text.clear()
        findViewById<EditText>(R.id.etPreco).text.clear()
        findViewById<EditText>(R.id.etQuantidade).text.clear()
    }
}
