package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.Categoria;
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

public interface CategoriaService {
    @Headers({
            "Content-Type: application/json",
            "usuario: usuario2"
    })


    @GET("categoria")
    Call<Lista<Categoria>> obtenerCategorias(@Query("orderBy") String orderBy, @Query("orderDir") String orderDir,
                                         @Query("like") String like, @Query("ejemplo") String ejemplo);
    @POST("categoria")
    Call<Categoria> agregarCategoria(@Body Categoria categoria);

    @PUT("categoria")
    Call<Categoria> actualizarCategoria(@Body Categoria categoria);

    @DELETE("categoria/{id}")
    Call<Persona> borrarCategoria(@Path("id") Integer idCategoria);

}