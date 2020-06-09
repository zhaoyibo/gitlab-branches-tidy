package com.windmt.tool;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author yibo
 * @since 2020-06-09
 */
public class DeleteBranch {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("请输入 projectId 和 分支列表 !");
            System.exit(1);
        }
        String projectId = args[0];
        String[] branches = args[1].split(",");

        String token = System.getenv("GITFLOW_ACCESS_TOKEN");
        if (token == null || token.length() == 0) {
            System.err.println("获取不到 Gitlab Access Token，请检查 gitflow 是否正确安装 !");
            System.exit(1);
        }

        String url = "http://git.wb-intra.com/api/v4/projects/%s/repository/branches/";

        for (String branch : branches) {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpDelete httpDelete = new HttpDelete(String.format(url, projectId) + URLEncoder.encode(branch, StandardCharsets.UTF_8.name()));
                httpDelete.setHeader("PRIVATE-TOKEN", token);
                ResponseHandler<String> responseHandler = response -> {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) { // 404
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        System.err.println("branch: " + branch + " Unexpected response status: " + status);
                        return null;
                    }
                };
                String responseBody = httpclient.execute(httpDelete, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
