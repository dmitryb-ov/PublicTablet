package com.tablet.notification.telegram.util;

import com.tablet.notification.telegram.model.ImageType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class GiphyUtil {
    private static final String URL = "https://api.giphy.com/v1/gifs/random?api_key=";
    private static final String TAG = "&tag=";
    private static final String FIND_PARAMETER = "animal";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final String apiKey = "cNGj9Y9GjfInzIKK2aEkfQFg7wyw97jO";

    private URL gifUrl;

    @Getter
    private ImageType imageType;

    public GiphyUtil() {
        final String FULL_URL = URL + apiKey + TAG + FIND_PARAMETER;
        System.err.println(FULL_URL);
        HttpGet request = new HttpGet(FULL_URL);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            this.gifUrl = getGifUrlFromJson(result);
            this.imageType = ImageType.GIF;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringGifUrl() {
        return this.gifUrl.toString();
    }

    private URL getGifUrlFromJson(String json) throws MalformedURLException {
        JSONObject jsonObject = new JSONObject(json);
        System.err.println(json);
        return new URL(getCleanGifUrl(jsonObject
                .getJSONObject("data")
                .getJSONObject("images")
                .getJSONObject("original")
                .getString("url")).toString());
    }

    private StringBuilder getCleanGifUrl(String url) {
        final String GIF_LINK_REGEX = "media\\/(.+)\\/";
        final String LINK = "https://i.giphy.com/";
        final String GIPHY = "giphy";

        String GIF_LINK = "";
        StringBuilder cleanUrl = new StringBuilder();

        Pattern pattern = Pattern.compile(GIF_LINK_REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            GIF_LINK = matcher.group(0);
        }

        return cleanUrl.append(LINK).append(GIF_LINK).append(GIPHY).append(".").append(convertFormatToGif(url));
    }

    private String convertFormatToGif(String url) {
        final String REGEX = "\\.";
        String[] strings = url.split(REGEX);
        String str = url.replace(strings[strings.length - 1], "gif");
        String[] strings1 = str.split(REGEX);
        return strings1[strings1.length - 1];
    }
}
