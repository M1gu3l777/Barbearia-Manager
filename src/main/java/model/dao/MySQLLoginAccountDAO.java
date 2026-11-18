
package model.dao;

import model.LoginAccount;
import model.ModelException;
import model.User;

public class MySQLLoginAccountDAO
        implements LoginAccountDAO {

    @Override
    public boolean save(LoginAccount account)
            throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "INSERT INTO login_accounts "
                + "(username, password_hash, active, user_id) "
                + "VALUES (?, ?, ?, ?);";

        db.prepareStatement(sql);

        db.setString(1, account.getUsername());
        db.setString(2, account.getPasswordHash());
        db.setInt(3, account.isActive() ? 1 : 0);
        db.setInt(4, account.getUser().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public LoginAccount findByUsername(String username)
            throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM login_accounts "
                + "WHERE username = ?;";

        db.prepareStatement(sql);
        db.setString(1, username);
        db.executeQuery();

        LoginAccount account = null;

        while (db.next()) {

            account = new LoginAccount(db.getInt("id"));

            account.setUsername(
                    db.getString("username"));

            account.setPasswordHash(
                    db.getString("password_hash"));

            account.setActive(db.getInt("active") == 1);

            User user = new User(db.getInt("user_id"));

            account.setUser(user);

            break;
        }

        return account;
    }
}
