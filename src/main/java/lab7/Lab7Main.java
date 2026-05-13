package lab7;

import lab7.client.ClientMain;
import lab7.server.ServerMain;
import main.Solution;

public class Lab7Main implements Solution {

    private boolean isServer;

    public Lab7Main(boolean isServer){
        this.isServer = isServer;
    }

    @Override
    public void solve() {
        Runnable side = isServer ? new ServerMain() : new ClientMain();
        side.run();
    }
}
