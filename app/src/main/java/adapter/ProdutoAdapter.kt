package adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.problemamobile.R
import model.Produto

class ProdutoAdapter(
    private val produtos: MutableList<Produto>,
    private val onEditar: (Produto) -> Unit,
    private val onRemover: (Produto) -> Unit
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    inner class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        val tvPreco: TextView = itemView.findViewById(R.id.tvPreco)
        val tvQuantidade: TextView = itemView.findViewById(R.id.tvQuantidade)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnRemover: Button = itemView.findViewById(R.id.btnRemover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.tvNome.text = produto.nome
        holder.tvPreco.text = "Preço: R$ ${produto.preco}"
        holder.tvQuantidade.text = "Qtd: ${produto.quantidade}"

        holder.btnEditar.setOnClickListener { onEditar(produto) }
        holder.btnRemover.setOnClickListener { onRemover(produto) }
    }

    override fun getItemCount(): Int = produtos.size
}
