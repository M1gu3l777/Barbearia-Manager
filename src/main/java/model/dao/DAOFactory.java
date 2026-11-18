package model.dao;

import java.util.HashMap;
import java.util.Map;

public class DAOFactory {

    private static Map<Class<?>, Object> listDAOsInterfaces =
            new HashMap<Class<?>, Object>();

    static {
        listDAOsInterfaces.put(PostDAO.class, new MySQLPostDAO());
        listDAOsInterfaces.put(UserDAO.class, new MySQLUserDAO());
        listDAOsInterfaces.put(CompanyDAO.class, new MySQLCompanyDAO());

        // Novos DAOs da Barbearia
        listDAOsInterfaces.put(BarberDAO.class, new MySQLBarberDAO());
        listDAOsInterfaces.put(AppointmentDAO.class,
                new MySQLAppointmentDAO());
        
        listDAOsInterfaces.put(
                LoginAccountDAO.class,
                new MySQLLoginAccountDAO()
        );
    }

    @SuppressWarnings("unchecked")
    public static <DAOInterface> DAOInterface createDAO(Class<?> entity) {
        return (DAOInterface) listDAOsInterfaces.get(entity);
    }
    
}

