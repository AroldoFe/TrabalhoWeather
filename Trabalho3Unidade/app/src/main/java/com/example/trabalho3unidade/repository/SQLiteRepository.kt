package com.example.trabalho3unidade.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Estado

class SQLiteRepository(ctx: Context): EstadoRepository,
    CidadeRepository {

    private val cHelper: CidadeSqlHelper = CidadeSqlHelper(ctx)
    private val eHelper: EstadoSqlHelper = EstadoSqlHelper(ctx)

    override fun save(estado: Estado) {
        val db = eHelper.writableDatabase

        val cv = ContentValues().apply {
            put(COLUMN_ESTADOS_ID, estado.id)
            put(COLUMN_ESTADOS_SIGLA, estado.sigla)
            put(COLUMN_ESTADOS_ESTADO, estado.nome)
        }

        db.insert(TABLE_ESTADOS, null, cv)
        db.close()
    }

    override fun save(cidade: Cidade) {
        val db = cHelper.writableDatabase

        val cv = ContentValues().apply {
            put(COLUMN_CIDADES_ID, cidade.id)
            put(COLUMN_CIDADES_CIDADE, cidade.nome)
            put(COLUMN_CIDADES_ESTADO, cidade.UF.id)
        }

        db.insert(TABLE_CIDADES, null, cv)
        db.close()
    }

    private fun recuperar(sigla: String): Estado{
        val sql = "SELECT * FROM $TABLE_ESTADOS WHERE $COLUMN_ESTADOS_SIGLA = $sigla"
        val db = eHelper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        val estados = ArrayList<Estado>()

        while(cursor.moveToNext()){
            val estado = estadoFromCursor(cursor)
            estados.add(estado)
        }

        cursor.close()
        db.close()
        return estados[0]
    }

    private fun recuperar(id: Int): Estado{
        val sql = "SELECT * FROM $TABLE_ESTADOS WHERE $COLUMN_ESTADOS_ID = $id"
        val db = eHelper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        val estados = ArrayList<Estado>()

        while(cursor.moveToNext()){
            val estado = estadoFromCursor(cursor)
            estados.add(estado)
        }

        cursor.close()
        db.close()
        return estados[0]
    }

    override fun list(): List<Estado> {
        val sql = "SELECT * FROM $TABLE_ESTADOS ORDER BY $COLUMN_ESTADOS_ESTADO"
        val db = eHelper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        val estados = ArrayList<Estado>()

        while(cursor.moveToNext()){
            val estado = estadoFromCursor(cursor)
            estados.add(estado)
        }

        cursor.close()
        db.close()
        return estados
    }

    fun listCidades(): List<Cidade> {
        val sql = "SELECT * FROM $TABLE_CIDADES"
        val db = cHelper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        val cidades = ArrayList<Cidade>()

        while(cursor.moveToNext()){
            val estado = recuperar(cursor.getInt(cursor.getColumnIndex(COLUMN_CIDADES_ESTADO)))
            val cidade = cidadeFromCursor(cursor, estado)
            cidades.add(cidade)
        }

        cursor.close()
        db.close()
        return cidades
    }

    fun listCidades(estado: Estado): ArrayList<Cidade>{
        val sql = "SELECT * FROM $TABLE_CIDADES WHERE $COLUMN_CIDADES_ESTADO = ${estado.id} ORDER BY $COLUMN_CIDADES_CIDADE"
        val db = eHelper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        val cidades = ArrayList<Cidade>()

        while(cursor.moveToNext()){
            val cidade = cidadeFromCursor(cursor, estado)
            cidades.add(cidade)
        }

        cursor.close()
        db.close()
        return cidades
    }

    override fun list(sigla: String): List<Cidade> {
        val estado = recuperar(sigla)

        val sql = "SELECT * FROM $TABLE_CIDADES WHERE $COLUMN_CIDADES_ESTADO = ${estado.id}"
        val db = cHelper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        val cidades = ArrayList<Cidade>()

        while (cursor.moveToNext()){
            val cidade = cidadeFromCursor(cursor, estado)
            cidades.add(cidade)
        }

        cursor.close()
        db.close()
        return cidades
    }

    private fun estadoFromCursor(cursor: Cursor): Estado{
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ESTADOS_ID))
        val sigla = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADOS_SIGLA))
        val nome = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADOS_ESTADO))

        return Estado(id, sigla, nome)
    }

    private fun cidadeFromCursor(cursor: Cursor, estado: Estado): Cidade{
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_CIDADES_ID))
        val nome = cursor.getString(cursor.getColumnIndex(COLUMN_CIDADES_CIDADE))

        return Cidade(id, nome, estado)
    }
}