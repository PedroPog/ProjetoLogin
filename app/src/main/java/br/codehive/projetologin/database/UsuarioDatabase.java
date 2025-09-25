package br.codehive.projetologin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.codehive.projetologin.model.PerfilModel;

public class UsuarioDatabase {

    private DBHandler dbHandler;
    private static String TABLE = "usuario";
    private static String COL_NOME = "name";
    private static String COL_EMAIL = "email";
    private static String COL_PASS = "password";
    private static String COL_IMG = "imgperfil";
    private static String COL_ID = "id";

    public UsuarioDatabase(Context context) {
        dbHandler = new DBHandler(context);
    }

    public PerfilModel loginUsuario(PerfilModel model) throws SQLException {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = null;
        PerfilModel retornModel = null;
        try {
            String selection = COL_EMAIL + " = ?";
            String[] selectionArgs = { model.getEmail() };
            cursor = db.query(
                    TABLE,
                    null, // null retorna todas as colunas (*)
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                retornModel = new PerfilModel();
                retornModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                retornModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)));
                retornModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                retornModel.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASS)));
                retornModel.setImagemPerfil(cursor.getString(cursor.getColumnIndexOrThrow(COL_IMG)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return retornModel;
    }
    public long inserirUsuario(PerfilModel model) throws SQLException {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        long retorno = -1;
        try {
            values.put(COL_NOME, model.getName());
            values.put(COL_EMAIL, model.getEmail());
            values.put(COL_PASS, model.getPassword());
            values.put(COL_IMG, model.getImagemPerfil());
            retorno = db.insertOrThrow(TABLE, null, values); // insertOrThrow lança exceção em caso de erro
        } finally {
            db.close();
        }
        return retorno;
    }
    public long updateUsuario(PerfilModel model) throws SQLException {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        long retorno = 0;
        try {
            if (model.getName() != null && !model.getName().isEmpty()) values.put(COL_NOME, model.getName());
            if (model.getEmail() != null && !model.getEmail().isEmpty()) values.put(COL_EMAIL, model.getEmail());
            if (model.getPassword() != null && !model.getPassword().isEmpty()) values.put(COL_PASS, model.getPassword());
            if (model.getImagemPerfil() != null && !model.getImagemPerfil().isEmpty()) values.put(COL_IMG, model.getImagemPerfil());

            String selection = COL_ID + " = ?";
            String[] args = { String.valueOf(model.getId()) };
            retorno = db.update(TABLE, values, selection, args);
        } finally {
            db.close();
        }
        return retorno;
    }
    public long deleteUsuario(long id) throws SQLException {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        long retorno = 0;
        try {
            String selection = COL_ID + " = ?";
            String[] args = { String.valueOf(id) };
            retorno = db.delete(TABLE, selection, args);
        } finally {
            db.close();
        }
        return retorno;
    }
}
