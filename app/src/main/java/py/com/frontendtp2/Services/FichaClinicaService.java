package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Persona;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FichaClinicaService {
    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @GET("fichaClinica")
    Call<Lista<FichaClinica>> obtenerFichaClinica(@Query("orderBy") String orderBy, @Query("orderDir") String orderDir,
                                                  @Query("like") String like, @Query("ejemplo") String ejemplo);;


    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @POST("fichaClinica")
    Call<FichaClinica> agregarFichaClinica(@Body FichaClinica fichaClinica);


    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @PUT("fichaClinica")
    Call<FichaClinica> actualizarFichaClinica(@Body FichaClinica fichaClinica);


    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })
    @DELETE("fichaClinica/{id}")
    Call<FichaClinica> borrarFichaClinica(@Path("id") Integer idFicha);
}
