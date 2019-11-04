package com.example.trabalho3unidade.ui.login.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CidadeSqlHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            "CREATE TABLE $TABLE_CIDADES(" +
                    "$COLUMN_CIDADES_ID INTEGER NOT NULL, " +
                    "$COLUMN_CIDADES_CIDADE TEXT NOT NULL, " +
                    "$COLUMN_CIDADES_ESTADO INT NOT NULL)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}