package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.Agenda;
import py.com.frontendtp2.data.model.Categoria;
import py.com.frontendtp2.data.model.Lista;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AgendaService {
    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })


    @GET("persona/{idPersona}/agenda")
    Call<Agenda[]> obtenerAgendas(@Path("idPersona") String idPersona
            , @Query("fecha") String fecha);
}
