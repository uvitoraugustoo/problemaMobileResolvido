package db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.Usuario

class DbUsuariosHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOME = "nome"
        private const val COL_EMAIL = "email"
        private const val COL_SENHA = "senha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOME TEXT NOT NULL,
                $COL_EMAIL TEXT NOT NULL UNIQUE,
                $COL_SENHA TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun inserirUsuario(usuario: Usuario): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME, usuario.nome)
            put(COL_EMAIL, usuario.email)
            put(COL_SENHA, usuario.senha)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getTodosUsuarios(): List<Usuario> {
        val lista = mutableListOf<Usuario>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val usuario = Usuario(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    senha = cursor.getString(cursor.getColumnIndexOrThrow(COL_SENHA))
                )
                lista.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    fun atualizarUsuario(usuario: Usuario): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME, usuario.nome)
            put(COL_EMAIL, usuario.email)
            put(COL_SENHA, usuario.senha)
        }
        return db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(usuario.id.toString()))
    }

    fun deletarUsuario(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
    }

    fun autenticar(email: String, senha: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ? AND $COL_SENHA = ?",
            arrayOf(email, senha)
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }
}
