package server.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.json.actions.IAction;
import server.json.actions.Register;

public class Message {
    private String action;
    private String content;
    private IAction data;

    public Message(String action) {
        this.action = action;
    }

    public Message(String action, String content) {
        this.action = action;
        this.content = content;
    }

    public Message(String action, IAction data) {
        this.action = action;
        Gson gson = new Gson();
        this.content = gson.toJson(data);
    }

    public String getAction() {
        return action;
    }

    public String getContent() {
        return content;
    }

    public IAction getData() {
        return data;
    }

    public void parseData(Class<? extends IAction> iAction){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(content).getAsJsonObject();
        Gson g = new Gson();
        this.data = g.fromJson(jsonObject, iAction);
    }
}
