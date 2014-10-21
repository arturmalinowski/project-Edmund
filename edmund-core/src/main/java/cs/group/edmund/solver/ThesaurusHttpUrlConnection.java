package cs.group.edmund.solver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ThesaurusHttpUrlConnection {

    public static ThesaurusHttpUrlConnection http;

    private HttpURLConnection con;
    private int responseCode;

    public static void main(String[] args) {

        http = new ThesaurusHttpUrlConnection();

        System.out.println("Send Http GET request");
    }

    public String sendGet(String word) throws Exception {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/";

        URL obj = new URL(url);
        con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    public HttpURLConnection connection() {
        return con;
    }

    public int responseCode() {
        return responseCode;
    }
}
