
package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse resp =
                (HttpServletResponse) response;

        String path = req.getServletPath();

        // Recursos que podem ser acessados sem Login
        boolean publicResource =
                path.equals("/login")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/fonts/")
                || path.equals("/favicon.ico");

        if (publicResource) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        boolean authenticated =
                session != null
                && session.getAttribute("loggedUser") != null;

        if (!authenticated) {
            resp.sendRedirect(
                    req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
