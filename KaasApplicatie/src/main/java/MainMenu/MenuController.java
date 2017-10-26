/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainMenu;

import Address.AddressMenu;
import Address.AddressTypeMenu;
import Cheese.CheeseMenu;
import Client.ClientMenu;
import Account.LoginMenu;
import Order.OrderMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
@ComponentScan({"Cheese", "Order", "Client", "Address", "AddressType"})
public class MenuController {

    @Autowired
    private MainMenu mainmenu;
    @Autowired
    private CheeseMenu cheesemenu;
    @Autowired
    private OrderMenu ordermenu;
    @Autowired
    private LoginMenu loginmenu;
    @Autowired
    private ClientMenu clientmenu;
    @Autowired
    private AddressMenu addressmenu;
    @Autowired
    private AddressTypeMenu addresstypemenu;

    public void goToMain() {
        mainmenu.mainMenu();
    }

    public void goToCheese() {
        cheesemenu.cheeseMenu();
    }

    public void goToOrder() {
        ordermenu.orderMenu();
    }

    public void goToLogin() {
        loginmenu.loginMenu();
    }

    public void goToClient() {
        clientmenu.clientMenu();
    }

    public void goToAddress() {
        addressmenu.addressMenu();
    }

    public void goToAddressType() {
        addresstypemenu.addressTypeMenu();
    }
}
