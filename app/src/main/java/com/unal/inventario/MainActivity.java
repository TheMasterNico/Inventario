package com.unal.inventario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unal.inventario.DatosSQL.UsuariosDBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Connection conexionMySQL = null;
        Class.forName("com.mysql.jdbc.Driver").newInstance ();
        conexionMySQL = DriverManager.getConnection("jdbc:mysql://localhost:3360/inventario", "root", "6582039");
        Log.i("TAGGER", "Mensaje");*/
    }

    public void LoginUser(View view)
    {
    // Obtenemos el CC y la pass
        EditText IDCC = findViewById(R.id.LaCC);
        String CC = IDCC.getText().toString();

        EditText IDPass = findViewById(R.id.Pass);
        String Contra = IDPass.getText().toString();
        //Enviamos el texto para ver si existe o no
        //UsuariosDBHelper.SelectUser(getApplicationContext(), CC, Contra).show();
        //Cursor cursor = UsuariosDBHelper.SelectUser(new String[]{CC, Contra});

        UsuariosDBHelper DBH = new UsuariosDBHelper(this);
        SQLiteDatabase db = DBH.getWritableDatabase();


        Cursor c = db.query(UsuariosDBHelper.Users.TABLE_NAME, null,
                UsuariosDBHelper.Users.ID + " = ? AND " + UsuariosDBHelper.Users.PASS + " = ?",
                new String[]{CC, Contra},
                null, null, null);

        String pass, user;
        if (c != null) {
            if(c.moveToFirst()) { // El if Evita crash
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                user = c.getString(c.getColumnIndex(UsuariosDBHelper.Users.NAME));
                pass = c.getString(c.getColumnIndex(UsuariosDBHelper.Users.PASS));
                Toast.makeText(this, user + "--" + pass, Toast.LENGTH_SHORT).show();

                Intent CambioaSelect = new Intent(this, SelectOptionActivity.class);
                CambioaSelect.putExtra("userName", user);
                startActivityForResult(CambioaSelect, 0);

            }
            else
            {
                Toast.makeText(this, "No se encuentra", Toast.LENGTH_SHORT).show();
            }
        }

        db.close();
        DBH.close();
    }

}
