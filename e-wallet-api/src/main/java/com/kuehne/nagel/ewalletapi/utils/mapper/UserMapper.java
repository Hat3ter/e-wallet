package com.kuehne.nagel.ewalletapi.utils.mapper;

import com.kuehne.nagel.ewalletapi.models.dtos.UserDetailDto;
import com.kuehne.nagel.ewalletapi.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Converter for {@link User} and {@link UserDetailDto}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Instance
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Convert {@link User} and {@link UserDetailDto}
     *
     * @param user {@link User}
     * @return {@link UserDetailDto}
     */
    UserDetailDto convert(User user);
}
