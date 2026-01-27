

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;

public class FileUploadClient {

    public static void main(String[] args) throws Exception {

        String url = "http://localhost:8080/HTTPCLient_war_exploded/uploads";
        File file = new File("C:\\Users\\Axeno\\Downloads\\aem.log");

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost post = new HttpPost(url);
            post.setHeader("Authorization", "prateek 1403");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file);

            post.setEntity(builder.build());

            var response = client.execute(post);

           // System.out.println("Status: " + response.getCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }
}