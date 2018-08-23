package com.teammistique.extensionrepository.services.base;

import java.util.Date;

public interface GitHubService {
    Integer getNumberOfIssues(String repo);
    Date getLastCommitDate(String repo);
    Integer getNumberOfPullRequests(String repo);
}
