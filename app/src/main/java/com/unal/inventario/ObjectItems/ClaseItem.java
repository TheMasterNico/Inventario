package com.unal.inventario.ObjectItems;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.unal.inventario.DatosSQL.ObjectsDBHelper;


public class ClaseItem
{
    private String  Nombre;             // Nombre del objeto
    private String  Lote;               // Lote de fabricacion
    private int     Vencimiento;        // Fecha de vencimiento epoch
    private int     Categoria;          // 0: Refrigerado. 1: Secos 2: Congelados
    private int     UnidadPrincipal;    // Paquetes, bolsas, cajas, etc...
    private int     UnidadSecundaria;   // Unidad, KG, Gramos, litros, etc...
    private int     Unidad;             // Cada UnidadPrincipal contiene X UnidadSecundaria

    public ClaseItem(String nombre, String lote, int vencimiento, int categoria, int unidadPrincipal, int unidadSecundaria) {
        Nombre = nombre;
        Lote = lote;
        Vencimiento = vencimiento;
        Categoria = categoria;
        UnidadPrincipal = unidadPrincipal;
        UnidadSecundaria = unidadSecundaria;
    }


    public ClaseItem(String nombre, int categoria, int unidadPrincipal, int unidadSecundaria, int unidad) {
        Nombre = nombre;
        Categoria = categoria;
        UnidadPrincipal = unidadPrincipal;
        UnidadSecundaria = unidadSecundaria;
        Unidad = unidad;
        Log.e("Constructor claseitem: ", Nombre + "-" + Categoria + "-" + UnidadPrincipal + "-" + UnidadSecundaria + "-" + Unidad);
    }

    public void InsertarDatos(Context context, SQLiteDatabase db)
    {

        ContentValues Valores = new ContentValues();
        Valores.put(ObjectsDBHelper.Objeto.OBJ_NAME,    Nombre);
        Valores.put(ObjectsDBHelper.Objeto.OBJ_CAT,     Categoria);
        Valores.put(ObjectsDBHelper.Objeto.OBJ_UNDP,    UnidadPrincipal);
        Valores.put(ObjectsDBHelper.Objeto.OBJ_UNDS,    UnidadSecundaria);
        Valores.put(ObjectsDBHelper.Objeto.OBJ_UND,     Unidad);
        db.insert(ObjectsDBHelper.Objeto.TABLE_NAME, null, Valores);
    }

    public void ConsultarObjetos(Context context, SQLiteDatabase db)
    {

    }
}
