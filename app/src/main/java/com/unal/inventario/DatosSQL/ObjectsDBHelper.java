package com.unal.inventario.DatosSQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ObjectsDBHelper extends SQLiteOpenHelper {

    /*private static final int    DATABASE_VERSION    = 1;
    private static final String DATABASE_NAME       = "Inventario.db";*/

    public static abstract class Objeto implements BaseColumns
    {
        public static String TABLE_NAME = "Objetos";
        public static String OBJ_ID     = "ID";
        public static String OBJ_NAME   = "Nombre";
        public static String OBJ_CAT    = "Categoria";
        public static String OBJ_UNDP   = "UnidadP";
        public static String OBJ_UNDS   = "UnidadS";
        public static String OBJ_UND    = "Unidad";

    }

    public ObjectsDBHelper(Context context) {
        super(context, UsuariosDBHelper.DATABASE_NAME, null, UsuariosDBHelper.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "
                + Objeto.TABLE_NAME     + " ("
                + Objeto.OBJ_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Objeto.OBJ_NAME       + " VARCHAR(24) NOT NULL,"
                + Objeto.OBJ_CAT        + " INTEGER NOT NULL,"
                + Objeto.OBJ_UNDP       + " INTEGER NOT NULL,"
                + Objeto.OBJ_UNDS       + " INTEGER NOT NULL,"
                + Objeto.OBJ_UND        + " INTEGER NOT NULL"
                + ")");
        Log.e("Creando tabla", "Se creo la tabla");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
