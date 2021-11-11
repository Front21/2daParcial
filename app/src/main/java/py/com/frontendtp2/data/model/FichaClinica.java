
package py.com.frontendtp2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FichaClinica {

    @SerializedName("idFichaClinica")
    @Expose
    private Integer idFichaClinica;
    @SerializedName("fechaHora")
    @Expose
    private String fechaHora;
    @SerializedName("motivoConsulta")
    @Expose
    private String motivoConsulta;
    @SerializedName("diagnostico")
    @Expose
    private String diagnostico;
    @SerializedName("observacion")
    @Expose
    private String observacion;
    @SerializedName("idTipoProducto")
    @Expose
    private TipoProducto idTipoProducto;
    @SerializedName("fechaHoraCadena")
    @Expose
    private String fechaHoraCadena;
    @SerializedName("fechaDesdeCadena")
    @Expose
    private Object fechaDesdeCadena;
    @SerializedName("fechaHastaCadena")
    @Expose
    private Object fechaHastaCadena;
    @SerializedName("idEmpleado")
    @Expose
    private Persona idEmpleado;
    @SerializedName("idCliente")
    @Expose
    private Persona idCliente;
    @SerializedName("fechaHoraCadenaFormateada")
    @Expose
    private String fechaHoraCadenaFormateada;

    public Integer getIdFichaClinica() {
        return idFichaClinica;
    }

    public void setIdFichaClinica(Integer idFichaClinica) {
        this.idFichaClinica = idFichaClinica;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    public String getFechaHoraCadena() {
        return fechaHoraCadena;
    }

    public void setFechaHoraCadena(String fechaHoraCadena) {
        this.fechaHoraCadena = fechaHoraCadena;
    }

    public Object getFechaDesdeCadena() {
        return fechaDesdeCadena;
    }

    public void setFechaDesdeCadena(Object fechaDesdeCadena) {
        this.fechaDesdeCadena = fechaDesdeCadena;
    }

    public Object getFechaHastaCadena() {
        return fechaHastaCadena;
    }

    public void setFechaHastaCadena(Object fechaHastaCadena) {
        this.fechaHastaCadena = fechaHastaCadena;
    }

    public Persona getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Persona idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Persona getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Persona idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaHoraCadenaFormateada() {
        return fechaHoraCadenaFormateada;
    }

    public void setFechaHoraCadenaFormateada(String fechaHoraCadenaFormateada) {
        this.fechaHoraCadenaFormateada = fechaHoraCadenaFormateada;
    }

    public TipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }
}