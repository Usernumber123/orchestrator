package com.efimov.orchestrator.converter;


import com.efimov.orchestrator.model.UserDto;
import com.efimov.orchestrator.domain.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setAge(user.getAge());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
