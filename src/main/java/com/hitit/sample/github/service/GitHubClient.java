package com.hitit.sample.github.service;

import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.RepositorySearchResult;
import com.hitit.sample.github.model.User;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface GitHubClient extends Closeable {
    List<Repository> getRepositories(String organization, int page, int perPage) throws IOException;
    RepositorySearchResult searchRepositories(String searchQuery, String sort, int perPage) throws IOException;
    List<User> getContributors(String owner, String repository) throws IOException;
    User getUser(String user) throws IOException;
}
