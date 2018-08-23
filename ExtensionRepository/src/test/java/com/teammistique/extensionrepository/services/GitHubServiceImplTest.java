package com.teammistique.extensionrepository.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class GitHubServiceImplTest {
    private GitHubServiceImpl gitHubService;

    @Before
    public void setUp(){
        gitHubService = new GitHubServiceImpl();
    }

    @Test
    public void getOwnerAndRepo_shouldReturnACorrectString() {
        String result = GitHubServiceImpl.GitHubHelpers.getOwnerAndRepo("https://github.com/biaedwards/Java-Alpha-Module-1");
        Assert.assertEquals("biaedwards/Java-Alpha-Module-1", result);
    }

    @Test
    public void getOwner_shouldReturnACorrectString() {
        String result = GitHubServiceImpl.GitHubHelpers.getOwner("https://github.com/biaedwards/Java-Alpha-Module-1");
        Assert.assertEquals("biaedwards", result);
    }

    @Test
    public void getRepo_shouldReturnNameOfRepo() {
        String result = GitHubServiceImpl.GitHubHelpers.getRepo("https://github.com/biaedwards/Java-Alpha-Module-1");
        Assert.assertEquals("Java-Alpha-Module-1", result);
    }

    @Test
    public void getNumberOfIssues_shouldNotReturnNull() {
        Integer openIssues = gitHubService.getNumberOfIssues("https://github.com/ValveSoftware/Proton");
        Assert.assertNotNull(openIssues);
    }

    @Test
    public void getNumberOfPullRequests_shouldNotReturnNull() {
        Integer openIssues = gitHubService.getNumberOfPullRequests("https://github.com/ValveSoftware/Proton");
        Assert.assertNotNull(openIssues);
    }

    @Test
    public void getLastCommitDate_shouldNotReturnNull() {
        Date lastCommitDate = gitHubService.getLastCommitDate("https://github.com/ValveSoftware/Proton");
        Assert.assertNotNull(lastCommitDate);
    }
}