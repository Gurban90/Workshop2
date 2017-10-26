/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainMenu;

import Account.LoginMenu;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
@ComponentScan("Account")
public class Starter {

    @Autowired
    private LoginMenu menu;

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
        ApplicationContext context = new AnnotationConfigApplicationContext(Starter.class);
        Starter starter = context.getBean(Starter.class);
        LogManager.getLogManager().reset();

        starter.menu.loginMenu();
    }

}
