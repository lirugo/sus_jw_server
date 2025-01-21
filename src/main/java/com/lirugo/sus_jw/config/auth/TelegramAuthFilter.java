package com.lirugo.sus_jw.config.auth;

import com.lirugo.sus_jw.service.UserService;
import com.lirugo.sus_jw.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class TelegramAuthFilter extends OncePerRequestFilter {

    private final TelegramAuthValidator telegramAuthValidator;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            var data = Utils.parseAuthHeader(authHeader);

            if (telegramAuthValidator.verifyInitData(authHeader)) {
                // Create an authenticated token
                var userJson = data.get("user");
                var userDto = userService.update(userJson);
                var authentication = new TelegramAuthenticationToken(userDto.getUsername(), true);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


}