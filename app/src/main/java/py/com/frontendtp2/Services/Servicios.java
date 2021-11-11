package py.com.frontendtp2.Services;

import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Persona;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicios {
    public static PersonaService getPersonaService() {
        return getClient("https://equipoyosh.com/stock-nutrinatalia/").create(PersonaService.class);
    }
    public static Retrofit getClient(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static CategoriaService getCategoriaService() {
        return getClient("https://equipoyosh.com/stock-nutrinatalia/").create(CategoriaService.class);
    }

    public static SubCategoriaService getSubCategoriaService() {
        return getClient("https://equipoyosh.com/stock-nutrinatalia/").create(SubCategoriaService.class);
    }

    public static ReservaService getReservaService() {
        return getClient("https://equipoyosh.com/stock-nutrinatalia/").create(ReservaService.class);
    }
    public static AgendaService getAgendaService() {
        return getClient("https://equipoyosh.com/stock-nutrinatalia/").create(AgendaService.class);
    }
    public static Retrofit getReserva(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static FichaClinicaService getFichaClinicaService() {
        return getFichas("https://equipoyosh.com/stock-nutrinatalia/").create(FichaClinicaService.class);
    }
    public static Retrofit getFichas(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
