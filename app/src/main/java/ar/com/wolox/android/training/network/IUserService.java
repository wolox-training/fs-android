package ar.com.wolox.android.training.network;

import java.util.List;

import ar.com.wolox.android.training.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * IUserService services
 */
public interface IUserService {

    @GET("/users")
    Call<List<User>> getUserRequest(@Query("email") String emailStr,
                                    @Query("password") String passStr);

}
