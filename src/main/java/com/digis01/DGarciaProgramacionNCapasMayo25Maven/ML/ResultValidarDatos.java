
package com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML;


public class ResultValidarDatos {
    private int fila;
    private String texto;
    private String Descripcion;

    public ResultValidarDatos(int fila, String texto, String Descripcion) {
        this.fila = fila;
        this.texto = texto;
        this.Descripcion = Descripcion;
    }
   
    

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
    
}
