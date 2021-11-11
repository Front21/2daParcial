package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.TipoProducto;
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

public interface SubCategoriaService {
    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })


    @GET("tipoProducto")
    Call<Lista<TipoProducto>> obtenerTipoProductos(@Query("orderBy") String orderBy, @Query("orderDir") String orderDir,
                                             @Query("like") String like, @Query("ejemplo") String ejemplo);
    @POST("tipoProducto")
    Call<TipoProducto> agregarTipoProducto(@Body TipoProducto TipoProducto);

    @PUT("tipoProducto")
    Call<TipoProducto> actualizarTipoProducto(@Body TipoProducto TipoProducto);

    @DELETE("tipoProducto/{id}")
    Call<Persona> borrarTipoProducto(@Path("id") Integer idTipoProducto);

}