package se.yrgo.UserService.dto;

import java.util.List;

import se.yrgo.UserService.domain.User;

public record UserDto (
    Long id,
    String name,
    String username,
    List<String> favouriteSongs) {
    
    public static UserDto createUserDto(User u){
        return new UserDto(
            u.getId(),
            u.getName(), 
            u.getUsername(), 
            u.getFavouriteSongs());
    }
}
