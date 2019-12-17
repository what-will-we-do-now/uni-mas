/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icametaagent;

import icamessages.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v8036651
 */
public class SocketAgent extends MetaAgent implements Runnable {

    protected Portal portalConection;
    protected Socket routerConnection;
    private ArrayBlockingQueue<Message> messageQueue;
    private boolean busy;

    /**
     * Draws from the super class of MetaAgent,
     * Constructor for the socket agent.
     * @param name
     * @param p
     * @param s 
     * @author v8036651
     * @author v8073331
     */
    public SocketAgent(String name, Portal p, Socket s) {
        super(name);
        busy = false;
        portalConection = p;
        routerConnection = s;
        messageQueue = new ArrayBlockingQueue<>(20);
    }

    /**
     * Overwrites the messageHandler method from the super class of MetaAgent,
     * this adds a message to the message queue.
     * @param agent
     * @param msg 
     * @author v8073331
     */
    @Override
    public void messageHandler(MetaAgent agent, Message msg) {
        messageQueue.add(msg);
    }

    /**
     * Checks for incoming messages and send outgoing messages.
     * @author v8073331
     */
    @Override
    public void run() {

        try {
            InputStream in = routerConnection.getInputStream();
            OutputStream out = routerConnection.getOutputStream();
            while (!routerConnection.isClosed()) {

                
                if(in.available() != 0){
                    byte[] rmsg = new byte[in.available()];     //rmsg is a recived message in byte format.
                    in.read(rmsg);
                    
                    String armsg = new String(rmsg);            //armsg actual recived message in string format.
                    
                    if(armsg.equals("#")){
                        busy = false;
                        
                    }else if(armsg.startsWith("#")){
                        
                        busy = false;
                        armsg = armsg.substring(1);
                        Message farmsg = Message.parseMessage(armsg);     //farmsg final actual message to be sent again as a message object.
                        portalConection.messageHandler(this, farmsg);
                        out.write("#".getBytes());
                        out.flush();
                        
                    }else if(armsg.endsWith("#")){
                        
                        busy = false;
                        armsg = armsg.substring(0, armsg.length()-1);
                        Message farmsg = Message.parseMessage(armsg);
                        portalConection.messageHandler(this, farmsg);
                        out.write("#".getBytes());
                        out.flush();
                        
                    }else{
                        Message farmsg = Message.parseMessage(armsg);
                        portalConection.messageHandler(this, farmsg);
                        out.write("#".getBytes());
                        out.flush();
                    }
                }
                
                if(!messageQueue.isEmpty() && !busy){
                    Message msg = messageQueue.poll();
                    out.write(msg.toString().getBytes());
                    out.flush();
                    busy = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}