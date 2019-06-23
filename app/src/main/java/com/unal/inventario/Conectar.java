/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unal.inventario;

//import com.mysql.jdbc.Connection;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author locon
 */
public class Conectar {
    public static Connection obj;

    public Conectar(){
        obj = null;

    }
    //Metodo para visualiza la conexiÃ³n en consola
    public Connection getConnection(){
        return obj;
    }
    //Metodo para visualiza la conexiÃ³n en consola
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            obj = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.64.2:3306/test","nestor","12345");
            if(obj != null){
                System.out.println("ConexiÃ³n exitosa..");
                Log.e("Conexion:", "Exitosa");
            }
        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("ConexiÃ³n fallida.."+e);
            Log.e("Conexion:", "Fallida " + e);

        }
        return obj ;

    }
    //
    public void desconectar(){
        obj = null;
        if(obj == null){
            System.out.println("ConexiÃ³n finalizada..");
            Log.e("Conexion:", "Finalizada");
        }
    }

}
