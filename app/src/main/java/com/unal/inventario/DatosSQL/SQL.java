package com.unal.inventario.DatosSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS "
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
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + ObjectsDBHelper.Objeto.TABLE_NAME     + " ("
                + ObjectsDBHelper.Objeto.OBJ_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ObjectsDBHelper.Objeto.OBJ_NAME       + " VARCHAR(24) NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_CAT        + " VARCHAR(24) NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_UNDP       + " VARCHAR(24) NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_UNDS       + " VARCHAR(24) NOT NULL,"
                + ObjectsDBHelper.Objeto.OBJ_UND        + " INTEGER NOT NULL,"
                + "UNIQUE ("
                + ObjectsDBHelper.Objeto.OBJ_NAME
                + "))"
        );
    
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + HistoricoInv.Historico.TABLE_HIST     + " ("
                + HistoricoInv.Historico.HIST_OBJ       + " INTEGER NOT NULL,"
                + HistoricoInv.Historico.HIST_USER      + " INTEGER NOT NULL,"
                + HistoricoInv.Historico.HIST_TIME      + " INTEGER NOT NULL,"
                + HistoricoInv.Historico.HIST_CANTP     + " INTEGER NOT NULL DEFAULT 0,"
                + HistoricoInv.Historico.HIST_CANTS     + " DOUBLE NOT NULL DEFAULT 0,"
                + HistoricoInv.Historico.HIST_SAVE      + " INTEGER NOT NULL DEFAULT 0"
                + ")"
        );
    
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + HistoricoInv.Configuracion.TABLE_CONFIG     + " ("
                //+ HistoricoInv.Configuracion.LAST_IS_SAVE     + " INTEGER NOT NULL"
                + HistoricoInv.Configuracion.LAST_TIME        + " INTEGER NOT NULL"
                + ")"
        );
        
        
        
        //db.close(); // Esto da error attempt to re-open an already-closed object: SQLiteDatabase

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + UsuariosDBHelper.Users.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ObjectsDBHelper.Objeto.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HistoricoInv.Historico.TABLE_HIST);*/
        db.execSQL("DROP TABLE IF EXISTS " + HistoricoInv.Configuracion.TABLE_CONFIG);
        Log.e("SQL", "Probando");
        onCreate(db);
    }
}
