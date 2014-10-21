package cs.group.edmund.fixtures;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class HttpClient {

    private static int responseCode;

    public static String makeRequest(String url) {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        HttpMethod method = new GetMethod(url);

        String responseBody = "";
        try {
            responseCode = client.executeMethod(method);
            responseBody = method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;

    }

    public static int responseCode() {
        return responseCode;
    }

}
