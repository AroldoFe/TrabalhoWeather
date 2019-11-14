package com.example.trabalho3unidade.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.trabalho3unidade.utils.LOG

class SqlHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        Log.d(LOG,"\tSQLHelper: Criando tabela de Estados")
        p0?.execSQL(
            "CREATE TABLE $TABLE_ESTADOS(" +
                    "$COLUMN_ESTADOS_ID INTEGER PRIMARY KEY, " +
                    "$COLUMN_ESTADOS_SIGLA TEXT NOT NULL, " +
                    "$COLUMN_ESTADOS_ESTADO TEXT NOT NULL)"
        )
        p0?.execSQL(
            "CREATE TABLE $TABLE_CIDADES(" +
                    "$COLUMN_CIDADES_ID INTEGER PRIMARY KEY, " +
                    "$COLUMN_CIDADES_CIDADE TEXT NOT NULL, " +
                    "$COLUMN_CIDADES_ESTADO INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}