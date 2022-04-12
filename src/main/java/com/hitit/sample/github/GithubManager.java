package com.hitit.sample.github;

import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.RepositorySearchResult;
import com.hitit.sample.github.model.User;
import com.hitit.sample.github.service.GitHubClient;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class GithubManager implements Closeable {

    private GitHubClient gitHubClient;

    public GithubManager(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<Repository> getRepositories(String organizationName, int count) throws IOException {
//        List<Repository> repositories = new ArrayList<>();
//        List<Repository> body;
//        int page = 1;
//        do {
//            body = gitHubClient.getRepositories(organizationName, page, 100);
//            if (body != null) {
//                repositories.addAll(body);
//            }
//            page++;
//        }
//        while (body != null && body.size() == 100);
//
//        repositories.sort((o1, o2) -> o2.getForkQuantity() - o1.getForkQuantity());
//
//        return repositories.size() >= count ? repositories.subList(0, count) : repositories;

        RepositorySearchResult searchResult = gitHubClient.searchRepositories("user:" + organizationName, "forks", count);
        return searchResult.getItems();
    }

    public List<User> getUsers(String organizationName, String repositoryName, int count) throws IOException {
        List<User> contributors = gitHubClient.getContributors(organizationName, repositoryName);

        contributors = contributors.size() >= count ? contributors.subList(0, count) : contributors;

        for (User contributor : contributors) {

            updateContributor(contributor);
        }

        return contributors;
    }

    private void updateContributor(User contributor) throws IOException {
        String userName = contributor.getUserName();

        User user = gitHubClient.getUser(userName);

        contributor.setCompany(user.getCompany());
        contributor.setLocation(user.getLocation());
    }


    public void close() throws IOException {
        gitHubClient.close();
    }
}
