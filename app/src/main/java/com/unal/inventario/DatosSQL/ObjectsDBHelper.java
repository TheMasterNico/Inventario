package com.unal.inventario.DatosSQL;


import android.provider.BaseColumns;

public class ObjectsDBHelper {

    public static abstract class Objeto implements BaseColumns
    {
        public static String TABLE_NAME = "Objetos";
        public static String OBJ_ID     = "_id";
        public static String OBJ_NAME   = "Nombre";
        public static String OBJ_CAT    = "Categoria";
        public static String OBJ_UNDP   = "UnidadP";
        public static String OBJ_UNDS   = "UnidadS";
        public static String OBJ_UND    = "Unidad";

    }
}
