
package model.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Barber;
import model.ModelException;
import model.User;

public class MySQLBarberDAO implements BarberDAO {

    @Override
    public boolean save(Barber barber) throws ModelException {
        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO barbers "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);

        db.setString(1, barber.getName());
        db.setString(2, barber.getPhone());
        db.setString(3, barber.getSpecialty());
        db.setString(4, barber.getStartTime().toString());
        db.setString(5, barber.getEndTime().toString());
        db.setInt(6, barber.isActive() ? 1 : 0);
        db.setInt(7, barber.getUser().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Barber barber) throws ModelException {
        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE barbers "
                + "SET name = ?, "
                + "phone = ?, "
                + "specialty = ?, "
                + "start_time = ?, "
                + "end_time = ?, "
                + "active = ?, "
                + "user_id = ? "
                + "WHERE id = ?;";

        db.prepareStatement(sqlUpdate);

        db.setString(1, barber.getName());
        db.setString(2, barber.getPhone());
        db.setString(3, barber.getSpecialty());
        db.setString(4, barber.getStartTime().toString());
        db.setString(5, barber.getEndTime().toString());
        db.setInt(6, barber.isActive() ? 1 : 0);
        db.setInt(7, barber.getUser().getId());
        db.setInt(8, barber.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Barber barber) throws ModelException {
        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM barbers WHERE id = ?;";

        db.prepareStatement(sqlDelete);
        db.setInt(1, barber.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Barber> listAll() throws ModelException {
        DBHandler db = new DBHandler();

        List<Barber> barbers = new ArrayList<Barber>();

        String sqlQuery = "SELECT b.id AS barber_id, "
                + "b.*, u.nome, u.sexo, u.email "
                + "FROM barbers b "
                + "INNER JOIN users u "
                + "ON b.user_id = u.id;";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            User user = new User(db.getInt("user_id"));
            user.setName(db.getString("nome"));
            user.setGender(db.getString("sexo"));
            user.setEmail(db.getString("email"));

            Barber barber = new Barber(db.getInt("barber_id"));
            barber.setName(db.getString("name"));
            barber.setPhone(db.getString("phone"));
            barber.setSpecialty(db.getString("specialty"));
            barber.setStartTime(
                    Time.valueOf(db.getString("start_time")));
            barber.setEndTime(
                    Time.valueOf(db.getString("end_time")));
            barber.setActive(db.getInt("active") == 1);
            barber.setUser(user);

            barbers.add(barber);
        }

        return barbers;
    }

    @Override
    public Barber findById(int id) throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM barbers WHERE id = ?;";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Barber barber = null;

        while (db.next()) {
            barber = new Barber(id);
            barber.setName(db.getString("name"));
            barber.setPhone(db.getString("phone"));
            barber.setSpecialty(db.getString("specialty"));
            barber.setStartTime(
                    Time.valueOf(db.getString("start_time")));
            barber.setEndTime(
                    Time.valueOf(db.getString("end_time")));
            barber.setActive(db.getInt("active") == 1);

            UserDAO userDAO =
                    DAOFactory.createDAO(UserDAO.class);

            User user = userDAO.findById(
                    db.getInt("user_id"));

            barber.setUser(user);
            break;
        }

        return barber;
    }
}
