package praekelt.weblistingapp.ListView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by altus on 2015/02/04.
 * @author  Altus Barry
 * @version 1.0
 *
 */
public class Socket extends Fragment {

    private int lastPublishOn = 0;
    private ConnectionThread connectThread = new ConnectionThread();
    private HeartBeatThread heartBeatThread = new HeartBeatThread();

    // Socket
    private FragmentSocket client = null;
    private static final String ECHIDNA_URL = "qa-visual-radio.za.prk-host.net";

    public messageCallback callback;

    /**
     * savedInstanceState bundle data used when applicable
     * Connection Thread started.
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (messageCallback) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString() + " must implement OnArticleSelectedListener");
        }

        try {
            client = new FragmentSocket(new URI("ws://" + ECHIDNA_URL + ":8888/subscribe"), new Draft_10());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // Initiate Socket connection
        connectThread.start();
    }

    public interface messageCallback {
        public void incomingContent(String data);
    }

    /**
     * Set the lastPublishOn time to send to socket server
     * @param lastPublishOn
     */
    public void lastPublishOn(int lastPublishOn) {
        this.lastPublishOn = lastPublishOn;
    }


    private static final String EXCEPTION = "exeption";
    private static final String LOST_CONNECTION = "lostConnection";
    private static final String SERVER_SHUTDOWN = "serverShutDown";

    /**
     * Switch case statement to handle some known errors that can occur with the server or connection
     * @param error
     */
    private void networkIssues(String error) {
        switch (error) {
            case EXCEPTION:
                break;
            case LOST_CONNECTION:
                connectThread.start();
                break;
            case SERVER_SHUTDOWN:
                connectThread.start();
                break;
        }
    }

    /**
     * Checks whether there is an active network connection on te phone
     * @return true if yes, false if no
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Log.e("Network Status: ", "Network's Down");
            return false;
        } else {
            Log.i("Network Status: ", "Network's Up");
            return true;
        }
    }

    /**
     * tries to connect to the google server to check if internet access is available
     * @return true if yes, false if no
     */
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("8.8.8.8");

            if (ipAddr.equals("")) {
                Log.e("Internet Status: ", "Cannot connect to Internet");
                return false;
            } else {
                Log.i("Internet Status: ", "Connected");
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * method for connecting the client
     * made a method merely for easier access by other methods and a controllable point to check if
     * connection is being made
     */
    private void connectSocket() {
        client.connect();
    }

    /**
     * Nested Websocket class
     */
    class FragmentSocket extends WebSocketClient {

        public FragmentSocket(URI serverURI, Draft draft) {
            super(serverURI, draft);
        }

        public FragmentSocket(URI serverURI) {
            super(serverURI);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            long timeLimit = (((System.currentTimeMillis())-10800000)/1000);

            if(lastPublishOn < (timeLimit)) {
                this.send("{\"msg_type\": \"subscribe\", \"channel\": \"weblistingapp\", \"last_seen\":" + String.valueOf(timeLimit) + "}");
                Log.i("SENT TIME", String.valueOf(timeLimit));
            } else {
                this.send("{\"msg_type\": \"subscribe\", \"channel\": \"weblistingapp\", \"last_seen\":" + String.valueOf(lastPublishOn) + "}");
                Log.i("SENT TIME", String.valueOf(lastPublishOn));
            }
            Log.i("Websocket: ", "Handshake successfull");
        }

        @Override
        public void onMessage(String message) {
            callback.incomingContent(message);
            Log.i("Client Message:", message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.i("WebSocket closed with exit code " + code, " additional info: " + reason);
            networkIssues(SERVER_SHUTDOWN);
        }

        @Override
        public void onError(Exception ex) {
            Log.e("Network Error", ex.toString());
            networkIssues(EXCEPTION);
        }
    }

    /**
     * Thread to connect client
     * Checks if Network and Internet is up, polls till true
     */
    class ConnectionThread extends Thread {

        final Handler connectionHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                connectSocket();
            }
        };
        public void run() {
            //heartBeatThread.start();
            while (!Thread.currentThread().isInterrupted()) {
                if(isNetworkConnected() && isInternetAvailable()) {
                    connectionHandler.sendEmptyMessage(0);
                    Thread.currentThread().interrupt();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // TODO currently does not message Server
    /**
     * Thread to intermittently check if server is still reachable
     */
    class HeartBeatThread extends Thread {

        final Handler heartBeatHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                networkIssues(LOST_CONNECTION);
            }
        };
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                // Check for response

                // if(!response){
                // heartBeatHandler.sendEmptyMessage(0);
                // Thread.currentThread().interrupt();
                // }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
