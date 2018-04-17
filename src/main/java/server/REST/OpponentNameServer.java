package server.REST;

import com.google.gson.Gson;
import server.messageHandler.SeaBattleGameMessageHandler;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;


@Path("/opponent")
public class OpponentNameServer {
    private Session websocketSession;

    @GET
    @Path("/{playernumber}")
    public Response getMsg(@PathParam("playernumber") String message) {
        OpponentResponse response = new OpponentResponse();
        response.setPlayerNumber(message);
        try {

            int playernumber = Integer.parseInt(message);
            SeaBattleGameMessageHandler messageHandler = new SeaBattleGameMessageHandler();
            String opponentname;
            if (message.equals("1")) {
                opponentname = messageHandler.getPlayer2Name();
            } else if (message.equals("2")) {
                opponentname = messageHandler.getPlayer1Name();
            }
        } catch (NumberFormatException e) {
            response.setOpponentName("error: invalid value");
        }
        Gson json = new Gson();
        String output = json.toJson(response);
        return Response.status(200).entity(output).build();
    }
}
