package py.com.frontendtp2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Persona {
    @SerializedName("idPersona")
    @Expose
    private Integer idPersona;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("seguroMedico")
    @Expose
    private String seguroMedico;
    @SerializedName("seguroMedicoNumero")
    @Expose
    private String seguroMedicoNumero;
    @SerializedName("ruc")
    @Expose
    private String ruc;
    @SerializedName("cedula")
    @Expose
    private String cedula;
    @SerializedName("tipoPersona")
    @Expose
    private String tipoPersona;
    @SerializedName("usuarioLogin")
    @Expose
    private String usuarioLogin;
    /**@SerializedName("idLocalDefecto")
    @Expose
    private Local idLocalDefecto;**/
    @SerializedName("flagVendedor")
    @Expose
    private String flagVendedor;
    @SerializedName("observacion")
    @Expose
    private String observacion;
    @SerializedName("tipoCliente")
    @Expose
    private String tipoCliente;
    @SerializedName("fechaHoraAprobContrato")
    @Expose
    private String fechaHoraAprobContrato;
    @SerializedName("soloUsuariosDelSistema")
    @Expose
    private Boolean soloUsuariosDelSistema;
    @SerializedName("nombreCompleto")
    @Expose
    private String nombreCompleto;
    @SerializedName("limiteCredito")
    @Expose
    private Double limiteCredito;
    @SerializedName("fechaNacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("soloProximosCumpleanhos")
    @Expose
    private Boolean soloProximosCumpleanhos;
    @SerializedName("todosLosCampos")
    @Expose
    private Boolean todosLosCampos;

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSeguroMedico() {
        return seguroMedico;
    }

    public void setSeguroMedico(String seguroMedico) {
        this.seguroMedico = seguroMedico;
    }

    public String getSeguroMedicoNumero() {
        return seguroMedicoNumero;
    }

    public void setSeguroMedicoNumero(String seguroMedicoNumero) {
        this.seguroMedicoNumero = seguroMedicoNumero;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getFlagVendedor() {
        return flagVendedor;
    }

    public void setFlagVendedor(String flagVendedor) {
        this.flagVendedor = flagVendedor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getFechaHoraAprobContrato() {
        return fechaHoraAprobContrato;
    }

    public void setFechaHoraAprobContrato(String fechaHoraAprobContrato) {
        this.fechaHoraAprobContrato = fechaHoraAprobContrato;
    }

    public Boolean getSoloUsuariosDelSistema() {
        return soloUsuariosDelSistema;
    }

    public void setSoloUsuariosDelSistema(Boolean soloUsuariosDelSistema) {
        this.soloUsuariosDelSistema = soloUsuariosDelSistema;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getSoloProximosCumpleanhos() {
        return soloProximosCumpleanhos;
    }

    public void setSoloProximosCumpleanhos(Boolean soloProximosCumpleanhos) {
        this.soloProximosCumpleanhos = soloProximosCumpleanhos;
    }

    public Boolean getTodosLosCampos() {
        return todosLosCampos;
    }

    public void setTodosLosCampos(Boolean todosLosCampos) {
        this.todosLosCampos = todosLosCampos;
    }


}
