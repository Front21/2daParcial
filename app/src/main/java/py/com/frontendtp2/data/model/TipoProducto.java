
package py.com.frontendtp2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoProducto {

    @SerializedName("idTipoProducto")
    @Expose
    private Integer idTipoProducto;
    @SerializedName("idCategoria")
    @Expose
    private Categoria idCategoria;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public TipoProducto() {
    }

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
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
        return String.valueOf(getIdTipoProducto())+" - "+getDescripcion();
    }
}