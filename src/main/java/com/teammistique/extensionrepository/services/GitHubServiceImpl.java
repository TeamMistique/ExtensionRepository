package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.exceptions.SyncException;
import com.teammistique.extensionrepository.services.base.GitHubService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import javax.validation.constraints.Null;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public Integer getNumberOfIssues(String repo) throws SyncException {
        Integer openIssues = null;
        String ownerAndRepo = GitHubHelpers.getOwnerAndRepo(repo);

        String url = "https://api.github.com/repos/" + ownerAndRepo;
        String data = GitHubHelpers.getDataFromUrl(url);

        try {
            JSONObject repoInfo = new JSONObject(data);
            openIssues = (Integer) repoInfo.get("open_issues_count");
        }  catch (JSONException e) {
            e.printStackTrace();
            throw new SyncException("Could not get number of issues.");
        }

        return openIssues;
    }

    @Override
    public Date getLastCommitDate(String repo) throws SyncException {
        Date lastCommitDate = null;
        String url = "https://api.github.com/repos/" + GitHubHelpers.getOwnerAndRepo(repo) + "/commits";
        String data = GitHubHelpers.getDataFromUrl(url);

        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject lastCommit = jsonArray.getJSONObject(0);
            String lastCommitDateString = String.valueOf(lastCommit.getJSONObject("commit").getJSONObject("author").get("date"));
            lastCommitDate = format.parse(lastCommitDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new SyncException("Could not get last commit date.");
        }

        return lastCommitDate;
    }

    @Override
    public Integer getNumberOfPullRequests(String repo) throws SyncException {
        Integer pulls = null;
        String url = "https://api.github.com/repos/" + GitHubHelpers.getOwnerAndRepo(repo) + "/pulls?state=all";
        String data = GitHubHelpers.getDataFromUrl(url);

        try {
            JSONArray jsonArray = new JSONArray(data);
            pulls = jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new SyncException("Could not get number of pull requests.");
        }

        System.out.println(pulls);
        return pulls;
    }

    static class GitHubHelpers {
        static String getOwnerAndRepo(String gitHubUrl) throws SyncException {
            try {
                String result = null;
                int lengthToSkip = "github.com/".length();
                int gh = gitHubUrl.indexOf("github.com/");
                if(gh==-1) throw new NullPointerException();
                int beginningOfResult = gh + lengthToSkip;
                result = gitHubUrl.substring(beginningOfResult);
                return result;
            } catch (NullPointerException e) {
                e.printStackTrace();
                throw new SyncException("Invalid git hub link.");
            }
        }

        static String getDataFromUrl(String url) throws SyncException {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            request.addHeader("Accept", "application/vnd.github.v3+json");

            StringBuilder result = new StringBuilder();

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
                throw new SyncException("Could not execute git hub request.");
            }

            return result.toString();
        }
    }
}
