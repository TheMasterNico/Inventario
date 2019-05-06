package com.unal.inventario.DatosSQL;

public class Usuario {
    private int idUsuario;
    private String Nombre;
    private String Pass;
    private int Admin;

    public Usuario(int idUsuario, String Nombre, String Pass, int Admin)
    {
        this.idUsuario = idUsuario;
        this.Admin = Admin;
        this.Nombre = Nombre;
        this.Pass = Pass;
    }

    public int getIdUsuario()
    {
        return idUsuario;
    }

    public int getAdmin() {
        return Admin;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getPass() {
        return Pass;
    }
}
