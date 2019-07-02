package com.unal.inventario.DatosSQL;

import android.provider.BaseColumns;

public class HistoricoInv {
	public static abstract class Historico implements BaseColumns
	{
		public static String TABLE_HIST		= "Historico_Inventario";
		
		//public static String HIST_ID 		= "_id";
		public static String HIST_OBJ		= "obj_id";
		public static String HIST_USER		= "user_id";
		public static String HIST_CANTP		= "cant_p";
		public static String HIST_CANTS		= "cant_s";
		public static String HIST_TIME		= "fecha"; // fecha en la que se ingreso el inventario
		public static String HIST_SAVE		= "guardado"; // 0: Solo aplicado. 1: Guardado sin poder editar
	}
	public static abstract class Configuracion implements BaseColumns
	{
		public static String TABLE_CONFIG 	= "Configuracion";
		
		
		public static String LAST_TIME	 	= "last_time"; // tiempo del ultimo "Aplicar" sin guardado
		public static String LAST_IS_SAVE 	= "Guardado"; // 1: Ya se guardo. 0: No se ha guardado
	}
}
