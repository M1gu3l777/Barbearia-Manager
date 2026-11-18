
package model.dao;

import java.util.List;

import model.ModelException;
import model.Barber;

public interface BarberDAO {

    boolean save(Barber barber) throws ModelException;
    boolean update(Barber barber) throws ModelException;
    boolean delete(Barber barber) throws ModelException;
    List<Barber> listAll() throws ModelException;
    Barber findById(int id) throws ModelException;

}
