package com.yakup.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;                                                     //socket üzerinden mesajı attığımız metotun parçası
        server.addEventListener("send_message", Message.class, onChatReceived());  //postman test de message send etiğimiz kutuya eklenir

    }


    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            String room = senderClient.getHandshakeData().getSingleUrlParam("room");
            log.info(data.toString());                                                      //socketi dinlediğimiz metodun parçası
            socketService.sendMessage(room,"get_message", senderClient, data);  //postman testinde dinlenilecek event a ekleyelim
        };
    }
}