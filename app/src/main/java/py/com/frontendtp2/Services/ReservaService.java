package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservaService {
    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @GET("reserva")
    Call<Lista<Reserva>> obtenerReservas(@Query("orderBy") String orderBy, @Query("orderDir") String orderDir,
                                         @Query("like") String like, @Query("ejemplo") String ejemplo);

    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @POST("reserva")
    Call<Reserva> agregarReserva(@Body Reserva reserva);

    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @PUT("reserva")
    Call<Reserva> actualizarReserva(@Body Reserva reserva);

    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @DELETE("reserva/{id}")
    Call<Reserva> borrarReserva(@Path("id") Integer idPersona);

}
