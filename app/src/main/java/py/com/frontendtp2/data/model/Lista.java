package py.com.frontendtp2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lista<T> {
    @SerializedName("lista")
    @Expose
    private T[] lista;
    @SerializedName("totalDatos")
    @Expose
    private Long totalDatos;
    public Lista(){

    }

    public T[] getLista() {
        return lista;
    }

    public void setLista(T[] lista) {
        this.lista = lista;
    }

    public Long getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(Long totalDatos) {
        this.totalDatos = totalDatos;
    }
}
