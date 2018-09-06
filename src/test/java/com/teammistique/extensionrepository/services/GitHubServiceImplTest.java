package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.exceptions.SyncException;
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
    public void getOwnerAndRepo_shouldReturnACorrectString_whenGivenCorrectRepo() throws SyncException {
        String result = GitHubServiceImpl.GitHubHelpers.getOwnerAndRepo(repo);
        Assert.assertEquals("biaedwards/Java-Alpha-Module-1", result);
    }

    @Test(expected = SyncException.class)
    public void getOwnerAndRepo_shouldThrowSyncException_whenGivenWrongRepo() throws SyncException {
        String result = GitHubServiceImpl.GitHubHelpers.getOwnerAndRepo("hajkfhakshdj.git");
    }
}