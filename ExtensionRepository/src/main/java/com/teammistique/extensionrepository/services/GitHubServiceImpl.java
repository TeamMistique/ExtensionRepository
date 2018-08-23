package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.services.base.GitHubService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public Integer getNumberOfIssues(String repo) {
        Integer openIssues = null;
        String owner = GitHubHelpers.getOwner(repo);
        String repoName = GitHubHelpers.getRepo(repo);

        String url = "https://api.github.com/users/"+owner+"/repos";
        String data = GitHubHelpers.getDataFromUrl(url);

        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get("name").equals(repoName)){
                    openIssues = (Integer) jsonObject.get("open_issues_count");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return openIssues;
    }

    @Override
    public Date getLastCommitDate(String repo) {
        Date lastCommitDate = null;
        String url = "https://api.github.com/repos/"+GitHubHelpers.getOwnerAndRepo(repo)+"/commits";
        String data = GitHubHelpers.getDataFromUrl(url);

        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject lastCommit = jsonArray.getJSONObject(0);
            String lastCommitDateString = String.valueOf(lastCommit.getJSONObject("commit").getJSONObject("author").get("date"));
            lastCommitDate = format.parse(lastCommitDateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lastCommitDate;
    }

    @Override
    public Integer getNumberOfPullRequests(String repo) {
        Integer pulls = null;
        String url = "https://api.github.com/repos/"+GitHubHelpers.getOwnerAndRepo(repo)+"/pulls?state=all";
        String data = GitHubHelpers.getDataFromUrl(url);

        try {
            JSONArray jsonArray = new JSONArray(data);
            pulls = jsonArray.length();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(pulls);
        return pulls;
    }

    static class GitHubHelpers{
        static String getOwnerAndRepo(String gitHubUrl){
            String result = null;
            int lengthToSkip = "github.com/".length();
            int beginningOfResult = gitHubUrl.indexOf("github.com/") + lengthToSkip;
            result = gitHubUrl.substring(beginningOfResult);
            return result;
        }

        static String getOwner(String gitHubUrl){
            String result = null;
            int lengthToSkip = "github.com/".length();
            int beginningOfResult = gitHubUrl.indexOf("github.com/") + lengthToSkip;
            int endOfResult = gitHubUrl.indexOf("/", beginningOfResult);
            result = gitHubUrl.substring(beginningOfResult, endOfResult);
            return result;
        }

        static String getRepo(String gitHubUrl){
            String result = null;
            int lengthToSkip = "github.com/".length();
            int beginningOfResult = gitHubUrl.indexOf("github.com/") + lengthToSkip;
            int endOfResult = gitHubUrl.indexOf("/", beginningOfResult);
            result = gitHubUrl.substring(endOfResult+1);
            return result;
        }

        static String getDataFromUrl(String url){
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            request.addHeader("Accept", "application/vnd.github.v3+json");

            StringBuffer result = new StringBuffer();

            try {
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }
    }
}
