package com.aldiramdan.library.model.dto.response;

import com.aldiramdan.library.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUser {
    private Long id;
    private String name;
    private String username;
    private String email;

    public ResponseUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
