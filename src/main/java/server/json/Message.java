package server.json;

import server.json.actions.IAction;

public class Message {
    private String actionType;
    private IAction action;

    public Message(String actionType, IAction action) {
        this.actionType = actionType;
        this.action = action;
    }

    public String getActionType() {
        return actionType;
    }

    public IAction getAction() {
        return action;
    }
}
