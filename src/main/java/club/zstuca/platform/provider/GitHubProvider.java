package club.zstuca.platform.provider;

import club.zstuca.platform.dto.AccessTokenDTO;
import club.zstuca.platform.dto.GitHubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    private static final MediaType MediaType_JSON
            = MediaType.get("application/json; charset=utf-8");
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), MediaType_JSON);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String resstring = response.body().string();
            String token =resstring.split("&")[0]
                    .split("=")[1];
            return token;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser getUser(String AccessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+AccessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(res, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
