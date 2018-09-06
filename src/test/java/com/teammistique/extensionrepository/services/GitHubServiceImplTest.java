package com.teammistique.extensionrepository.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GitHubServiceImplTest {
    private GitHubServiceImpl gitHubService;
    private static final String repo = "https://github.com/biaedwards/Java-Alpha-Module-1";

    @Before
    public void setUp(){
        gitHubService = new GitHubServiceImpl();
    }

    @Test
    public void getOwnerAndRepo_shouldReturnACorrectString() {
        String result = GitHubServiceImpl.GitHubHelpers.getOwnerAndRepo(repo);
        Assert.assertEquals("biaedwards/Java-Alpha-Module-1", result);
    }
}