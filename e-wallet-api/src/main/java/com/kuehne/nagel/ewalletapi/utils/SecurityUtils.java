package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.models.dtos.UserDetailDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static UUID getUserId() {

        UserDetailDto principal = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }
}
