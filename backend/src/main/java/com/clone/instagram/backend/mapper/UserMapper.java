package com.clone.instagram.backend.mapper;


import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.model.MSignUpResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MSignUpResponse toSignUpResponse(User user);

}
