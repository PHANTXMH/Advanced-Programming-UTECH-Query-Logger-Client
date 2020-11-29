package covid.client.httpclient.service;

import covid.client.logging.LoggingManager;
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

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by nick on 30/09/2015.
 */
public class LiveChatHelper {


    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    public ListenableFuture<StompSession> connect() {

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://localhost:8005/v1/send/message";
        //String url = "ws://ap-utech-2020-covid19-chat.herokuapp.com/v1/send/message";
        return stompClient.connect(url, headers, new MyHandler(), "localhost", 8005);
    }

    public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
        stompSession.subscribe("/user/target/1", new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
                LoggingManager.getLogger(this).info("Received greeting " + new String((byte[]) o));
                System.out.println("Received greeting " + new String((byte[]) o));
            }
        });
    }

    public void sendHello(final StompSession stompSession, final LiveChatMessage liveChatMessage) throws InterruptedException {
        stompSession.send("/app/v1/send/message/2", liveChatMessage.toJsonString().getBytes());
//        for(int i = 0; i < 59; i++){
//            Thread.sleep(2000);
//            stompSession.send("/app/v1/send/message/2", liveChatMessage.toJsonString().getBytes());
//        }
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
    
    public static void main(String[] args) throws Exception {


        LiveChatHelper helloClient = new LiveChatHelper();

        ListenableFuture<StompSession> f = helloClient.connect();
        StompSession stompSession = f.get();

        LoggingManager.getLogger(LiveChatHelper.class).info("Subscribing to greeting topic using session " + stompSession);


        System.out.println("Subscribing to greeting topic using session " + stompSession);
        helloClient.subscribeGreetings(stompSession);


        LoggingManager.getLogger(LiveChatHelper.class).info("Sending hello message" + stompSession);

        System.out.println("Sending hello message" + stompSession);



        helloClient.sendHello(stompSession, null);
        Thread.sleep(60000);


    }
    
}
