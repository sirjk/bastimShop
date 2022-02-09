package com.company.shopBastim.utility;

import com.company.shopBastim.filter.CustomAuthenticationFilter;
import com.company.shopBastim.repository.UserRepository;
import com.company.shopBastim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;


@Component
public class Initializer {

    @Value("${refresh-token-TTL:10}")
    private Integer refreshTokenTTL;

    @Value("${refresh-token-longevity:720}")
    private Integer refreshTokenLongevity;

    @Value("${refresh-token-dynamic-settings-mode:false}")
    private Boolean refreshTokenDynamicSettingsMode;

    @Value("${refresh-token-max-longevity:1440}")
    private Integer refreshTokenMaxLongevity;

    @Value("${refresh-token-min-longevity:60}")
    private Integer refreshTokenMinLongevity;

    @Value("${refresh-token-decrease-multiplier:0.5}")
    private Double refreshTokenDecreaseMultiplier;



    public CustomAuthenticationFilter returnCustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository){
        return new CustomAuthenticationFilter(authenticationManager, userRepository, refreshTokenTTL, refreshTokenLongevity, refreshTokenDynamicSettingsMode, refreshTokenMaxLongevity, refreshTokenMinLongevity, refreshTokenDecreaseMultiplier);
    }

    public RefreshTokenUtility returnRefreshTokenUtility(UserService userService){
        return new RefreshTokenUtility(userService, refreshTokenTTL, refreshTokenLongevity, refreshTokenDynamicSettingsMode, refreshTokenMaxLongevity, refreshTokenMinLongevity, refreshTokenDecreaseMultiplier);
    }
}
