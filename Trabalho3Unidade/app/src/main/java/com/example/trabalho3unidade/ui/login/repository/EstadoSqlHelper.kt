package com.example.trabalho3unidade.ui.login.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EstadoSqlHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            "CREATE TABLE $TABLE_ESTADOS(" +
                    "$COLUMN_ESTADOS_ID INTEGER PRIMARY KEY," +
                    "$COLUMN_ESTADOS_SIGLA TEXT NOT NULL," +
                    "$COLUMN_ESTADOS_ESTADO TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}