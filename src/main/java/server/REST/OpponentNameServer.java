package server.REST;

import com.google.gson.Gson;
import seabattlegame.client.ClientEndpointSocket;
import server.EventServerSocket;
import server.messageHandler.IMessageHandler;
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
    public Response getMsg(@PathParam("playernumber") String message) throws Exception {
        OpponentResponse response = new OpponentResponse();
        response.setPlayerNumber(message);

        String opponentname;
            IMessageHandler messageHandler = EventServerSocket.getMessageHandler();
        if (!(messageHandler instanceof SeaBattleGameMessageHandler)) {
            throw new Exception("MessageHandler isn't a instace of SeaBattleGameMessageHandler");
        }
        if (message.equals("0")) {
            opponentname = ((SeaBattleGameMessageHandler) messageHandler).getPlayer2Name();
        } else if (message.equals("1")) {
            opponentname = ((SeaBattleGameMessageHandler) messageHandler).getPlayer1Name();
        } else {
            opponentname = "error: invalid value";
        }
        response.setOpponentName(opponentname);

        Gson json = new Gson();
        String output = json.toJson(response);
        return Response.status(200).entity(output).build();
    }
}
