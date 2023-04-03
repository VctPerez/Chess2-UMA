package multiplayer;

import java.io.IOException;

public class Player {
    boolean color;
    String name;
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
