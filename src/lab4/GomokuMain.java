package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/**
 *
 */
public class GomokuMain {
    private static int portNumber = 4000;
    private static final int maxArgs = 1;


    public static void main(String[] args) {
        if (args.length == maxArgs) {
            portNumber = Integer.parseInt(args[0]);
            System.out.println("Opening game to port: " + portNumber);
        } else if (args.length == 0) {
            System.out.println("Setting port number to default:" + portNumber);
        } else {
            System.out.println("Incorrect number of arguments. Expected: " + maxArgs);
            System.out.println("Shutting down!");
            System.exit(0);
        }

        GomokuClient gc = new GomokuClient(portNumber);
        GomokuGameState gs = new GomokuGameState(gc);
        GomokuGUI gg = new GomokuGUI(gs, gc);
    }
}
