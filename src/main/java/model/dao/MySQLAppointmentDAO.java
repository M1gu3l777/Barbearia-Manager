
package model.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Appointment;
import model.Barber;
import model.ModelException;
import model.User;

public class MySQLAppointmentDAO implements AppointmentDAO {

    @Override
    public boolean save(Appointment appointment) throws ModelException {
        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO appointments "
                + "(user_id, barber_id, service, appointment_date, "
                + "appointment_time, status, notes) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);

        db.setInt(1, appointment.getUser().getId());
        db.setInt(2, appointment.getBarber().getId());
        db.setString(3, appointment.getService());
        db.setDate(4, appointment.getAppointmentDate());
        db.setString(5, appointment.getAppointmentTime().toString());
        db.setString(6, appointment.getStatus());
        db.setString(7, appointment.getNotes());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Appointment appointment) throws ModelException {
        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE appointments "
                + "SET user_id = ?, "
                + "barber_id = ?, "
                + "service = ?, "
                + "appointment_date = ?, "
                + "appointment_time = ?, "
                + "status = ?, "
                + "notes = ? "
                + "WHERE id = ?;";

        db.prepareStatement(sqlUpdate);

        db.setInt(1, appointment.getUser().getId());
        db.setInt(2, appointment.getBarber().getId());
        db.setString(3, appointment.getService());
        db.setDate(4, appointment.getAppointmentDate());
        db.setString(5, appointment.getAppointmentTime().toString());
        db.setString(6, appointment.getStatus());
        db.setString(7, appointment.getNotes());
        db.setInt(8, appointment.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Appointment appointment) throws ModelException {
        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM appointments "
                + "WHERE id = ?;";

        db.prepareStatement(sqlDelete);
        db.setInt(1, appointment.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Appointment> listAll() throws ModelException {
        DBHandler db = new DBHandler();

        List<Appointment> appointments = new ArrayList<Appointment>();

        String sqlQuery = "SELECT a.*, "
                + "u.nome AS client_name, "
                + "u.sexo AS client_gender, "
                + "u.email AS client_email, "
                + "b.name AS barber_name "
                + "FROM appointments a "
                + "INNER JOIN users u ON a.user_id = u.id "
                + "INNER JOIN barbers b ON a.barber_id = b.id;";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            User user = new User(db.getInt("user_id"));
            user.setName(db.getString("client_name"));
            user.setGender(db.getString("client_gender"));
            user.setEmail(db.getString("client_email"));

            Barber barber = new Barber(db.getInt("barber_id"));
            barber.setName(db.getString("barber_name"));

            Appointment appointment =
                    new Appointment(db.getInt("id"));

            appointment.setUser(user);
            appointment.setBarber(barber);
            appointment.setService(db.getString("service"));
            appointment.setAppointmentDate(
                    db.getDate("appointment_date"));
            appointment.setAppointmentTime(
                    Time.valueOf(db.getString("appointment_time")));
            appointment.setStatus(db.getString("status"));
            appointment.setNotes(db.getString("notes"));

            appointments.add(appointment);
        }

        return appointments;
    }

    @Override
    public Appointment findById(int id) throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM appointments WHERE id = ?;";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Appointment appointment = null;

        while (db.next()) {
            appointment = new Appointment(id);

            appointment.setService(db.getString("service"));
            appointment.setAppointmentDate(
                    db.getDate("appointment_date"));
            appointment.setAppointmentTime(
                    Time.valueOf(db.getString("appointment_time")));
            appointment.setStatus(db.getString("status"));
            appointment.setNotes(db.getString("notes"));

            int userId = db.getInt("user_id");
            int barberId = db.getInt("barber_id");

            UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
            User user = userDAO.findById(userId);

            BarberDAO barberDAO =
                    DAOFactory.createDAO(BarberDAO.class);
            Barber barber = barberDAO.findById(barberId);

            appointment.setUser(user);
            appointment.setBarber(barber);

            break;
        }

        return appointment;
    }
}
