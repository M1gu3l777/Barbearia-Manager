
package controller;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Barber;
import model.ModelException;
import model.User;
import model.dao.BarberDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {
        "/barbers",
        "/barber/form",
        "/barber/insert",
        "/barber/delete",
        "/barber/update"
})
public class BarbersController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {

        case "/barber/form": {
            CommonsController.listUsers(req);

            req.setAttribute("action", "insert");

            ControllerUtil.forward(req, resp,
                    "/form-barber.jsp");
            break;
        }

        case "/barber/update": {
            int id = Integer.parseInt(
                    req.getParameter("barberId"));

            BarberDAO dao =
                    DAOFactory.createDAO(BarberDAO.class);

            Barber barber = null;

            try {
                barber = dao.findById(id);
            } catch (ModelException e) {
                e.printStackTrace();
            }

            CommonsController.listUsers(req);

            req.setAttribute("action", "update");
            req.setAttribute("barber", barber);

            ControllerUtil.forward(req, resp,
                    "/form-barber.jsp");
            break;
        }

        default: {
            listBarbers(req);

            ControllerUtil.transferSessionMessagesToRequest(req);

            ControllerUtil.forward(req, resp,
                    "/barbers.jsp");
            break;
        }
        }
    }

    private void listBarbers(HttpServletRequest req) {

        BarberDAO dao =
                DAOFactory.createDAO(BarberDAO.class);

        try {
            List<Barber> barbers = dao.listAll();

            // Paginação: 5 barbeiros por página
            int tamanhoPagina = 5;
            int totalRegistros = barbers.size();

            int totalPaginas = Math.max(1,
                    (int) Math.ceil(
                            (double) totalRegistros / tamanhoPagina));

            int paginaAtual = 1;

            try {
                String parametro = req.getParameter("page");

                if (parametro != null) {
                    paginaAtual = Integer.parseInt(parametro);
                }

            } catch (NumberFormatException e) {
                paginaAtual = 1;
            }

            // Impede páginas inválidas
            if (paginaAtual < 1) {
                paginaAtual = 1;
            }

            if (paginaAtual > totalPaginas) {
                paginaAtual = totalPaginas;
            }

            int inicio = (paginaAtual - 1) * tamanhoPagina;

            int fim = Math.min(
                    inicio + tamanhoPagina, totalRegistros);

            req.setAttribute("barbers",
                    barbers.subList(inicio, fim));

            req.setAttribute("currentPage", paginaAtual);
            req.setAttribute("totalPages", totalPaginas);

        } catch (ModelException e) {
            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Erro ao listar barbeiros.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {

        case "/barber/insert": {
            insertBarber(req);
            break;
        }

        case "/barber/update": {
            updateBarber(req);
            break;
        }

        case "/barber/delete": {
            deleteBarber(req);
            break;
        }

        default:
            System.out.println("URL inválida: " + action);
        }

        ControllerUtil.redirect(resp,
                req.getContextPath() + "/barbers");
    }

    private void insertBarber(HttpServletRequest req) {

        try {
            Barber barber = new Barber();

            barber.setName(req.getParameter("name"));
            barber.setPhone(req.getParameter("phone"));
            barber.setSpecialty(req.getParameter("specialty"));

            barber.setStartTime(Time.valueOf(
                    req.getParameter("startTime") + ":00"));

            barber.setEndTime(Time.valueOf(
                    req.getParameter("endTime") + ":00"));

            barber.setActive(
                    req.getParameter("active") != null);

            int userId = Integer.parseInt(
                    req.getParameter("user"));

            barber.setUser(new User(userId));

            if (!validarHorario(barber)) {
                ControllerUtil.errorMessage(req,
                        "O horário inicial deve ser menor "
                        + "que o horário final.");
                return;
            }

            BarberDAO dao =
                    DAOFactory.createDAO(BarberDAO.class);

            if (dao.save(barber)) {
                ControllerUtil.sucessMessage(req,
                        "Barbeiro cadastrado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                        "Não foi possível cadastrar o barbeiro.");
            }

        } catch (ModelException | IllegalArgumentException e) {
            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Erro ao cadastrar barbeiro: "
                    + e.getMessage());
        }
    }

    private void updateBarber(HttpServletRequest req) {

        try {
            int id = Integer.parseInt(
                    req.getParameter("barberId"));

            Barber barber = new Barber(id);

            barber.setName(req.getParameter("name"));
            barber.setPhone(req.getParameter("phone"));
            barber.setSpecialty(req.getParameter("specialty"));

            barber.setStartTime(Time.valueOf(
                    req.getParameter("startTime") + ":00"));

            barber.setEndTime(Time.valueOf(
                    req.getParameter("endTime") + ":00"));

            barber.setActive(
                    req.getParameter("active") != null);

            int userId = Integer.parseInt(
                    req.getParameter("user"));

            barber.setUser(new User(userId));

            if (!validarHorario(barber)) {
                ControllerUtil.errorMessage(req,
                        "O horário inicial deve ser menor "
                        + "que o horário final.");
                return;
            }

            BarberDAO dao =
                    DAOFactory.createDAO(BarberDAO.class);

            if (dao.update(barber)) {
                ControllerUtil.sucessMessage(req,
                        "Barbeiro atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                        "Não foi possível atualizar o barbeiro.");
            }

        } catch (ModelException | IllegalArgumentException e) {
            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Erro ao atualizar barbeiro: "
                    + e.getMessage());
        }
    }

    private void deleteBarber(HttpServletRequest req) {

        try {
            int id = Integer.parseInt(
                    req.getParameter("id"));

            BarberDAO dao =
                    DAOFactory.createDAO(BarberDAO.class);

            Barber barber = dao.findById(id);

            if (barber == null) {
                ControllerUtil.errorMessage(req,
                        "Barbeiro não encontrado.");
                return;
            }

            if (dao.delete(barber)) {
                ControllerUtil.sucessMessage(req,
                        "Barbeiro excluído com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                        "Não foi possível excluir o barbeiro.");
            }

        } catch (ModelException | IllegalArgumentException e) {
            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Não foi possível excluir o barbeiro. "
                    + "Verifique se existem agendamentos relacionados.");
        }
    }

    private boolean validarHorario(Barber barber) {
        return barber.getStartTime().before(
                barber.getEndTime());
    }
}
