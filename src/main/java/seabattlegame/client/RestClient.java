package seabattlegame.client;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RestClient {
    class Response {

        private String playerNumber = "n/a";
        private String opponentName = "n/a";

        public String getPlayerNumber() {
            return playerNumber;
        }

        public void setPlayerNumber(String playerNumber) {
            this.playerNumber = playerNumber;
        }

        public String getOpponentName() {
            return opponentName;
        }

        public void setOpponentName(String opponentName) {
            this.opponentName = opponentName;
        }
    }

    public String getOpponentName(int playerNr) {

        final String query = "http://localhost:8090/opponent/" + Integer.toString(playerNr);
        String stringResult = null;
        System.out.println("[Query] : " + query);

        // Perform the query
        HttpGet httpGet = new HttpGet(query);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet);) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            Gson gson = new Gson();
            Response jsonResponse = gson.fromJson(entityString, Response.class);
            stringResult = jsonResponse.getOpponentName();
            System.out.println("[Result] : " + stringResult);
        } catch (IOException e) {
            // Evil, pure evil this solution: ....
            System.out.println("IOException : " + e.toString());
        }

        return stringResult;
    }
}
