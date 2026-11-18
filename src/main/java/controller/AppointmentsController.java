
package controller;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Appointment;
import model.Barber;
import model.ModelException;
import model.User;
import model.dao.AppointmentDAO;
import model.dao.BarberDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {
        "/appointments",
        "/appointment/form",
        "/appointment/insert",
        "/appointment/update",
        "/appointment/delete"
})
public class AppointmentsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {

        case "/appointment/form": {
            try {
                carregarListas(req);

                req.setAttribute("action", "insert");

                ControllerUtil.forward(req, resp,
                        "/form-appointment.jsp");

            } catch (ModelException e) {
                e.printStackTrace();

                ControllerUtil.errorMessage(req,
                        "Erro ao carregar clientes e barbeiros.");

                ControllerUtil.redirect(resp,
                        req.getContextPath() + "/appointments");
            }
            break;
        }

        case "/appointment/update": {
            try {
                int id = Integer.parseInt(
                        req.getParameter("appointmentId"));

                AppointmentDAO dao =
                        DAOFactory.createDAO(AppointmentDAO.class);

                Appointment appointment = dao.findById(id);

                if (appointment == null) {
                    ControllerUtil.errorMessage(req,
                            "Agendamento não encontrado.");

                    ControllerUtil.redirect(resp,
                            req.getContextPath() + "/appointments");
                    return;
                }

                carregarListas(req);

                req.setAttribute("action", "update");
                req.setAttribute("appointment", appointment);

                ControllerUtil.forward(req, resp,
                        "/form-appointment.jsp");

            } catch (ModelException | IllegalArgumentException e) {
                e.printStackTrace();

                ControllerUtil.errorMessage(req,
                        "Erro ao carregar agendamento.");

                ControllerUtil.redirect(resp,
                        req.getContextPath() + "/appointments");
            }
            break;
        }

