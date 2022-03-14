package com.hitit.sample.github;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.SearchResult;
import com.hitit.sample.github.model.User;
import com.hitit.sample.github.service.GitHubContributorAPI;
import com.hitit.sample.github.service.GitHubRepositoryAPI;
import com.hitit.sample.github.service.GitHubUserAPI;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class GithubManager implements AutoCloseable {

    private OkHttpClient client;
    private Retrofit retrofit;
    private String url = "https://api.github.com/";

    public GithubManager() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        client = new OkHttpClient().newBuilder().build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();
    }

    public List<Repository> getRepositories(String organizationName, int count) throws IOException {
        GitHubRepositoryAPI repositoryService = retrofit.create(GitHubRepositoryAPI.class);

//        List<Repository> repositories = new ArrayList<>();
//        List<Repository> body;
//        int page = 1;
//        do {
//            Call<List<Repository>> listCall = repositoryService.getRepositories(organizationName, page, 100);
//            Response<List<Repository>> response = listCall.execute();
//
//            body = response.body();
//            if (body != null) {
//                repositories.addAll(body);
//            }
//            page++;
//        }
//        while (body != null && body.size() == 100);

//        repositories.sort((o1, o2) -> o2.getForkQuantity() - o1.getForkQuantity());

//        return repositories.size() >= count ? repositories.subList(0, count) : repositories;

        Call<SearchResult<Repository>> searchCall = repositoryService.searchRepositories("user:" + organizationName, "forks", count);
        SearchResult<Repository> body = searchCall.execute().body();

        return body.getItems();
    }

    public List<User> getUsers(String organizationName, String repositoryName, int count) throws IOException {
        GitHubContributorAPI contributorService = retrofit.create(GitHubContributorAPI.class);

        Call<List<User>> contributorListCall = contributorService.getContributors(organizationName, repositoryName);

        Response<List<User>> contributorResponse = contributorListCall.execute();
        List<User> contributors = contributorResponse.body();

        contributors = contributors.size() >= count ? contributors.subList(0, count) : contributors;

        for (User contributor : contributors) {

            updateContributor(contributor);
        }

        return contributors;
    }

    private void updateContributor(User contributor) throws IOException {
        GitHubUserAPI userService = retrofit.create(GitHubUserAPI.class);

        String userName = contributor.getUserName();

        Call<User> userCall = userService.getUser(userName);
        Response<User> userResponse = userCall.execute();
        User user = userResponse.body();

        contributor.setCompany(user.getCompany());
        contributor.setLocation(user.getLocation());
    }


    public void close() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
}
