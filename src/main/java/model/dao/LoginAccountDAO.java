
package model.dao;

import model.LoginAccount;
import model.ModelException;

public interface LoginAccountDAO {

    boolean save(LoginAccount account) throws ModelException;

    LoginAccount findByUsername(String username)
            throws ModelException;

}
