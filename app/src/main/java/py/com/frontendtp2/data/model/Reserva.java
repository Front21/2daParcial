package py.com.frontendtp2.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import py.com.frontendtp2.data.model.Persona;

public class Reserva {

    @SerializedName("idReserva")
    @Expose
    private Integer idReserva;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;

    @SerializedName("horaFin")
    @Expose
    private String horaFin;

    @SerializedName("fechaHoraCreacion")
    @Expose
    private String fechaHoraCreacion;

    @SerializedName("flagEstado")
    @Expose
    private String flagEstado;

    @SerializedName("flagAsistio")
    @Expose
    private String flagAsistio;

    @SerializedName("observacion")
    @Expose
    private String observacion;

    @SerializedName("idFichaClinica")
    @Expose
    private Integer idFichaClinica;

    @SerializedName("fechaDesdeCadena")
    @Expose
    private String fechaDesdeCadena;

    @SerializedName("fechaHastaCadena")
    @Expose
    private String fechaHastaCadena;

    @SerializedName("horaInicioCadena")
    @Expose
    private String horaInicioCadena;

    @SerializedName("horaFinCadena")
    @Expose
    private String horaFinCadena;

    @SerializedName("idCliente")
    @Expose
    private Persona idCliente;

    @SerializedName("idEmpleado")
    @Expose
    private Persona idEmpleado;

    @SerializedName("fechaCadena")
    @Expose
    private String fechaCadena;

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(String fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public String getFlagAsistio() {
        return flagAsistio;
    }

    public void setFlagAsistio(String flagAsistio) {
        this.flagAsistio = flagAsistio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getIdFichaClinica() {
        return idFichaClinica;
    }

    public void setIdFichaClinica(Integer idFichaClinica) {
        this.idFichaClinica = idFichaClinica;
    }

    public String getFechaDesdeCadena() {
        return fechaDesdeCadena;
    }

    public void setFechaDesdeCadena(String fechaDesdeCadena) {
        this.fechaDesdeCadena = fechaDesdeCadena;
    }

    public String getFechaHastaCadena() {
        return fechaHastaCadena;
    }

    public void setFechaHastaCadena(String fechaHastaCadena) {
        this.fechaHastaCadena = fechaHastaCadena;
    }

    public String getHoraInicioCadena() {
        return horaInicioCadena;
    }

    public void setHoraInicioCadena(String horaInicioCadena) {
        this.horaInicioCadena = horaInicioCadena;
    }

    public String getHoraFinCadena() {
        return horaFinCadena;
    }

    public void setHoraFinCadena(String horaFinCadena) {
        this.horaFinCadena = horaFinCadena;
    }

    public Persona getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Persona idCliente) {
        this.idCliente = idCliente;
    }

    public Persona getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Persona idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFechaCadena() {
        return fechaCadena;
    }

    public void setFechaCadena(String fechaCadena) {
        this.fechaCadena = fechaCadena;
    }
}