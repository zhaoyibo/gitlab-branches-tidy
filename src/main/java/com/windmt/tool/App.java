package com.windmt.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.windmt.tool.domain.Branch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yibo
 * @since 2020-06-09
 */
public class App {

    private static final String URL = "http://git.wb-intra.com/api/v4/projects/%s/repository/branches?per_page=100";

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("请输入 projectId !");
            System.exit(1);
        }
        String projectId = args[0];

        String token = System.getenv("GITFLOW_ACCESS_TOKEN");
        if (token == null || token.length() == 0) {
            System.err.println("获取不到 Gitlab Access Token，请检查 gitflow 是否正确安装 !");
            System.exit(1);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, List<Branch>> map = new HashMap<>();

        HttpClient client = new DefaultHttpClient();

        int page = 1;

        while (true) {
            HttpGet get = new HttpGet(String.format(URL, projectId) + "&page=" + page);
            get.setHeader("PRIVATE-TOKEN", token);
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                String message = EntityUtils.toString(resEntity, "utf-8");
                List<Branch> branches = mapper.readValue(message, new TypeReference<List<Branch>>() {
                });

                if (branches.isEmpty()) {
                    break;
                }

                for (Branch branch : branches) {
                    map.computeIfAbsent(branch.getCommit().getAuthorName(), k -> new ArrayList<>())
                            .add(branch);
                }

                page++;

            } else {
                System.out.println("请求失败");
            }
        }

        int c = 0;
        System.out.println("======================================================");
        System.out.println("分支创建者\t创建者邮箱\t分支名\t分支创建日期\t最后一次提交日期");
        for (Map.Entry<String, List<Branch>> entry : map.entrySet()) {
            for (Branch branch : entry.getValue()) {
                System.out.println(entry.getKey() + "\t" + branch.getCommit().getAuthorEmail() + "\t" + branch.getName() + "\t" + branch.getCommit().createdAt() + "\t" + branch.getCommit().authoredDate());
                c++;
            }
        }
        System.out.println("======================================================");
        System.out.println();
        System.out.println("分支总数: " + c);


//
//        URL resource = App.class.getClassLoader().getResource("ret.json");
//        assert resource != null;
//        String json = new String(Files.readAllBytes(Paths.get(resource.toURI())));
//

//        List<Branch> branches = mapper.readValue(json, new TypeReference<List<Branch>>() {
//        });
//

//
//

    }

}
