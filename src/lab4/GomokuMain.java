package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/**
 *
 */
public class GomokuMain {
    public static void main(String[] args) {
        int portNumber = 4000;
        if (args.length <= 1) {
            portNumber = Integer.parseInt(args[0]);
        } else {
            System.out.println("Too many arguments. Shutting down!");
            System.exit(0);
        }

        System.out.println("Opening game to port " + portNumber);
        GomokuClient gc = new GomokuClient(portNumber);
        GomokuGameState gs = new GomokuGameState(gc);
        GomokuGUI gg = new GomokuGUI(gs, gc);
    }
}
