package src.com.mygdx.game.Models;

import com.badlogic.gdx.Screen;
import src.com.mygdx.game.Views.*;

public enum Menu {
    LOGIN_MENU(new LoginMenu()),
    SIGNUP_MENU(new SignUpMenu()),
    MAIN_MENU(new MainMenu()),
    PROFILE_MENU(new ProfileMenu()),
    PREGAME_MENU(new PreGameMenu()),
    SCOREBOARD_MENU(new ScoreBoardMenu()),
    HINT_MENU(new HintMenu()),
    SETTING_MENU(new SettingMenu()),
    PAUSE_MENU(new PauseMenu()),
    MAIN_GAME_SCREEN(new MainGameScreen()),
    OPENING_SCREEN(new OpeningScreen()),
    GAME_OVER(new GameOver()),
    ABILITY_MENU(new AbilityMenu());

    private Screen screen;

    Menu(Screen menu) {
        this.screen = menu;
    }

    public Screen getScreen() {
        return screen;
    }

}
