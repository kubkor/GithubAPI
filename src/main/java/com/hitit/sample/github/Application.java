package com.hitit.sample.github;

import com.hitit.sample.github.export.TextFileExporter;
import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.service.GitHubClient;
import com.hitit.sample.github.service.retrofit.RetrofitGitHubClient;
import com.hitit.sample.github.service.spring.SpringGitHubClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application {

    private static String organizationName = "apache";

    private static int userCount = 10;
    private static int repositoryCount = 5;

    public static void main(String[] args) throws IOException {
        File file = new File("apacheContributors.txt");

//        GitHubClient gitHubClient = new RetrofitGitHubClient();
        GitHubClient gitHubClient = new SpringGitHubClient();

        try (GithubManager manager = new GithubManager(gitHubClient)) {

            List<Repository> repositories = manager.getRepositories(organizationName, repositoryCount);

            for (Repository repository : repositories) {
                repository.setUsers(manager.getUsers(organizationName, repository.getName(), userCount));
            }
            TextFileExporter exporter = new TextFileExporter();
            exporter.export(file, repositories);
        }

    }

}
