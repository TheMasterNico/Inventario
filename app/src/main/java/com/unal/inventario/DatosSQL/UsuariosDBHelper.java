package com.unal.inventario.DatosSQL;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class UsuariosDBHelper extends SQLiteOpenHelper {

    public static final int    DATABASE_VERSION    = 2;
    public static String DATABASE_NAME       = "Inventario.db";

    public static abstract class Users implements BaseColumns
    { // Creamos las variables para referirnos al nombre de la columna
        public static final String TABLE_NAME   = "Usuarios";
        public static final String ID           = "idUsuario";
        public static final String NAME         = "Nombre";
        public static final String PASS         = "PASS";
        public static final String ADMIN        = "ADMIN";
    }
    public UsuariosDBHelper(Context context)
    {   // Constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + Users.TABLE_NAME  + " ("
                + Users.ID          + " UNSIGNED INT PRIMARY KEY,"

                + Users.NAME        + " VARCHAR(42) NOT NULL,"
                + Users.PASS        + " VARCHAR(42) NOT NULL,"
                + Users.ADMIN       + " INT(1) NOT NULL,"
                + "UNIQUE ("
                + Users.ID
                + "))");
        // Contenedor de valores
        ContentValues values = new ContentValues();

        // Pares clave-valor
        values.put(Users.ID, "11001100");
        values.put(Users.NAME, "El Administrador");
        values.put(Users.PASS, "toor");
        values.put(Users.ADMIN, "1");

        // Insertar...
        db.insert(Users.TABLE_NAME, null, values);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }




}
