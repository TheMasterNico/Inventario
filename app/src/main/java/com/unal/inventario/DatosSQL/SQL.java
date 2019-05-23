package com.unal.inventario.DatosSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL extends SQLiteOpenHelper {
    public static int       DATABASE_VERSION    = 1;
    public static String    DATABASE_NAME       = "inventario.db";

    public SQL(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* ## Crear la tabla de Uusuarios ## */
        db.execSQL("CREATE TABLE "
            + UsuariosDBHelper.Users.TABLE_NAME  + " ("
            + UsuariosDBHelper.Users.ID          + " UNSIGNED INT PRIMARY KEY,"

            + UsuariosDBHelper.Users.NAME        + " VARCHAR(42) NOT NULL,"
            + UsuariosDBHelper.Users.PASS        + " VARCHAR(42) NOT NULL,"
            + UsuariosDBHelper.Users.ADMIN       + " INT(1) NOT NULL,"
            + "UNIQUE ("
            + UsuariosDBHelper.Users.ID
            + "))"
        );
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(UsuariosDBHelper.Users.ID,       "11001100");
        values.put(UsuariosDBHelper.Users.NAME,     "El Administrador");
        values.put(UsuariosDBHelper.Users.PASS,     "toor");
        values.put(UsuariosDBHelper.Users.ADMIN,    "1");
        // Insertar...
        db.insert(UsuariosDBHelper.Users.TABLE_NAME, null, values);

        /*
                #### Crear la tabla de Objetos (Items) ####
         */
        db.execSQL("CREATE TABLE "
                + ObjectsDBHelper.Objeto.TABLE_NAME     + " ("
                + ObjectsDBHelper.Objeto.OBJ_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ObjectsDBHelper.Objeto.OBJ_NAME       + " VARCHAR(24) NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_CAT        + " INTEGER NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_UNDP       + " INTEGER NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_UNDS       + " INTEGER NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_UND        + " INTEGER NOT NULL,"
                + "UNIQUE ("
                + ObjectsDBHelper.Objeto.OBJ_NAME
                + "))"
        );
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
