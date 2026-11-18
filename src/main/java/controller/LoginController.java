
package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.LoginAccount;
import model.ModelException;
import model.PasswordUtil;
import model.dao.DAOFactory;
import model.dao.LoginAccountDAO;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        if (action.equals("/login")) {

            HttpSession session = req.getSession(false);

            if (session != null &&
                    session.getAttribute("loggedUser") != null) {

                ControllerUtil.redirect(resp,
                        req.getContextPath() + "/");
                return;
            }

            ControllerUtil.transferSessionMessagesToRequest(req);

            ControllerUtil.forward(req, resp, "/login.jsp");

        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {

        case "/login":
            authenticate(req, resp);
            break;

        case "/logout":
            logout(req, resp);
            break;

        default:
            resp.sendError(
                    HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void authenticate(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank()
                || password == null || password.isEmpty()) {

            ControllerUtil.errorMessage(req,
                    "Informe usuário e senha.");

            ControllerUtil.redirect(resp,
                    req.getContextPath() + "/login");
            return;
        }

        try {
            LoginAccountDAO dao =
                    DAOFactory.createDAO(LoginAccountDAO.class);

            LoginAccount account =
                    dao.findByUsername(username);

            if (account != null
                    && account.isActive()
                    && PasswordUtil.verifyPassword(
                            password, account.getPasswordHash())) {

                HttpSession session = req.getSession();

                // Renova o identificador da sessão no login
                req.changeSessionId();

                session.setAttribute("loggedUser", account);

                ControllerUtil.redirect(resp,
                        req.getContextPath() + "/");

            } else {

                ControllerUtil.errorMessage(req,
                        "Usuário ou senha inválidos.");

                ControllerUtil.redirect(resp,
                        req.getContextPath() + "/login");
            }

        } catch (ModelException e) {
            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Erro ao acessar o banco de dados.");

            ControllerUtil.redirect(resp,
                    req.getContextPath() + "/login");
        }
    }

    private void logout(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        ControllerUtil.redirect(resp,
                req.getContextPath() + "/login");
    }
}
