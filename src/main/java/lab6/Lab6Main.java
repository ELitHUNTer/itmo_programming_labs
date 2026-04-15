package lab6;

import lab6.client.ClientMain;
import lab6.server.ServerMain;
import main.Solution;

public class Lab6Main implements Solution {

    private boolean isServer;

    public Lab6Main(boolean isServer){
        this.isServer = isServer;
    }

    @Override
    public void solve() {
        Runnable side = isServer ? new ServerMain() : new ClientMain();
        side.run();
    }
}
