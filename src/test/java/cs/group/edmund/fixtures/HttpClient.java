package cs.group.edmund.fixtures;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class HttpClient {

    public static String makeRequest(String url) {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        HttpMethod method = new GetMethod(url);

        String responseBody = "";
        try {
            client.executeMethod(method);
            responseBody = method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

}
