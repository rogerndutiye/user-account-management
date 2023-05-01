package rw.usermanagement.userauthservice.Config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.usermanagement.userauthservice.Dto.request.CreateUserDTO;
import rw.usermanagement.userauthservice.Dto.response.UserDTO;
import rw.usermanagement.userauthservice.Dto.response.UserProfileDTO;
import rw.usermanagement.userauthservice.Model.User;
import rw.usermanagement.userauthservice.Model.UserProfile;

@Mapper(componentModel = "spring")
public interface ZMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(CreateUserDTO dto);
    UserDTO toDTO(User entity);
    @Mapping(target = "user.password", ignore = true)
    UserProfileDTO toUserProfileDTO(UserProfile entity);
}
