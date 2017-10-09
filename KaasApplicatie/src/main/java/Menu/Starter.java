/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class Starter {

    /*
    public static void main (String [] args) {
        ClientMenu menu = new ClientMenu();
        menu.clientMenu();
     */
 /*public static void main (String [] args) {
        LoginMenu menu = new LoginMenu();
        menu.loginMenu();
    }
     */
 /*public static void main (String [] args) {
        MainMenu menu = new MainMenu();
        menu.mainMenu();
    }
    
  /*  public static void main(String [] args){
        OrderMenu menu = new OrderMenu();
        menu.orderMenu();
     }
     */
    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        ConnectionMenu menu = new ConnectionMenu();
        menu.connectionMenu();
    }

}
