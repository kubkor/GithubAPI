package com.hitit.sample.github.service.spring;

import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.RepositorySearchResult;
import com.hitit.sample.github.model.User;
import com.hitit.sample.github.service.GitHubClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SpringGitHubClient implements GitHubClient {

    private final String baseUrl = "https://api.github.com/";
    RestTemplate restTemplate;

    public SpringGitHubClient() {
        restTemplate = new RestTemplate();
        HeaderRequestInterceptor interceptor = new HeaderRequestInterceptor("Authorization", "Basic a3Via29yOmdocF9BbVhUdVFDSENtWWJqcXJDNXBtZ01LQjJFbUJ1dHcybTY4MTk=");
        restTemplate.getInterceptors().add(interceptor);
    }

    @Override
    public List<Repository> getRepositories(String organization, int page, int perPage) {
        List<Repository> repositoriesList = null;
        ResponseEntity<Repository[]> response =
                restTemplate.getForEntity(
                        baseUrl
                                + "/orgs/"
                                + organization
                                + "/repos?per_page="
                                + perPage
                                + "&page="
                                + page,
                        Repository[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public RepositorySearchResult searchRepositories(String searchQuery, String sort, int perPage) {
        String url =
                String.format(
                        "%s/search/repositories?q=%s&sort=%s&per_page=%d",
                        baseUrl, searchQuery, sort, perPage);
        ResponseEntity<RepositorySearchResult> response =
                restTemplate.getForEntity(url, RepositorySearchResult.class);
        return response.getBody();
    }

    @Override
    public List<User> getContributors(String owner, String repository) {
        String url = String.format("%s/repos/%s/%s/contributors", baseUrl, owner, repository);
        ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public User getUser(String user) {
        String url = String.format("%s/users/%s", baseUrl, user);
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        return response.getBody();
    }

    @Override
    public void close() {}
}
