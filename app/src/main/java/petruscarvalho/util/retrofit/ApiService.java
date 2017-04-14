package petruscarvalho.util.retrofit;

import java.util.List;

import petruscarvalho.model.Beer;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by petruscarvalho on 14/04/17.
 */

public interface ApiService {

    @GET("/v2/beers")
    Call<List<Beer>> getBeerJSON();

}
