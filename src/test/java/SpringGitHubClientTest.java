import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.RepositorySearchResult;
import com.hitit.sample.github.model.User;
import com.hitit.sample.github.service.GitHubClient;
import com.hitit.sample.github.service.spring.SpringGitHubClient;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.List;

public class SpringGitHubClientTest {
    private final String url = "https://api.github.com/";
    private GitHubClient gitHubClient = new SpringGitHubClient();

    @Test
    public void getRepositories() throws IOException {
        int count = 10;
        List<Repository> repositories = gitHubClient.getRepositories("apache", 5, count);
        Assertions.assertNotNull(repositories, "Repositories should not be null!");
        Assertions.assertEquals(10, repositories.size(), "5 repositories should be returned!");
    }

    @Test
    public void searchRepositories() throws IOException {
        String searchQuery = "user:apache";
        String sort = "forks";
        int count = 10;
        RepositorySearchResult searchResult = gitHubClient.searchRepositories(searchQuery, sort, count);
        List<Repository> repositories = searchResult.getItems();
        Assertions.assertNotNull(repositories, "Repositories should not be null!");
        Assertions.assertEquals(10, repositories.size(), "10 repositories should be returned!");
        Assertions.assertEquals("spark", repositories.get(0).getName(), "Most forked repository of apache is spark!");
    }

    @Test
    public void getContributors() throws IOException{
        String owner = "apache";
        String repository = "spark";
        List<User> contributors = gitHubClient.getContributors(owner,repository);
        Assertions.assertNotNull(contributors, "Contributors should not be null!");
        Assertions.assertEquals(30, contributors.size());
    }

    @Test
    public void getUser() throws  IOException{
        String userName = "mateiz";
        User user = gitHubClient.getUser(userName);
        Assertions.assertNotNull(user);
    }
}
