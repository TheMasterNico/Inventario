package com.unal.inventario;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.unal.inventario.DatosSQL.HistoricoInv;
import com.unal.inventario.DatosSQL.ObjectsDBHelper;
import com.unal.inventario.DatosSQL.SQL;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.unal.inventario.DatosSQL.ObjectsDBHelper.Objeto.OBJ_ID;
import static com.unal.inventario.DatosSQL.ObjectsDBHelper.Objeto.OBJ_UNDS;

public class Inventariando extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventariando);
		
		
		String Nombre;
		Intent get = getIntent();
		Nombre = get.getStringExtra("userName");
		int user_id = get.getIntExtra("userID", 1111001111);
		
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
						OBJ_UNDS,
						OBJ_ID
						//ObjectsDBHelper.Objeto.OBJ,
				},
				new int[] {
						R.id.invName,
						R.id.invundp,
						R.id.invunds,
						R.id.idObjeto
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
	
	public void Aplicar_Cambios(View view) {
		Actualizar();
	}
	
	public void GuardarCambios(View view) {
		Actualizar(); // Aplicamos los cambios
		// Einmediatamente guardamos
		SQL DBH = new SQL(this);//Creamos el objeto
		SQLiteDatabase db = DBH.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM " + HistoricoInv.Configuracion.TABLE_CONFIG, null);
		if (c != null)
		{
			if (c.moveToFirst()) // El if Evita crash
			{
				ContentValues valores = new ContentValues();
				valores.put(HistoricoInv.Historico.HIST_SAVE, 1);
				//Actualizamos la columnda "HIST_SAVE" de "no se a guardado" a "Ya se guardo" y no se podra modificar
				db.update(HistoricoInv.Historico.TABLE_HIST, valores,
						HistoricoInv.Historico.HIST_TIME + " = ?",
						new String[]{c.getString(c.getColumnIndex(HistoricoInv.Configuracion.LAST_TIME))});
				// y como ya no necesitamos el ultimo "tiempo" de guardado que se estaba modificando, lo eliminamos
				
				db.delete(HistoricoInv.Configuracion.TABLE_CONFIG, null, null);
			}
		}
		
		db.close();
		DBH.close();
	}
	
	public void Actualizar()
	{
		SQL DBH = new SQL(this);//Creamos el objeto
		SQLiteDatabase db = DBH.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM " + HistoricoInv.Configuracion.TABLE_CONFIG, null);
		
		
		ListView lista = findViewById(R.id.lv_inventariar);
		
		int user_id = getIntent().getIntExtra("userID", 123456);
		int max =  lista.getAdapter().getCount();
		String tiempo = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		
		ContentValues values = new ContentValues();
		
		int decision = 0; //0: Crear nuevo dato. 1: Ya hay un dato sin "Guardar" 2: Ya hay un dato "Guardado"
		if (c != null)
		{
			if (c.moveToFirst()) // El if Evita crash
			{
				tiempo = c.getString(c.getColumnIndex(HistoricoInv.Configuracion.LAST_TIME));
				decision = 1; // ya hay algo, ya le habia dado antes "aplicar". Hay que "UPDATE"
			}
			//else
			//{
			// No hay nada, acaba de darle aplicar por primera vez. HAY QUE "INSERT" entonces decision sigue en 0
			//}
		}
		
		if(decision == 0)
		{
			// Se crea un nuevo registro
			ContentValues nuevoRegistro = new ContentValues();
			nuevoRegistro.put(HistoricoInv.Configuracion.LAST_TIME, tiempo);
			db.insert(HistoricoInv.Configuracion.TABLE_CONFIG, null, nuevoRegistro);
		}
		for(int i = 0; i < max; i++)
		{
			View viewLista = lista.getChildAt(i);
			
			//Obtenemos los datos
			EditText dataPrincipal = viewLista.findViewById(R.id.cantP);
			TextView DBID = viewLista.findViewById(R.id.idObjeto);
			EditText dataSecundaria = findViewById(R.id.canS);
			
			// Los guardamos en un contentValue
			values.put(HistoricoInv.Historico.HIST_USER, user_id);
			values.put(HistoricoInv.Historico.HIST_OBJ, String.valueOf(DBID.getText()));
			
			values.put(HistoricoInv.Historico.HIST_CANTP, String.valueOf(dataPrincipal.getText()));
			values.put(HistoricoInv.Historico.HIST_CANTS, String.valueOf(dataSecundaria.getText()));
			
			if(decision == 0) {
				values.put(HistoricoInv.Historico.HIST_TIME, tiempo);
				db.insert(HistoricoInv.Historico.TABLE_HIST, null, values);
			}
			else {
				db.update(HistoricoInv.Historico.TABLE_HIST, values,
						HistoricoInv.Historico.HIST_TIME + " = ? AND "
								+ HistoricoInv.Historico.HIST_OBJ + " = ?"
						, new String[] {tiempo, String.valueOf(DBID.getText())});
			}
			//Log.e("Datos", DBID.getText().toString());
			//Log.e("Datos: ", viewLista.getResources().getResourceName(R.id.cantP));
		}
		
		db.close();
		DBH.close();
	}
	
	public void SubirPrincipalHandler(View view) {
		//reset all the listView items background colours
		
		//before we set the clicked one..
		
		
		/*ListView lvItems = findViewById(R.id.lv_inventariar);
		for (int i=0; i < lvItems.getChildCount(); i++)
		{
			lvItems.getChildAt(i).setBackgroundColor(Color.BLUE);
		}*/
		
		//Obtenemos el parent que contiene los botones
		ConstraintLayout vwParentRow = (ConstraintLayout)view.getParent();
		
		EditText cantidadP = (EditText) vwParentRow.getChildAt(2); // Obtenemos la ID del EditText de cantidad principal
		String numeroString = cantidadP.getText().toString();
		if(numeroString.equals("")) // si no se ha escrito nada
		{
			numeroString = "0"; // Le ponemos un cero al valor
		}
		int numero = Integer.decode(numeroString) + 1;	 // Obtenemos el numero, lo convertimos y le sumamos 1
		
		
		cantidadP.setText(String.valueOf(numero)); // Le ponemos el texto al edittext
		
		
		/*Button btnChild = (Button) vwParentRow.getChildAt(4);
		btnChild.setText("I've been clicked!");*/
		
		//int c = Color.CYAN;
		
		//vwParentRow.setBackgroundColor(c);
		//vwParentRow.refreshDrawableState();
	}
	
	public void bajarPrincipalHandler(View view) {
		ConstraintLayout vwParentRow = (ConstraintLayout)view.getParent();
		
		EditText cantidadP = (EditText) vwParentRow.getChildAt(2); // Obtenemos la ID del EditText de cantidad principal
		String numeroString = cantidadP.getText().toString();
		if(numeroString.equals("")) // si no se ha escrito nada
		{
			numeroString = "0"; // Le ponemos un cero al valor
		}
		int numero = Integer.decode(numeroString) - 1;	 // Obtenemos el numero, lo convertimos y le restamos 1
		if(numero < 0) numero = 0; // Evitamos cantidades negativas
		
		
		cantidadP.setText(String.valueOf(numero)); // Le ponemos el texto al edittext
	}
	
	public void SubirSecundario(View view) {
		ConstraintLayout vwParentRow = (ConstraintLayout)view.getParent();
		
		EditText cantidadS = (EditText) vwParentRow.getChildAt(7); // Obtenemos la ID del EditText de cantidad secundaria
		String numeroString = cantidadS.getText().toString();
		if(numeroString.equals("")) // si no se ha escrito nada
		{
			numeroString = "0.0"; // Le ponemos un cero al valor
		}
		double numero = Double.parseDouble(numeroString) + 0.2;	 // Obtenemos el numero, lo convertimos y le sumamos 1
		
		
		cantidadS.setText(String.valueOf(numero)); // Le ponemos el texto al edittext
		
	}
	
	public void BajarSecundario(View view) {
		ConstraintLayout vwParentRow = (ConstraintLayout)view.getParent();
		
		EditText cantidadS = (EditText) vwParentRow.getChildAt(7); // Obtenemos la ID del EditText de cantidad secundaria
		String numeroString = cantidadS.getText().toString();
		if(numeroString.equals("")) // si no se ha escrito nada
		{
			numeroString = "0"; // Le ponemos un cero al valor
		}
		double numero = Double.parseDouble(numeroString) - 0.20000;	 // Obtenemos el numero, lo convertimos y le restamos 1
		if(numero < 0.000010) numero = 0.0; // Evitamos cantidades negativas
		
		
		cantidadS.setText(String.valueOf(numero)); // Le ponemos el texto al edittext
	}
	
}


/*

SELECT
	b.Nombre,
	a.cant_p,
	a.cant_s,
	(CASE WHEN a.guardado = 1 THEN 'Guardado' ELSE 'Modificando' END) as Guardado,
	c.Nombre,
	DATETIME(a.fecha, 'unixepoch', 'localtime') as fecha_ingreso
FROM
	Historico_Inventario a,
	Objetos b,
	Usuarios c
WHERE
	a.user_id == c.idUsuario AND
	a.obj_id == b._id;

* */