        default: {
            listAppointments(req);

            ControllerUtil.transferSessionMessagesToRequest(req);

            ControllerUtil.forward(req, resp,
                    "/appointments.jsp");
            break;
        }
        }
    }

    private void carregarListas(HttpServletRequest req)
            throws ModelException {

        CommonsController.listUsers(req);

        BarberDAO barberDAO =
                DAOFactory.createDAO(BarberDAO.class);

        req.setAttribute("barbers", barberDAO.listAll());
    }

    private void listAppointments(HttpServletRequest req) {

        AppointmentDAO dao =
                DAOFactory.createDAO(AppointmentDAO.class);

        try {
            List<Appointment> appointments = dao.listAll();

            // Paginação: 5 agendamentos por página
            int tamanhoPagina = 5;
            int totalRegistros = appointments.size();

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

            if (paginaAtual < 1) {
                paginaAtual = 1;
            }

            if (paginaAtual > totalPaginas) {
                paginaAtual = totalPaginas;
            }

            int inicio = (paginaAtual - 1) * tamanhoPagina;

            int fim = Math.min(
                    inicio + tamanhoPagina, totalRegistros);

            req.setAttribute("appointments",
                    appointments.subList(inicio, fim));

            req.setAttribute("currentPage", paginaAtual);
            req.setAttribute("totalPages", totalPaginas);

        } catch (ModelException e) {
            e.printStackTrace();

            req.setAttribute("message",
                    "Erro ao listar agendamentos.");
            req.setAttribute("alertType", 0);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {

        case "/appointment/insert":
            salvarAgendamento(req, false);
            break;

        case "/appointment/update":
            salvarAgendamento(req, true);
            break;

        case "/appointment/delete":
            deleteAppointment(req);
            break;

        default:
            ControllerUtil.errorMessage(req,
                    "Ação inválida.");
        }

        ControllerUtil.redirect(resp,
                req.getContextPath() + "/appointments");
    }

    private void salvarAgendamento(HttpServletRequest req,
            boolean atualizacao) {

        try {
            Appointment appointment;

            if (atualizacao) {
                int id = Integer.parseInt(
                        req.getParameter("appointmentId"));

                appointment = new Appointment(id);
            } else {
                appointment = new Appointment();
            }

            int userId = Integer.parseInt(
                    req.getParameter("user"));

            int barberId = Integer.parseInt(
                    req.getParameter("barber"));

            appointment.setUser(new User(userId));
            appointment.setBarber(new Barber(barberId));

            appointment.setService(
                    req.getParameter("service"));

            appointment.setAppointmentDate(
                    java.sql.Date.valueOf(
                            req.getParameter("appointmentDate")));

            String horario =
                    req.getParameter("appointmentTime");

            if (horario.length() == 5) {
                horario += ":00";
            }

            appointment.setAppointmentTime(
                    Time.valueOf(horario));

            appointment.setStatus(
                    req.getParameter("status"));

            appointment.setNotes(
                    req.getParameter("notes"));

            if (appointment.getService() == null
                    || appointment.getService().isBlank()
                    || appointment.getStatus() == null
                    || appointment.getStatus().isBlank()) {

                ControllerUtil.errorMessage(req,
                        "Preencha os campos obrigatórios.");
                return;
            }

            if (!validarDisponibilidade(appointment)) {
                ControllerUtil.errorMessage(req,
                        "Horário indisponível para este barbeiro.");
                return;
            }

            AppointmentDAO dao =
                    DAOFactory.createDAO(AppointmentDAO.class);

            boolean sucesso;

            if (atualizacao) {
                sucesso = dao.update(appointment);
            } else {
                sucesso = dao.save(appointment);
            }

            if (sucesso) {
                ControllerUtil.sucessMessage(req,
                        atualizacao
                        ? "Agendamento atualizado com sucesso."
                        : "Agendamento cadastrado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                        "Não foi possível salvar o agendamento.");
            }

        } catch (ModelException | IllegalArgumentException
                | NullPointerException e) {

            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Erro ao salvar agendamento. "
                    + "Verifique os dados informados.");
        }
    }

    private boolean validarDisponibilidade(
            Appointment appointment) throws ModelException {

        BarberDAO barberDAO =
                DAOFactory.createDAO(BarberDAO.class);

        Barber barber = barberDAO.findById(
                appointment.getBarber().getId());

        if (barber == null || !barber.isActive()) {
            return false;
        }

        Time horario = appointment.getAppointmentTime();

        // Confere o expediente do barbeiro
        if (horario.before(barber.getStartTime())
                || !horario.before(barber.getEndTime())) {
            return false;
        }

        // Agendamentos cancelados não ocupam horário
        if ("Cancelado".equals(appointment.getStatus())) {
            return true;
        }

        AppointmentDAO dao =
                DAOFactory.createDAO(AppointmentDAO.class);

        List<Appointment> agendamentos = dao.listAll();

        for (Appointment existente : agendamentos) {

            // Ignora o próprio registro na edição
            if (existente.getId() == appointment.getId()
                    && appointment.getId() != 0) {
                continue;
            }

            // Ignora agendamentos cancelados
            if ("Cancelado".equals(existente.getStatus())) {
                continue;
            }

            boolean mesmoBarbeiro =
                    existente.getBarber().getId()
                    == appointment.getBarber().getId();

            boolean mesmaData =
                    existente.getAppointmentDate().equals(
                            appointment.getAppointmentDate());

            boolean mesmoHorario =
                    existente.getAppointmentTime().equals(
                            appointment.getAppointmentTime());

            if (mesmoBarbeiro && mesmaData && mesmoHorario) {
                return false;
            }
        }

        return true;
    }

    private void deleteAppointment(HttpServletRequest req) {

        try {
            int id = Integer.parseInt(
                    req.getParameter("id"));

            AppointmentDAO dao =
                    DAOFactory.createDAO(AppointmentDAO.class);

            Appointment appointment = dao.findById(id);

            if (appointment == null) {
                ControllerUtil.errorMessage(req,
                        "Agendamento não encontrado.");
                return;
            }

            if (dao.delete(appointment)) {
                ControllerUtil.sucessMessage(req,
                        "Agendamento excluído com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                        "Não foi possível excluir o agendamento.");
            }

        } catch (ModelException | IllegalArgumentException e) {
            e.printStackTrace();

            ControllerUtil.errorMessage(req,
                    "Erro ao excluir agendamento.");
        }
    }
}
