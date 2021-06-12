package com.tablet.notification.telegram.util;

import com.tablet.notification.telegram.model.ImageType;
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

@Component
@Slf4j
public class ImageUtil {
    private static final String CONST_IMAGE_URL = "https://aws.random.cat/meow";
    private static final String KEY = "file";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    private URL imageURL;
    private ImageType imageType;

    public ImageUtil() {
        HttpGet request = new HttpGet(CONST_IMAGE_URL);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            this.imageURL = parseJson(result);

            if (this.imageURL.toString().contains(".gif")) {
                this.imageType = ImageType.GIF;
            } else {
                this.imageType = ImageType.OTHER;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getImageURL() {
        return this.imageURL.toString();
    }

    public ImageType getImageType() {
        return this.imageType;
    }

//    public static byte[] convertImageByte(URL url) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        InputStream inputStream = null;
//        try {
//            inputStream = new BufferedInputStream(url.openStream());
//            byte[] bytes = new byte[4096];
//            int n;
//            while ((n = inputStream.read(bytes)) > 0) {
//                baos.write(bytes, 0, n);
//            }
//            return baos.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//        }
//        return null;
//    }

    private URL parseJson(String result) throws MalformedURLException {
        JSONObject jsonObject = new JSONObject(result);
        return new URL(jsonObject.getString(KEY));
    }
}
