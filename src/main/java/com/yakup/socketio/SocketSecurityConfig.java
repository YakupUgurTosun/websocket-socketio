package com.yakup.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.yakup.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@AllArgsConstructor
public class SocketSecurityConfig {

    private final String API_TOKEN = "API_TOKEN";

    private final SocketIOServer server;

    private final UserService userService;


    @PostConstruct
    public void init(){
        server.addConnectListener(onConnected());
    }

    private ConnectListener onConnected() {
        return (client) -> {
            String token = client.getHandshakeData().getHttpHeaders().get(API_TOKEN);    //Tokenımızı header dan alacağız postmanden test sırasında ekleyiniz
            String room = client.getHandshakeData().getSingleUrlParam("room");      //room bilgisini param dan alacağız postman test sırasında ekleyin

            if(isAuthorized(token,room)){
                client.joinRoom(room);
                log.info("Connected : " + client.getSessionId());
            }else {
                log.warn("Socket ID[{}] Unauthorized", client.getSessionId().toString());
                client.disconnect();
                log.info("Disconnected : " + client.getSessionId());
            }
        };
    }


    private boolean isAuthorized(String token, String room){
        if(userService.findByIdAndRoomId(token, room).isPresent())  //kullanıcı ıd ile header dan gelen token aynı mı
            return true;                                            //bide paramdan gelen room ile kullanıcıdaki room aynımı kontrolü yapar
        else return false;
    }

}