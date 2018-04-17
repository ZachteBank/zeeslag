package server.REST;

import com.google.gson.Gson;
import seabattlegame.client.ClientEndpointSocket;
import server.EventServerSocket;
import server.messageHandler.SeaBattleGameMessageHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.awt.*;

import static server.EventServerSocket.getMessageHandler;


@Path("/opponent")
public class OpponentNameServer {

    @GET
    @Path("/{playernumber}")
    public Response getMsg(@PathParam("playernumber") String message) {
        OpponentResponse response = new OpponentResponse();
        response.setPlayerNumber(message);

        String opponentname;
        if (message.equals("1")) {
            SeaBattleGameMessageHandler messageHandler = (SeaBattleGameMessageHandler) EventServerSocket.getMessageHandler();
            opponentname = messageHandler.getPlayer2Name();
        } else if (message.equals("2")) {
            opponentname = ((SeaBattleGameMessageHandler) getMessageHandler()).getPlayer1Name();
        } else {
            opponentname = "error: invalid value";
        }
        response.setOpponentName(opponentname);

        Gson json = new Gson();
        String output = json.toJson(response);
        return Response.status(200).entity(output).build();
    }
}
