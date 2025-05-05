package src.com.mygdx.game.Controllers;

import src.com.mygdx.game.Models.User;

public class LoginMenuController {

    public boolean checkPassword(User user, String password) {
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
