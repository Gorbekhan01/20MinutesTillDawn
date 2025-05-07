package src.com.mygdx.game.Controllers;

import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.User;

import java.util.regex.Pattern;

public class SignUpController {


    public String isUsernameTaken(String username) {
        for (User user : GameManager.getUsers()) {
            if (user.getUsername().equals(username)) {
                return "This username is already taken!";
            }
        }
        return null;
    }

    public String checkPasswordStrength(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters";
        }

        if (!Pattern.compile("[@#$%&*)(_]+").matcher(password).find()
            || !Pattern.compile("[0-9]+").matcher(password).find()
            || !Pattern.compile("[a-z]+").matcher(password).find()
            || !Pattern.compile("[A-Z]+").matcher(password).find()) {
            return "Entered password isn't strong enough";
        }
        return null;
    }


}
