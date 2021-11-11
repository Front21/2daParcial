
package py.com.frontendtp2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categoria {

    @SerializedName("idCategoria")
    @Expose
    private Integer idCategoria;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public Categoria() {
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return String.valueOf(getIdCategoria())+" - "+getDescripcion();
    }

}