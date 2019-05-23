package com.unal.inventario.ObjectItems;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unal.inventario.DatosSQL.ObjectsDBHelper;
import com.unal.inventario.DatosSQL.SQL;
import com.unal.inventario.R;


public class AddItem extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final Spinner     undp    = findViewById(R.id.spinnerUNDP );
        final Spinner     unds    = findViewById(R.id.spinnerUNDS );


        final OnItemSelectedListener changetext = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                if(spinner == unds) // para diferenciar el spinner
                {
                    TextView chunds = findViewById(R.id.change_unds);
                    chunds.setText(spinner.getSelectedItem().toString());
                }
                else
                {
                    TextView chundp = findViewById(R.id.change_undp);
                    chundp.setText(spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                Log.e("Selecciona", "Nada");
            }
        };
        undp.setOnItemSelectedListener(changetext);
        unds.setOnItemSelectedListener(changetext);


    }








    public void oncAddItem(View view) {

        EditText    name    = findViewById(R.id.editName    ); String namestr   = name.getText().toString();
        Spinner     cat     = findViewById(R.id.spinnerCat  ); int categoria    = (int)cat.getSelectedItemId(); //String catstr    = cat.getSelectedItem().toString();
        Spinner     undp    = findViewById(R.id.spinnerUNDP ); int unidadp      = (int)undp.getSelectedItemId(); //String undpstr   = undp.getSelectedItem().toString();
        Spinner     unds    = findViewById(R.id.spinnerUNDS ); int unidads      = (int)unds.getSelectedItemId(); //String undsstr   = unds.getSelectedItem().toString();
        EditText    und     = findViewById(R.id.editUnidad  ); String unidad    = und.getText().toString();


        //Toast.makeText(this, "Categoria: " + catstr + "-" + cat.getSelectedItemId(), Toast.LENGTH_LONG).show();

        if(TextUtils.isEmpty(namestr) || TextUtils.isEmpty(unidad)) {
            Toast.makeText(this, "Por favor, escribe algo en cada parametro", Toast.LENGTH_LONG).show();
        }
        else
        {

            try {
                SQL DBH = new SQL(this);//Creamos el objeto
                SQLiteDatabase db = DBH.getWritableDatabase(); // Obtenemos la id de la database

                ClaseItem AgregarObjeto = new ClaseItem(namestr, categoria, unidadp, unidads, Integer.parseInt(unidad));

                AgregarObjeto.InsertarDatos(db);
                AgregarObjeto.ConsultarObjetos(this, db);

                db.close();
                DBH.close();
                onBackPressed();
            } catch (SQLiteConstraintException e) {
                Toast.makeText(this, "Ya existe un objeto con ese nombre!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
