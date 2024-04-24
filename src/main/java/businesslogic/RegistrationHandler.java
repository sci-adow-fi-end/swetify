package businesslogic;

import dao.Dao;
import dao.UserDao;
import domainmodel.User;

public class RegistrationHandler extends Handler{


    public String userName;
    public String password;
    public Dao<User> userDatabase = new UserDao();

    @Override
    protected void pullData() {}

    @Override
    protected void render() {
        System.out.println("insert your desired name and password or press 1 to go back to the login page");
    }

    @Override
    protected void handleInput() {
    }
}
