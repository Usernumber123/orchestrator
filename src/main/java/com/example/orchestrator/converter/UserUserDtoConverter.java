package com.example.orchestrator.converter;


import com.example.orchestrator.model.UserDto;
import com.example.orchestrator.domain.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserUserDtoConverter  implements Converter<User, UserDto> {
   @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
