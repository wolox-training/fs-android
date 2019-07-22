package ar.com.wolox.android.training.network;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

/**
 * IRest services
 */
public interface IRest {

    @GET("/users")
    Call<JsonElement> getUsersListRequest(@HeaderMap Map<String, String> headers);

    @GET("/users")
    Call<JsonElement> getUserRequest(@HeaderMap Map<String, String> headers, @Query("email") String query);

}
