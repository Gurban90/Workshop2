/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Menu.AddressMenu;
import Menu.AddressTypeMenu;
import Menu.CheeseMenu;
import Menu.ClientMenu;
import Menu.LoginMenu;
import Menu.MainMenu;
import Menu.OrderMenu;

/**
 *
 * @author Gerben
 */
public class MenuController {

    public void goToMain() {
        MainMenu mainmenu = new MainMenu();
        mainmenu.mainMenu();
    }

    public void goToCheese() {
        CheeseMenu cheesemenu = new CheeseMenu();
        cheesemenu.cheeseMenu();
    }

    public void goToOrder() {
        OrderMenu ordermenu = new OrderMenu();
        ordermenu.orderMenu();
    }

    public void goToLogin() {
        LoginMenu loginmenu = new LoginMenu();
        loginmenu.loginMenu();
    }

    public void goToClient() {
        ClientMenu clientmenu = new ClientMenu();
        clientmenu.clientMenu();
    }

    public void goToAddress() {
        AddressMenu addressmenu = new AddressMenu();
        addressmenu.addressMenu();
    }
    
     public void goToAddressType() {
        AddressTypeMenu addresstypemenu = new AddressTypeMenu();
        addresstypemenu.addressTypeMenu();
     }
}
