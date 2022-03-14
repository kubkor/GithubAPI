package com.hitit.sample.github.service;

import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.SearchResult;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GitHubRepositoryAPI {
    @GET("/orgs/{org}/repos")
    Call<List<Repository>> getRepositories(@Path("org") String organization, @Query("page") int page, @Query("per_page") int perPage);

    @GET("/search/repositories")
    Call<SearchResult<Repository>> searchRepositories(@Query("q") String searchQuery, @Query("sort") String sort, @Query("per_page") int perPage);
}

