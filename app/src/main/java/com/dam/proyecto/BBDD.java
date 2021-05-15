package com.dam.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BBDD extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS      = 1;
    private static final String NOMBRE_BASEDATOS    = "ProyectoDam.db";
    private Context contexto;

    private static final String TABLA_PREGUNTAS     = "CREATE TABLE preguntas " +
            "( id INTEGER PRIMARY KEY, pregunta TEXT, a TEXT, b TEXT, c TEXT, d TEXT, respuesta TEXT)";

    private static final String TABLA_CALIFICACION  = "CREATE TABLE calificacion " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, participante TEXT, puntuacion INTEGER )";

    private static final String BORRAR_PREGUNTAS    = "DROP TABLE IF EXISTS preguntas";
    private static final String BORRAR_CALIFICACION = "DROP TABLE IF EXISTS calificacion";


    public BBDD(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
        contexto = context;
    }
//es
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BORRAR_PREGUNTAS);
        db.execSQL(BORRAR_CALIFICACION);
        //creamoas las tablas donde se encuentran las preguntas y las calificaciones de usuarios
        db.execSQL(TABLA_PREGUNTAS);
        db.execSQL(TABLA_CALIFICACION);
        //Añadimos a la base de datos las preguntas almacenadas en el xml
        String xmlPreguntas     = contexto.getString(R.string.preguntas);
        String[] listaPreguntas = xmlPreguntas.split("º");
        int i = 1;
        while(i < listaPreguntas.length) {
            String[] parts          = listaPreguntas[i].split("-");
            int Id                  = Integer.parseInt(parts[0].trim());
            ContentValues valores   = new ContentValues();
            valores.put("id",           Id);
            valores.put("pregunta",     parts[1]);
            valores.put("a",            parts[2]);
            valores.put("b",            parts[3]);
            valores.put("c",            parts[4]);
            valores.put("d",            parts[5]);
            valores.put("respuesta",    parts[6]);
            db.insert("preguntas", null, valores);
            i++;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS" + TABLA_PREGUNTAS);
        //onCreate(db);
    }

    /*private void insertarPregunta(String id, String pregunta, String a, String b, String c, String d, String respuesta){
        SQLiteDatabase db = getWritableDatabase();
        int Id = Integer.parseInt(id.trim());
        if (db != null) {
            ContentValues valores = new ContentValues();
            if(Id!=0)
                valores.put("id",       Id);
            valores.put("pregunta",     pregunta);
            valores.put("a",            a);
            valores.put("b",            b);
            valores.put("c",            c);
            valores.put("d",            d);
            valores.put("respuesta",    respuesta);
            db.insert("preguntas", null, valores);
        }
        db.close();
    } */
    public Pregunta obtenerPregunta(int id){
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"id", "pregunta", "a", "b", "c", "d", "respuesta"};
        Cursor c = db.query("preguntas", valores_recuperar, "id=" + id, null, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
        }
        Pregunta pregunta = new Pregunta(c.getInt(0), c.getString(1), c.getString(2),
                c.getString(3), c.getString(4), c.getString(5), c.getString(6));
        db.close();
        c.close();
        return pregunta;
    }
    public void cargaDatos(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(BORRAR_PREGUNTAS);
        db.execSQL(TABLA_PREGUNTAS);
        String xmlPreguntas     = contexto.getString(R.string.preguntas);
        String[] listaPreguntas = xmlPreguntas.split("º");
        int i = 1;
        while(i < listaPreguntas.length) {
            String[] parts          = listaPreguntas[i].split("-");
            int Id                  = Integer.parseInt(parts[0].trim());
            ContentValues valores   = new ContentValues();
            valores.put("id",           Id);
            valores.put("pregunta",     parts[1]);
            valores.put("a",            parts[2]);
            valores.put("b",            parts[3]);
            valores.put("c",            parts[4]);
            valores.put("d",            parts[5]);
            valores.put("respuesta",    parts[6]);
            db.insert("preguntas", null, valores);
            i++;
        }
    }
    public boolean insertarCalificacion(Resultado resultado){
        long salida=0;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();

            valores.put("participante", resultado.getNombre());
            valores.put("puntuacion", resultado.getPuntuacion());
            salida=db.insert("calificacion", null, valores);
        }
        db.close();
        return(salida>0);
    }
    public ArrayList<Resultado> obtenerResultados() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Resultado> lista_resultados = new ArrayList<Resultado>();
        String[] valores_recuperar = {"id", "participante", "puntuacion"};
        Cursor c = db.query("calificacion", valores_recuperar, null, null, null, null, null, null);
        c.moveToFirst();
        do {
            Resultado calificacion = new Resultado(c.getString(1), c.getInt(2));
            lista_resultados.add(calificacion);
        } while (c.moveToNext());
        db.close();
        c.close();
        return lista_resultados;
    }

    public int getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "preguntas");
        db.close();
        return ((int) count);
    }
}
