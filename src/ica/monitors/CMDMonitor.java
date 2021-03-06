package ica.monitors;

import ica.messages.Message;

/**
 * This class is designed to to display the data about a message, including
 * where it was sent, who it was sent from and what the type and data of the
 * message is in the command line.
 *
 * @author v8036651
 */
public class CMDMonitor extends Monitor {

    /**
     * A name of the agent must be passed as it says which agent the monitor is
     * on, this is to compare the intended recipient and the actual, requires a
     * message to be passed for the output.
     *
     * @param name the name of the agent which is being monitored
     * @author v8036651
     */
    public CMDMonitor(String name) {
        super(name);
    }

    /**
     * Method to output to the command line showing the name of the sender, the
     * name of the recipient, the actual recipient, the message type and the
     * message details when a message is received.
     *
     * @param actualSender the name of the node which forwarded the message.
     * @param msg the message which was sent.
     * @author v8036651
     */
    @Override
    public void ReceivedMessage(Message msg, String actualSender) {
        System.out.println("+==========================================+");
        System.out.println("|This message was received from:           |");
        System.out.printf("|%42s|\n", msg.getSender());
        System.out.println("|This message was received by:             |");
        System.out.printf("|%42s|\n", agentName);
        System.out.println("|This message was meant to be received by: |");
        System.out.printf("|%42s|\n", msg.getRecipient());
        System.out.println("|This message type is:                     |");
        System.out.printf("|%42s|\n", msg.getMessageType());
        System.out.println("|The contents of the message is:           |");
        System.out.printf("|%42s|\n", msg.getMessageDetails());
        System.out.println("+==========================================+");
        System.out.println("");
    }

    /**
     * Method to output to the command line showing the name of the sender, the
     * name of the intended sender, the intended recipient, the message type and
     * the message details when a message is sent.
     *
     * @param actualReceiver the name of the node which is sending the message.
     * @param msg the message which is being sent.
     * @author v8036651
     */
    @Override
    public void SentMessage(Message msg, String actualReceiver) {
        System.out.println("+==========================================+");
        System.out.println("|This message was sent from:               |");
        System.out.printf("|%42s|\n", agentName);
        System.out.println("|This message is meant to be sent from:    |");
        System.out.printf("|%42s|\n", msg.getSender());
        System.out.println("|This message is meant to be received by:  |");
        System.out.printf("|%42s|\n", msg.getRecipient());
        System.out.println("|This message type is:                     |");
        System.out.printf("|%42s|\n", msg.getMessageType());
        System.out.println("|The contents of the message is:           |");
        System.out.printf("|%42s|\n", msg.getMessageDetails());
        System.out.println("+==========================================+");
        System.out.println("");
    }
}
