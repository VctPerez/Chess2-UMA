package multiplayer;

import java.io.IOException;

public class Player {

    //Host host;
    boolean color;
    String name;
    public Player(String name) throws IOException {
        this.name = name;
        //host = new Host();
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
