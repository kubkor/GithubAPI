package com.hitit.sample.github.service.retrofit;

import com.hitit.sample.github.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserAPI {
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String user);
}


