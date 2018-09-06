package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.exceptions.SyncException;

import java.util.Date;

public interface GitHubService {
    Integer getNumberOfIssues(String repo) throws SyncException;
    Date getLastCommitDate(String repo) throws SyncException;
    Integer getNumberOfPullRequests(String repo) throws SyncException;
}
