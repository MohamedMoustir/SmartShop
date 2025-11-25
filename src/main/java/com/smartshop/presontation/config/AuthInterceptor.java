package com.smartshop.presontation.config;

import com.smartshop.domain.Excption.ForbiddenException;
import com.smartshop.domain.Excption.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_ROLE_KEY = "USER_ROLE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session =request.getSession(false);
        if(session == null || session.getAttribute(USER_ID_KEY) == null){
            throw new UnauthorizedException("Session expirée ou non authentifié. Veuillez vous connecter.");

        }
        String userRle = session.getAttribute(USER_ROLE_KEY).toString();
        String requestPath = request.getRequestURI();
        if(requestPath.startsWith("/api/clients") || requestPath.startsWith("/api/products")){
            if("CLIENT".equals(userRle) && !request.getMethod().equalsIgnoreCase("GET")){
                throw new ForbiddenException("Accès refusé. Seul l'ADMIN peut créer, modifier ou supprimer ces ressources.");
            }
            if("CLIENT".equals(userRle) && requestPath.startsWith("/api/clients")){
                throw new ForbiddenException("Accès refusé. Un CLIENT ne peut pas voir la liste des autres clients.");           }

        }

        return true;
    }

}

