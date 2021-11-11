package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonaService {
    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })

    @GET("persona")
    Call<Lista<Persona>> obtenerPersonas(@Query("orderBy") String orderBy, @Query("orderDir") String orderDir,
                                         @Query("like") String like, @Query("ejemplo") String ejemplo);
    @GET("persona/{idPersona}/agenda")
    Call<Lista<Persona>> obtenerAgendaPersona(@Path ("idPersona") String idPersona, @Query("fecha") String fecha, @Query("disponible") String disponible);

    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @POST("persona")
    Call<Persona> agregarPersona(@Body Persona persona);

    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @PUT("persona")
    Call<Persona> actualizarPersona(@Body Persona persona);


    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @DELETE("persona/{id}")
    Call<Persona> borrarPersona(@Path("id") Integer idPersona);

}