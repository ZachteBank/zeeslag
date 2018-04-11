package server.json.actions;

public class Register implements IAction{
    private String name;

    public Register(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
