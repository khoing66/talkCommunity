package study.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import study.community.dto.accessTokenDTO;

import java.io.IOException;

/**
 * Create by Khoing.
 */
@Component
public class githubProvider {
    public String getAcessTokenDTO(accessTokenDTO atdto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(atdto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post( body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String[] split = string.split("&");
                String tokenstr = split[0].split("=")[1];
                return tokenstr;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    public githubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(" https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            githubUser ghUser = JSON.parseObject(string, githubUser.class);
            return ghUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
