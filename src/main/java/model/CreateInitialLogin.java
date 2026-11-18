
package model;

import java.util.Scanner;

import model.dao.DAOFactory;
import model.dao.LoginAccountDAO;
import model.dao.UserDAO;

public class CreateInitialLogin {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            UserDAO userDAO =
                    DAOFactory.createDAO(UserDAO.class);

            LoginAccountDAO loginDAO =
                    DAOFactory.createDAO(LoginAccountDAO.class);

            System.out.print("ID de um usuario existente: ");
            int userId = Integer.parseInt(scanner.nextLine());

            User user = userDAO.findById(userId);

            if (user == null) {
                System.out.println("Usuario nao encontrado.");
                return;
            }

            System.out.print("Novo nome de login: ");
            String username = scanner.nextLine().trim();

            if (username.isBlank()) {
                System.out.println("Login nao pode ficar vazio.");
                return;
            }

            if (loginDAO.findByUsername(username) != null) {
                System.out.println("Login ja cadastrado.");
                return;
            }

            System.out.print("Nova senha (minimo 8 caracteres): ");
            String password = scanner.nextLine();

            if (password.length() < 8) {
                System.out.println("Senha muito curta.");
                return;
            }

            LoginAccount account = new LoginAccount();

            account.setUsername(username);
            account.setPasswordHash(
                    PasswordUtil.hashPassword(password));
            account.setActive(true);
            account.setUser(user);

            if (loginDAO.save(account)) {
                System.out.println("Conta criada com sucesso!");
            } else {
                System.out.println("Nao foi possivel criar a conta.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar conta.");
            e.printStackTrace();
        }
    }
}
