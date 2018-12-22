package se115.master;

public class Main {

    public static void main (String[] args) {

        WelcomePanel welcomePanel = new WelcomePanel();
        if(welcomePanel.isPreviousUser()) { // if there is user accessing before, show it
            RegisteredUsers registeredUsers = new RegisteredUsers();
            registeredUsers.build();
        } else { // if first access, open welcome panel
            welcomePanel.getWelcomeFrame();
        }
    }
}
