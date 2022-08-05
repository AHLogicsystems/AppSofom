package com.logicsystems.appsofom.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

public class conectarBD {

    ArrayList Array_Resultados = new ArrayList<String>();
    ArrayList nicks = new ArrayList<String>();

    String BaseDeDatos="NOMBRE DE LA BASE DE DATOS";
    String ServidorRemoto="jdbc:jtds:sqlserver:Data Source=srvdev02;Initial Catalog=Dev_WAJunJul22;User ID=Cem;Password=D.S2011+";//para usar atravez de internet
    String ServidorLocal="jdbc:jtds:sqlserver://IP_LOCAL:1433/"+BaseDeDatos;//para usar en intranet para mejorar el rendimiento
    String Servidor="";
    String  Usuario="USUARIO SQL";
    String Clave="CLAVE USUARIO SQL";
    String Datos;
    Connection Conexion;
    public String VOnline="";
    EjecutaSelect Obj_DB1;
    EjecutaActualizacion Obj_DB2;

    public conectarBD(String Online){
        VOnline=Online;
        if(Online.equals("true")){
            Servidor=ServidorRemoto;
        }
        else{
            Servidor=ServidorLocal;
        }

    }

    public ResultSet Ejecutar(String sql,String Accion) throws SQLException{
        ResultSet Resultado = null;
        try{
            String nombre="";
            if(Accion.equals("SELECT")){
                Obj_DB1= new EjecutaSelect(sql);
                Obj_DB1.execute();
                try {
                    //Obtiene el resultado de la consulta
                    Resultado=Obj_DB1.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                Obj_DB2= new EjecutaActualizacion(sql);
                Obj_DB2.execute();
                try {
                    //Obtiene el resultado de la consulta
                    Array_Resultados=Obj_DB2.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }catch(Exception e ){
            e.getMessage();
        }
        Obj_DB1=null;
        return Resultado;
    }




/****************************************************************************************************/
    /*                     EJECUTA CONSULTAS SELECT                                        */
    /****************************************************************************************************/

//Tarea Asincronona para sacar los puestos del ranking.
    public class EjecutaSelect extends AsyncTask<Void, Integer, ResultSet> {
        public String Consulta="";

        public ResultSet Obj_Result = null;
        public EjecutaSelect(String sql)    {
            Consulta=sql;
            BaseDeDatos="NOMBRE DE LA BASE DE DATOS";

            if(VOnline.equals("true")){
                Servidor=ServidorRemoto;
            }
            else{
                Servidor=ServidorLocal;
            }
            Usuario="USUARIO SQL";
            Clave="CLAVE USUARIO SQL";
            //  Object rs[] = new Object[2];
        }

        @Override
        protected ResultSet doInBackground(Void... params) {

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                Connection conn = DriverManager.getConnection(Servidor, Usuario, Clave);
                Statement st = conn.createStatement();
                //solo para select retorna el array
                Obj_Result= st.executeQuery(Consulta);


            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            } catch (ClassNotFoundException e) {
                System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
            }
            return Obj_Result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {}
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onPostExecute(ResultSet n) {}
        @Override
        protected void onCancelled() {}
    }



/****************************************************************************************************/
    /*                     EJECUTA CONSULTAS INSERT,UPDATE,DELETE                                      */
    /****************************************************************************************************/
//Tarea Asincronona para sacar los puestos del ranking.
    public class EjecutaActualizacion extends AsyncTask<Void, Integer, ArrayList> {

        public String Consulta="";
        public EjecutaActualizacion(String sql){
            Consulta=sql;
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            ResultSet rs = null;
            int FilasAfectadas = 0;
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                Connection conn = DriverManager.getConnection(Servidor, Usuario, Clave);
                Statement st = conn.createStatement();
                //solo para Update, delete he insert,  devuelve el numero de linea modificadas
                FilasAfectadas=st.executeUpdate(Consulta);
                nicks.add(FilasAfectadas);
//Cerramos la conexión
                conn.close();
                publishProgress(100);
            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            } catch (ClassNotFoundException e) {
                System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
            }
            return nicks;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {}
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onPostExecute(ArrayList n) {}
        @Override
        protected void onCancelled() {}
    }
}