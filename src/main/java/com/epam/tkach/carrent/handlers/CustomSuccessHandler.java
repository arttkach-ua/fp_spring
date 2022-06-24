package com.epam.tkach.carrent.handlers;

import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    TransactionService transactionService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response, final Authentication authentication)
            throws IOException, ServletException {
        //Getting user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        try {
            HttpSession session = request.getSession(true);
            session.setAttribute("balance", transactionService.getUserBalance(userDetails.getUsername()));
        } catch (NoSuchUserException e) {
            logger.error(e);
        }

        response.sendRedirect(request.getContextPath());
    }

}