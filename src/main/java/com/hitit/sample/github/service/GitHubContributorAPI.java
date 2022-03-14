package com.hitit.sample.github.service;

import com.hitit.sample.github.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface GitHubContributorAPI {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<User>> getContributors(@Path("owner") String owner, @Path("repo") String repository);
}


