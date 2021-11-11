
package py.com.frontendtp2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdTipoProducto {

    @SerializedName("idTipoProducto")
    @Expose
    private Integer idTipoProducto;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("flagVisible")
    @Expose
    private String flagVisible;
    @SerializedName("idCategoria")
    @Expose
    private IdCategoria idCategoria;
    @SerializedName("posicion")
    @Expose
    private Integer posicion;

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFlagVisible() {
        return flagVisible;
    }

    public void setFlagVisible(String flagVisible) {
        this.flagVisible = flagVisible;
    }

    public IdCategoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(IdCategoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

}
