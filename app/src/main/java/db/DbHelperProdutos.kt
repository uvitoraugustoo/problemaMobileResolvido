package com.kleberson.problemamobile.controller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.Produto

class DbProdutosHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "produtos.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "produtos"
        private const val COL_ID = "id"
        private const val COL_NOME = "nome"
        private const val COL_PRECO = "preco"
        private const val COL_QUANTIDADE = "quantidade"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOME TEXT NOT NULL,
                $COL_PRECO REAL NOT NULL,
                $COL_QUANTIDADE INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun inserirProduto(produto: Produto): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME, produto.nome)
            put(COL_PRECO, produto.preco)
            put(COL_QUANTIDADE, produto.quantidade)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getTodosProdutos(): List<Produto> {
        val lista = mutableListOf<Produto>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val produto = Produto(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)),
                    preco = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRECO)),
                    quantidade = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTIDADE))
                )
                lista.add(produto)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    fun atualizarProduto(produto: Produto): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME, produto.nome)
            put(COL_PRECO, produto.preco)
            put(COL_QUANTIDADE, produto.quantidade)
        }
        return db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(produto.id.toString()))
    }

    fun deletarProduto(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
    }
}
