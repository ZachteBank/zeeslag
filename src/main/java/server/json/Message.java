package server.json;

import com.google.gson.Gson;
import server.json.actions.IAction;
import server.json.actions.Register;

public class Message {
    private String action;
    private String content;
    private IAction data;

    public Message(String action, String content) {
        this.action = action;
        this.content = content;
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
        Gson g = new Gson();
        this.data = g.fromJson(content, iAction);
    }
}
