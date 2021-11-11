package py.com.frontendtp2.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Agenda {

    @SerializedName("fechaCadena")
    @Expose
    private String fechaCadena;
    @SerializedName("horaInicioCadena")
    @Expose
    private String horaInicioCadena;
    @SerializedName("horaFinCadena")
    @Expose
    private String horaFinCadena;
    @SerializedName("idCliente")
    @Expose
    private Persona idCliente;

    public Agenda() {
    }

    public String getFechaCadena() {
        return fechaCadena;
    }

    public void setFechaCadena(String fechaCadena) {
        this.fechaCadena = fechaCadena;
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
}