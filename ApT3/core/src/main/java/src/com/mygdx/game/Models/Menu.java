package src.com.mygdx.game.Models;

import com.badlogic.gdx.Screen;
import src.com.mygdx.game.Views.*;

public enum Menu {
    LOGIN_MENU(LoginMenu::new),
    SIGNUP_MENU(SignUpMenu::new),
    MAIN_MENU(MainMenu::new),
    PROFILE_MENU(ProfileMenu::new),
    PREGAME_MENU(PreGameMenu::new),
    SCOREBOARD_MENU(ScoreBoardMenu::new),
    HINT_MENU(HintMenu::new),
    SETTING_MENU(SettingMenu::new),
    PAUSE_MENU(PauseMenu::new),
    MAIN_GAME_SCREEN(MainGameScreen::new),
    OPENING_SCREEN(OpeningScreen::new),
    GAME_OVER(GameOver::new),
    ABILITY_MENU(AbilityMenu::new);

    private final ScreenFactory screenFactory;

    Menu(ScreenFactory screenFactory) {
        this.screenFactory = screenFactory;
    }

    public Screen getScreen() {
        return screenFactory.create();
    }

    @FunctionalInterface
    private interface ScreenFactory {
        Screen create();
    }
}
