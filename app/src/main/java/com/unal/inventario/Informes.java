package com.unal.inventario;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.unal.inventario.DatosSQL.HistoricoInv;
import com.unal.inventario.DatosSQL.ObjectsDBHelper;
import com.unal.inventario.DatosSQL.SQL;
import com.unal.inventario.DatosSQL.UsuariosDBHelper;

import static com.unal.inventario.DatosSQL.ObjectsDBHelper.Objeto.OBJ_ID;
import static com.unal.inventario.DatosSQL.ObjectsDBHelper.Objeto.OBJ_UNDS;

public class Informes extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informes);
		
		SQL DBH = new SQL(this);//Creamos el objeto
		SQLiteDatabase db = DBH.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT " +
						"DISTINCT DATETIME(a.fecha, 'unixepoch', 'localtime') as fecha_ingreso," +
						"a.fecha as epoch, " +
						"c.Nombre as _id" + // Si no dice "_id" dara error y crash
						" FROM " +
						HistoricoInv.Historico.TABLE_HIST + " a," +
						ObjectsDBHelper.Objeto.TABLE_NAME + " b," +
						UsuariosDBHelper.Users.TABLE_NAME + " c" +
						" WHERE " +
						"a."+ HistoricoInv.Historico.HIST_USER + " == c." + UsuariosDBHelper.Users.ID + " AND " +
						"a."+ HistoricoInv.Historico.HIST_OBJ  + " == b._id"// + ObjectsDBHelper.Objeto.OBJ_ID
				, null);
		
		ListView lista = findViewById(R.id.lista_informes);
		ListAdapter a = new SimpleCursorAdapter(this,
				R.layout.base_informes,
				c,
				new String[] {
						"fecha_ingreso",
						"epoch",
						"_id"
				},
				new int[] {
						R.id.inf_fecha,
						R.id.inf_time_int,
						R.id.inf_user
				},
				0);
		lista.setAdapter(a);
		
		db.close();
		DBH.close();
	}
	
	public void Ver_Informe(View view) {
		ConstraintLayout vwParentRow = (ConstraintLayout) view.getParent();
		
		TextView tiempo = (TextView) vwParentRow.getChildAt(5); // Obtenemos la ID del EditText de cantidad principal
		String fecha = tiempo.getText().toString(); // lo pasamos a string
		
		ListView lista = findViewById(R.id.lista_informes);
		lista.setAdapter(null); // borramos los datos de la lista actual y mostraremos el informe
	
		
		
		
		SQL DBH = new SQL(this);//Creamos el objeto
		SQLiteDatabase db = DBH.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT " +
				"b." + ObjectsDBHelper.Objeto.OBJ_NAME +" as Objeto," +
				"a." + HistoricoInv.Historico.HIST_CANTP + " as Cantidad_P," +
				"a." + HistoricoInv.Historico.HIST_CANTS + " as Cantidad_S," +
				"(CASE WHEN a." + HistoricoInv.Historico.HIST_SAVE + " = 1 THEN 'Guardado' ELSE 'Modificando' END) as Guardado," +
				"c." + UsuariosDBHelper.Users.NAME + " as _id" +
				" FROM " +
						HistoricoInv.Historico.TABLE_HIST + " a," +
						ObjectsDBHelper.Objeto.TABLE_NAME + " b," +
						UsuariosDBHelper.Users.TABLE_NAME + " c" +
				" WHERE " +
						"a."+ HistoricoInv.Historico.HIST_USER + " == c." + UsuariosDBHelper.Users.ID + " AND " +
						"a."+ HistoricoInv.Historico.HIST_OBJ  + " == b._id" +
				" AND a."+ HistoricoInv.Historico.HIST_TIME +" = " + fecha
				, null);
		
		ListAdapter a = new SimpleCursorAdapter(this,
				R.layout.base_viendo_informe,
				c,
				new String[] {
						"fecha_ingreso",
						"epoch",
						"_id"
				},
				new int[] {
						R.id.inf_fecha,
						R.id.inf_time_int,
						R.id.inf_user
				},
				0);
		lista.setAdapter(a);
		
		db.close();
		DBH.close();
		
	}
}
