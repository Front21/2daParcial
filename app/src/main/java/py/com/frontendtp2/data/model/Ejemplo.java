package py.com.frontendtp2.data.model;

public class Ejemplo {
    private String nombre;
    private String apellido;
    private String soloUsuariosDelSistema;
    private Categoria idCategoria;
    private Persona idEmpleado;
    private Persona idCliente;
    private TipoProducto idTipoProducto;
    private String fechaDesdeCadena;
    private String fechaHastaCadena;

    public Ejemplo() {
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

    public String getSoloUsuariosDelSistema() {
        return soloUsuariosDelSistema;
    }

    public void setSoloUsuariosDelSistema(String soloUsuariosDelSistema) {
        this.soloUsuariosDelSistema = soloUsuariosDelSistema;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
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

    public TipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
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
}

