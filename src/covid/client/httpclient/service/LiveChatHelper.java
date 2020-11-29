package covid.client.httpclient.service;

import covid.client.logging.LoggingManager;
import covid.client.models.Constants;
import covid.client.models.request.LiveChatMessage;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.util.Collections;
import java.util.List;

/**
 * Created by RICARDO GAYNOR
 */
public class LiveChatHelper {


    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    public ListenableFuture<StompSession> connect() {

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        return stompClient.connect(Constants.WEBSOCKST_SERVICE_URL, headers, new MyHandler(), "localhost", 8005);
    }

    public void sendMessage(final StompSession stompSession, final LiveChatMessage liveChatMessage) throws InterruptedException {
        stompSession.send("/app/v1/send/message/"+liveChatMessage.getTo(), liveChatMessage.toJsonString().getBytes());
    }

    private class MyHandler extends StompSessionHandlerAdapter {
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            LoggingManager.getLogger(this).info("Now connected");
            System.out.println("Now connected");
        }
    }

    
}
