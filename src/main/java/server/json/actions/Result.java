package server.json.actions;

public class Result implements IAction {

    private boolean result;

    public Result(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }
}
