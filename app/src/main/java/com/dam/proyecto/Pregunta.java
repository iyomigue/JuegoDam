package com.dam.proyecto;

public class Pregunta {
    private int id;
    private String pregunta;
    private String a;
    private String b;
    private String c;
    private String d;
    private String respuesta;

    public Pregunta (int id, String pregunta, String a, String b, String c, String d, String respuesta){
        this.id = id;
        this.pregunta = pregunta;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.respuesta = respuesta;
    }
    public int getId(){
        return id;
    }
    public String getPregunta(){
        return pregunta;
    }
    public String getA(){
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getRespuesta() {
        return respuesta;
    }
}
