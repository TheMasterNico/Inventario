package com.unal.inventario.DatosSQL;

import android.provider.BaseColumns;

public class UsuariosDBHelper {

    public static abstract class Users implements BaseColumns
    { // Creamos las variables para referirnos al nombre de la columna
        public static final String TABLE_NAME   = "Usuarios";
        public static final String ID           = "_id";
        public static final String NAME         = "Nombre";
        public static final String PASS         = "PASS";
        public static final String ADMIN        = "ADMIN";
    }
}
