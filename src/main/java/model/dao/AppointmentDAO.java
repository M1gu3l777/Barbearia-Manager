
package model.dao;

import java.util.List;

import model.ModelException;
import model.Appointment;

public interface AppointmentDAO {

    boolean save(Appointment appointment) throws ModelException;
    boolean update(Appointment appointment) throws ModelException;
    boolean delete(Appointment appointment) throws ModelException;
    List<Appointment> listAll() throws ModelException;
    Appointment findById(int id) throws ModelException;

}
