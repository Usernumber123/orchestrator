package com.efimov.orchestrator.converter;


import com.efimov.orchestrator.model.UserDto;
import com.efimov.orchestrator.domain.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoUserConverter implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setAge(userDto.getAge());
        user.setPassword(userDto.getPassword());
        return user;
    }
}