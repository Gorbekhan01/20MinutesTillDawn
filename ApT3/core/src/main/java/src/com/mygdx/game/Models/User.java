package src.com.mygdx.game.Models;

public class User {
    private String username;
    private Player player;
    private String password;
    private String answer;
    private String securityQNumber;
    private String securityAnswer;
    private int avatarNumber;
    private int score = 0;
    private int kills = 0;
    private int thisGameKills = 0;
    private int thisGameScore = 0;
    private int mostTimeSurvived = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSecurityQNumber() {
        return securityQNumber;
    }

    public void setSecurityQNumber(String securityQNumber) {
        this.securityQNumber = securityQNumber;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public int getAvatarNumber() {
        return avatarNumber;
    }

    public void setAvatarNumber(int avatarNumber) {
        this.avatarNumber = avatarNumber;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        player = new Player();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getThisGameKills() {
        return thisGameKills;
    }

    public void setThisGameKills(int thisGameKills) {
        this.thisGameKills = thisGameKills;
    }

    public int getThisGameScore() {
        return thisGameScore;
    }

    public void setThisGameScore(int thisGameScore) {
        this.thisGameScore = thisGameScore;
    }

    public int getMostTimeSurvived() {
        return mostTimeSurvived;
    }

    public Player getPlayer() {
        return player;
    }




}
