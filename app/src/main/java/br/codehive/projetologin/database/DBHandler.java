package br.codehive.projetologin.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "base";
    private static final int DB_VERSION = 1;

    public DBHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        criarBancoInicial(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                break;
        }
    }

    private void criarBancoInicial(SQLiteDatabase db){
        try{
            String query = "CREATE TABLE usuario (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name TEXT,\n" +
                    "    email TEXT,\n" +
                    "    password TEXT,\n" +
                    "    imgperfil TEXT\n" +
                    ");";
            db.execSQL(query);
            Log.i("CREATEDV","Criação tabela usuario!");
        }catch (SQLException e){
            Log.e("CREATEDV","Erro criação tabela usuario!");
        }
    }
}
