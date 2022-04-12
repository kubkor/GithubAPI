package com.hitit.sample.github.service.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.RepositorySearchResult;
import com.hitit.sample.github.model.User;
import com.hitit.sample.github.service.GitHubClient;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class RetrofitGitHubClient implements GitHubClient {

    private final OkHttpClient client;
    private final Retrofit retrofit;
    private final String url = "https://api.github.com/";
    private final GitHubRepositoryAPI repositoryService;
    private final GitHubContributorAPI contributorService;
    private final GitHubUserAPI userService;

    public RetrofitGitHubClient() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        HeaderRequestInterceptor headerRequestInterceptor = new HeaderRequestInterceptor("Authorization", "Basic a3Via29yOmdocF9BbVhUdVFDSENtWWJqcXJDNXBtZ01LQjJFbUJ1dHcybTY4MTk=");
        ResponseValidatorInterceptor responseValidatorInterceptor = new ResponseValidatorInterceptor();

        client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(headerRequestInterceptor)
                .addInterceptor(responseValidatorInterceptor)
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        repositoryService = retrofit.create(GitHubRepositoryAPI.class);
        contributorService = retrofit.create(GitHubContributorAPI.class);
        userService = retrofit.create(GitHubUserAPI.class);

    }

    @Override
    public List<Repository> getRepositories(String organization, int page, int perPage) throws IOException {
        Call<List<Repository>> listCall = repositoryService.getRepositories(organization, page, perPage);
        Response<List<Repository>> response = listCall.execute();
        return response.body();
    }

    @Override
    public RepositorySearchResult searchRepositories(String searchQuery, String sort, int perPage) throws IOException {
        Call<RepositorySearchResult> searchCall = repositoryService.searchRepositories(searchQuery, sort, perPage);
        return searchCall.execute().body();
    }

    @Override
    public List<User> getContributors(String owner, String repository) throws IOException {
        Call<List<User>> contributorListCall = contributorService.getContributors(owner, repository);
        Response<List<User>> contributorResponse = contributorListCall.execute();
        return contributorResponse.body();
    }

    @Override
    public User getUser(String user) throws IOException {
        Call<User> userCall = userService.getUser(user);
        Response<User> userResponse = userCall.execute();
        return userResponse.body();
    }

    @Override
    public void close() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
}
