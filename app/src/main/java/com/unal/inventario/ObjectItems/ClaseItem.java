package com.unal.inventario.ObjectItems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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




    public ClaseItem(String nombre, int categoria, int unidadPrincipal, int unidadSecundaria, int unidad) {
        Nombre = nombre;
        Categoria = categoria;
        UnidadPrincipal = unidadPrincipal;
        UnidadSecundaria = unidadSecundaria;
        Unidad = unidad;
        Log.e("Constructor claseitem: ", Nombre + "-" + Categoria + "-" + UnidadPrincipal + "-" + UnidadSecundaria + "-" + Unidad);
    }

    public void InsertarDatos(SQLiteDatabase db)
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
        Cursor c = db.query(ObjectsDBHelper.Objeto.TABLE_NAME, null,null, null,null, null, null);

        if (c != null) {
            if(c.moveToFirst()) { // El if Evita crash
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                /*user = c.getString(c.getColumnIndex(UsuariosDBHelper.Users.NAME));
                pass = c.getString(c.getColumnIndex(UsuariosDBHelper.Users.PASS));
                Toast.makeText(this, user + "--" + pass, Toast.LENGTH_SHORT).show();

                Intent CambioaSelect = new Intent(this, SelectOptionActivity.class);
                CambioaSelect.putExtra("userName", user);
                startActivityForResult(CambioaSelect, 0);*/
                do {
                    String name = c.getString(c.getColumnIndex(ObjectsDBHelper.Objeto.OBJ_NAME));
                    String other = c.getString(c.getColumnIndex(ObjectsDBHelper.Objeto.OBJ_ID));
                    Log.e("Query", name + "-" + other);
                } while(c.moveToNext());

            }
            else
            {
                //Toast.makeText(this, "No se encuentra", Toast.LENGTH_SHORT).show();
                Log.i("Query", "Nope");
            }
        }
    }
}
