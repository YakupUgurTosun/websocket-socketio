package com.yakup.user;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @PostConstruct
    public  void addUser(){
        List<User> userList =  userRepository.findAll();
        if(userList.isEmpty()){
        User user = new User();
        user.setRoom("a");
        userRepository.save(user);
        }
    }

    public Optional<User> findByIdAndRoomId(String token, String room) {
        return userRepository.findByIdAndRoom(token,room);
    }
}
