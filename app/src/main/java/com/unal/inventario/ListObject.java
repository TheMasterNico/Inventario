package com.unal.inventario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.unal.inventario.DatosSQL.ObjectsDBHelper;
import com.unal.inventario.DatosSQL.SQL;

public class ListObject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_object);

       /* String Nombre;

        Intent get = getIntent();
        Nombre = get.getStringExtra("userName");

        TextView name = findViewById(R.id.textView2);
        name.setText(Nombre);*/

        SQL DBH = new SQL(this);//Creamos el objeto
        SQLiteDatabase db = DBH.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + ObjectsDBHelper.Objeto.TABLE_NAME, null);

        ListView lista = findViewById(R.id.ListaObjects);

        ListAdapter a = new SimpleCursorAdapter(this,
                R.layout.baselistview,
                c,
                new String[] {
                        ObjectsDBHelper.Objeto.OBJ_NAME,
                        ObjectsDBHelper.Objeto.OBJ_CAT,
                        ObjectsDBHelper.Objeto.OBJ_UND
                },
                new int[] {
                        R.id.LVNombre,
                        R.id.LVCategoria,
                        R.id.LVCaducidad
                },
                0);
        lista.setAdapter(a);
        if (c != null) {
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String codigo = c.getString(0);
                    String nombre = c.getString(1);
                    Log.e(codigo, nombre);
                } while (c.moveToNext());
            }
        }
        db.close();
        DBH.close();
    }
}
