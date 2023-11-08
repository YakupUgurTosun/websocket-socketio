package com.yakup.socketio;


import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Value("${socket-server.host}")        //aplication.properties de tanımlıyabilirsiniz
    private String host;
                                            //postman testde web io socet seceriz ve urlmiz "ws://0.0.0.0:8085?room=a" bu şekilde gözükür
                                                        //postman setting den client version v2 deneyebiliriz
    @Value("${socket-server.port}")        //aplication.properties de tanımlıyabilirsiniz
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);         // -host a kendi ıp nizide verebilirsiniz
        config.setPort(port);            // 8080 portu vermeyiniz çünkü Tomcat ile çakışır
        return new SocketIOServer(config);
    }

}