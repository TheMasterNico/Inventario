package com.unal.inventario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.unal.inventario.DatosSQL.ObjectsDBHelper;
import com.unal.inventario.DatosSQL.SQL;

import static com.unal.inventario.DatosSQL.ObjectsDBHelper.Objeto.OBJ_UNDS;

public class Inventariando extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariando);

        String Nombre;
        Intent get = getIntent();
        Nombre = get.getStringExtra("userName");

        TextView name = findViewById(R.id.name_user);
        name.setText(Nombre);

        SQL DBH = new SQL(this);//Creamos el objeto
        SQLiteDatabase db = DBH.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + ObjectsDBHelper.Objeto.TABLE_NAME, null);

        ListView lista = findViewById(R.id.lv_inventariar);

        ListAdapter a = new SimpleCursorAdapter(this,
                R.layout.base_inventariar,
                c,
                new String[] {
                        ObjectsDBHelper.Objeto.OBJ_NAME,
                        ObjectsDBHelper.Objeto.OBJ_UNDP,
                        OBJ_UNDS
                        //ObjectsDBHelper.Objeto.OBJ,
                },
                new int[] {
                        R.id.invName,
                        R.id.invundp,
                        R.id.invunds
                        //R.id.cantP
                },
                0);
        lista.setAdapter(a);
        /*if (c != null) {
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String codigo = c.getString(0);
                    String nombre = c.getString(1);
                    Log.e(codigo, nombre);
                } while (c.moveToNext());
            }
        }*/
        db.close();
        DBH.close();


    }
}
