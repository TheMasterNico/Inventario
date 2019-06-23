package com.unal.inventario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unal.inventario.ObjectItems.AddItem;

public class SelectOptionActivity extends AppCompatActivity {


    public String Nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_option);

        /*Bundle bdl = getIntent().getExtras();
        String Nombre = bdl.getString("userName");*/

        Intent get = getIntent();
        Nombre = get.getStringExtra("userName");

        TextView name = findViewById(R.id.welcome_name);
        name.setText(Nombre);
    }

    public void AddObject(View view) {
        //Toast.makeText(this, "Press", Toast.LENGTH_SHORT).show();
        Intent CambioAddItem = new Intent(this, AddItem.class);
        CambioAddItem.putExtra("userName", Nombre);
        startActivityForResult(CambioAddItem, 0);
    }

    public void ShowListObject(View view) {
        Intent CambioListObject = new Intent(this, ListObject.class);
        CambioListObject.putExtra("userName", Nombre);
        startActivityForResult(CambioListObject, 0);


    }

    public void StartInventario(View view) {
        Intent CambioInventario = new Intent(this, Inventariando.class);
        CambioInventario.putExtra("userName", Nombre);
        startActivityForResult(CambioInventario, 0);
    }
}
