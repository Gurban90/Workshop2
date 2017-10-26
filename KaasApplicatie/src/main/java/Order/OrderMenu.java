package Order;

import Order.OrderController;
import DatabaseConnector.DomXML;
import Helper.DaoFactory;
import Helper.HelpClientOrderCheese;
import Helper.IDCheck;
import Helper.Validator;
import MainMenu.MenuController;
import Order.OrderPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMenu {

    Logger logger = Logger.getLogger(OrderMenu.class.getName());

    private Scanner input;
    private boolean makeOrderDetail;
    private int choice;
    private String choiceNumber;
    private String anwser;
    private String outputString;
    private String clientID;
    private int clientIDint;
    private String orderID;
    private int orderIDint;
    private String orderDetailID;
    private int orderDetailIDint;
    private String cheeseAmmount;
    private int cheeseAmmountint;
    private String cheeseID;
    private int cheeseIDint;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String min;
    private int yearint;
    private int monthint;
    private int dayint;
    private int hourint;
    private int minint;
    private LocalDateTime returnedLocalDateTime;

    @Autowired
    private HelpClientOrderCheese collection;
    @Autowired
    private OrderController orderController;
    @Autowired
    private Validator validator;
    @Autowired
    private MenuController menu;
    @Autowired
    private IDCheck idCheck;

    public void orderMenu() {

        input = new Scanner(System.in);

        System.out.print(" Order menu: " + "\n"
                + "1. New Order" + "\n"
                + "2. New OrderDetail" + "\n"
                + "3. Remove Order" + "\n"
                + "4. Remove OrderDetail" + "\n"
                + "5. Edit OrderMenu" + "\n"
                + "6. Edit OrderDetailMenu" + "\n"
                + "7. Search Order" + "\n"
                + "8. Get All Orders" + "\n"
                + "9. Search orderDetail" + "\n"
                + "10. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    newOrder();
                    break;
                case 2:
                    newOrderDetail();
                    break;
                case 3:
                    removerOrder();
                    break;
                case 4:
                    removeOrderDetail();
                    break;
                case 5:
                    editOrderMenu();
                    break;
                case 6:
                    editOrderDetailMenu();
                    break;
                case 7:
                    searchOrder();
                    break;
                case 8:
                    logger.info("searchallorder start");
                    orderController.getAllOrders();
                    orderMenu();
                    logger.info("searchallorder end");
                    break;
                case 9:
                    searchOrderDetail();
                    break;
                case 10:
                    logger.info("Open mainMenu");
                    menu.goToMain();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    orderMenu();

            }
            logger.info("OrderMenu end");
        } else {
            System.out.println("Choice must be an integer.");
            orderMenu();
        }
    }

    public void editOrderMenu() {
        logger.info("editOrdereMenu start");
        System.out.print(" What do you want to edit? " + "\n"
                + "1. Edit Original order time" + "\n"
                + "2. Edit Delivery Date" + "\n"
                + "3. Return to order menu " + "\n"
                + "Please enter the number of your choice: ");

        choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    editOriginalOrderTime();
                    break;
                case 2:
                    editDeliveryDate();
                    break;
                case 3:
                    orderMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    editOrderMenu();
            }
        } else {
            System.out.println("Choice must be an integer.");
            orderMenu();
        }
    }

    private void editOrderDetailMenu() {

        logger.info("editOrdereMenu start");
        System.out.print(" What do you want to edit? " + "\n"
                + "1. Edit which Cheese you want" + "\n"
                + "2. Edit order Quantity of cheese" + "\n"
                + "3. Return to order menu " + "\n"
                + "Please enter the number of your choice: ");

        choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    editCheeseInOrderDetail();
                    break;
                case 2:
                    editCheeseQuantityInOrderDetail();
                    break;
                case 3:
                    orderMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    editOrderDetailMenu();
            }
        } else {
            System.out.println("Choice must be an integer. ");
            editOrderDetailMenu();
        }
    }

    private void newOrder() {
        logger.info("newOrder start");
        System.out.println("Fill in a new order: ");
        System.out.print("Please select the ClientID of the client that made the order:  ");
        clientID = input.nextLine();
        if (validator.idValidator(this.clientID)) {
            this.clientIDint = Integer.parseInt(this.clientID);
                        Boolean boo = idCheck.checkClientID(clientIDint);
            if (boo) {
                System.out.println("No client with this id has been found");
                orderMenu();
            }
        } else {
            System.out.println("ClientID must have a value. ");
            orderMenu();
        }

        System.out.println("Set the time of day when the order was made by the client: ");
        System.out.print("Enter Year: ");
        this.year = input.nextLine();
        if (validator.yearValidator(this.year)) {
            this.yearint = Integer.parseInt(this.year);
        } else {
            System.out.println("Year must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter month: ");
        this.month = input.nextLine();
        if (validator.monthValidator(this.month)) {
            this.monthint = Integer.parseInt(this.month);
        } else {
            System.out.println("Month must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter day: ");
        this.day = input.nextLine();
        if (validator.dayValidator(this.day)) {
            this.dayint = Integer.parseInt(this.day);
        } else {
            System.out.println("Day must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter hour: ");
        this.hour = input.nextLine();
        if (validator.hourValidator(this.hour)) {
            this.hourint = Integer.parseInt(this.hour);
        } else {
            System.out.println("Hour must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter minute(s): ");
        this.min = input.nextLine();
        if (validator.minValidator(this.min)) {
            this.minint = Integer.parseInt(this.min);
        } else {
            System.out.println("Minute(s) must have a correct value.");
            orderMenu();
        }

        LocalDateTime orderDate = collection.setNewOrderByClient(yearint, monthint, dayint, hourint, minint);

        System.out.println("Set the time of day when the order will be delivered to the client: ");
        System.out.print("Enter Year: ");
        this.year = input.nextLine();
        if (validator.yearValidator(this.year)) {
            this.yearint = Integer.parseInt(this.year);
        } else {
            System.out.println("Year must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter month: ");
        this.month = input.nextLine();
        if (validator.monthValidator(this.month)) {
            this.monthint = Integer.parseInt(this.month);
        } else {
            System.out.println("Month must have a value.");
            orderMenu();
        }

        System.out.print("Enter day: ");
        this.day = input.nextLine();
        if (validator.dayValidator(this.day)) {
            this.dayint = Integer.parseInt(this.day);
        } else {
            System.out.println("Day must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter hour: ");
        this.hour = input.nextLine();
        if (validator.hourValidator(this.hour)) {
            this.hourint = Integer.parseInt(this.hour);
        } else {
            System.out.println("Hour must have a correct value.");
            orderMenu();
        }

        System.out.print("Enter minute(s): ");
        this.min = input.nextLine();
        if (validator.minValidator(this.min)) {
            this.minint = Integer.parseInt(this.min);
        } else {
            System.out.println("Minute(s) must have a correct value.");
            orderMenu();
        }

        LocalDateTime deliveryDate = collection.setOrderDelivery(yearint, monthint, dayint, hourint, minint);
        int id = orderController.addOrder(orderDate, new BigDecimal(0), deliveryDate, clientIDint);
        collection.setOrderID(id);

        makeOrderDetail = true;
        while (makeOrderDetail) {

            System.out.print("Select cheeseID for OrderDetail: ");
            cheeseID = input.nextLine();
            if (validator.idValidator(this.cheeseID)) {
                this.cheeseIDint = Integer.parseInt(this.cheeseID);
            } else {
                System.out.println("CheeseID must have a value. ");
                orderMenu();
            }

            System.out.print("Enter quantity of cheese: ");
            cheeseAmmount = input.nextLine();
            if (validator.stockValidator(this.cheeseAmmount)) {
                this.cheeseAmmountint = Integer.parseInt(this.cheeseAmmount);
            } else {
                System.out.println("Quantity must have a value. ");
                orderMenu();
            }
            
            
            collection.setOrderDetail(cheeseIDint, cheeseAmmountint);
            collection.getSingleCheesePrice();
            collection.getOrderDetail();
            collection.addUpCheese();

            String y = collection.adjustStock();
            System.out.print(y);

            Boolean next = true;
            while (next) {
                System.out.println("Do you want to add a new order detail?");
                anwser = input.nextLine();

                if (validator.yesValidator(anwser)) {
                    next = false;
                    break;
                }
                if (validator.noValidator(anwser)) {
                    next = false;
                    makeOrderDetail = false;
                    collection.saveTotalPrice();
                } else {
                    System.out.println("Please enter Yes or No.");
                }
            }
        }

        orderMenu();
        logger.info("newOrder end");

    }

    private void newOrderDetail() {
        logger.info("new OrderDetail start");
        System.out.println("Input orderID for adding new orderdetail to order: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value.");
            orderMenu();
        }

        makeOrderDetail = true;

        while (makeOrderDetail) {
            System.out.println("adding an orderdetail");

            System.out.println("Select cheeseID for OrderDetail: ");
            cheeseID = input.nextLine();
            if (validator.idValidator(this.cheeseID)) {
                this.cheeseIDint = Integer.parseInt(this.cheeseID);
            } else {
                System.out.println("CheeseID must have a value. ");
                orderMenu();
            }

            System.out.println("Enter quantity of cheese: ");
            cheeseAmmount = input.nextLine();
            if (validator.stockValidator(this.cheeseAmmount)) {
                this.cheeseAmmountint = Integer.parseInt(this.cheeseAmmount);
            } else {
                System.out.println("Quantity must have a value. ");
                orderMenu();
            }

            collection.setOrderDetail(cheeseIDint, cheeseAmmountint);
            collection.setOrderID(orderIDint);
            collection.getSingleCheesePrice();
            collection.getOrderDetail();
            collection.addUpCheese();

            String y = collection.adjustStock();
            System.out.print(y);

            Boolean next = true;
            while (next) {
                System.out.println("Do you want to add a new order detail?");
                anwser = input.nextLine();

                if (validator.yesValidator(anwser)) {
                    next = false;
                    break;
                }
                if (validator.noValidator(anwser)) {
                    next = false;
                    makeOrderDetail = false;
                    collection.saveTotalPrice();
                } else {
                    System.out.println("Please enter Yes or No.");
                }
            }
        }
        orderMenu();
        logger.info("new OrderDetail end");
    }

    private void removerOrder() {
        logger.info("removeorder start");
        System.out.print("Enter The OrderID you want to remove: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value. ");
            orderMenu();
        }

        outputString = orderController.removeOrder(this.orderIDint);

        System.out.println(outputString);

        orderMenu();
        logger.info("removeorder end");
    }

    private void removeOrderDetail() {
        logger.info("removeorderdetail start");
        System.out.print("Enter The OrderID from which you want to remove an OrderDetail: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value. ");
            orderMenu();
        }

        System.out.print("Enter The OrderDetailID you want to remove: ");
        orderDetailID = input.nextLine();
        if (validator.idValidator(this.orderDetailID)) {
            this.orderDetailIDint = Integer.parseInt(this.orderDetailID);
        } else {
            System.out.println("OrderDetailID must have a value.");
            orderMenu();
        }

        collection.minusCheese(orderDetailIDint, orderIDint, false);
        outputString = orderController.removeOrderDetail(orderDetailIDint);
        System.out.println(outputString);

        orderMenu();
        logger.info("removeorderdetail end");
    }

    private void searchOrder() {
        logger.info("searchorder start");
        System.out.print("Enter the OrderID of the order you want to search: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value.");
            orderMenu();
        }
        orderController.searchOrder(orderIDint);
        orderMenu();
        logger.info("searchorder end");
    }

    private void searchOrderDetail() {
        logger.info("searchorderdetail start");
        System.out.print("Enter the OrderID of the OrderDetail you want to search: ");
        orderDetailID = input.nextLine();
        if (validator.idValidator(this.orderDetailID)) {
            this.orderDetailIDint = Integer.parseInt(this.orderDetailID);
        } else {
            System.out.println("OrderDetail must have a value. ");
            editOrderMenu();
        }
        orderController.searchOrderDetailWithOrder(orderDetailIDint);
        orderMenu();
        logger.info("searchorderdetail end");
    }

    private void editOriginalOrderTime() {
        System.out.print("Edit order: ");
        System.out.println("Please select the OrderID from the order you want to edit:  ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value.");
            editOrderDetailMenu();
        }
        collection.setOrderID(orderIDint);

        System.out.println("Set the time of day when the order was made by the client. ");
        System.out.print("Enter Year: ");
        this.year = input.nextLine();
        if (validator.yearValidator(this.year)) {
            this.yearint = Integer.parseInt(this.year);
        } else {
            System.out.println("Year must have a correct value.");
            editOrderMenu();
        }

        System.out.print("Enter month: ");
        this.month = input.nextLine();
        if (validator.monthValidator(this.month)) {
            this.monthint = Integer.parseInt(this.month);
        } else {
            System.out.println("Month must have a correct value.");
            editOrderMenu();
        }

        System.out.print("Enter day: ");
        this.day = input.nextLine();
        if (validator.dayValidator(this.day)) {
            this.dayint = Integer.parseInt(this.day);
        } else {
            System.out.println("Day must have a correct value.");
            editOrderMenu();
        }

        System.out.print("Enter hour: ");
        this.hour = input.nextLine();
        if (validator.hourValidator(this.hour)) {
            this.hourint = Integer.parseInt(this.hour);
        } else {
            System.out.println("Hour must have a correct value.");
            editOrderMenu();
        }

        System.out.print("Enter minute(s): ");
        this.min = input.nextLine();
        if (validator.minValidator(this.min)) {
            this.minint = Integer.parseInt(this.min);
        } else {
            System.out.println("Minute(s) must have a correct value.");
            editOrderMenu();
        }

        this.returnedLocalDateTime = collection.setNewOrderByClient(yearint, monthint, dayint, hourint, minint);
        orderController.editOrderTime(orderIDint, returnedLocalDateTime);

        editOrderMenu();
    }

    private void editDeliveryDate() {
        System.out.print("Edit order: ");
        System.out.println("Please select the OrderID from the order you want to change: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value.");
            editOrderDetailMenu();
        }
        collection.setOrderID(orderIDint);

        System.out.println("Set the time of day when the order will be delivered to the client: ");
        System.out.print("Enter Year: ");
        this.year = input.nextLine();
        if (validator.yearValidator(this.year)) {
            this.yearint = Integer.parseInt(this.year);
        } else {
            System.out.println("Year must have a correct value.");
            editOrderMenu();
        }

        System.out.print("Enter month: ");
        this.month = input.nextLine();
        if (validator.monthValidator(this.month)) {
            this.monthint = Integer.parseInt(this.month);
        } else {
            System.out.println("Month must have a correct value. ");
            editOrderMenu();
        }

        System.out.print("Enter day: ");
        this.day = input.nextLine();
        if (validator.dayValidator(this.day)) {
            this.dayint = Integer.parseInt(this.day);
        } else {
            System.out.println("Day must have a correct value. ");
            editOrderMenu();
        }

        System.out.println("Enter hour: ");
        this.hour = input.nextLine();
        if (validator.hourValidator(this.hour)) {
            this.hourint = Integer.parseInt(this.hour);
        } else {
            System.out.println("Hour must have a correct value. ");
            editOrderMenu();
        }

        System.out.println("Enter minute(s): ");
        this.min = input.nextLine();
        if (validator.minValidator(this.min)) {
            this.minint = Integer.parseInt(this.min);
        } else {
            System.out.println("Minute(s) must have a correct value.");
            editOrderMenu();
        }

        this.returnedLocalDateTime = collection.setOrderDelivery(yearint, monthint, dayint, hourint, minint);

        orderController.editOrderDeliverTime(orderIDint, returnedLocalDateTime);

        editOrderMenu();
    }

    private void editCheeseInOrderDetail() {
        System.out.println("Edit orderDetail Cheese. ");
        System.out.println("Enter The OrderID from which you want to edit an OrderDetail: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value. ");
            orderMenu();
        }

        System.out.println("Please select the OrderDetailID from the order you want to edit:  ");
        orderDetailID = input.nextLine();
        if (validator.idValidator(this.orderDetailID)) {
            this.orderDetailIDint = Integer.parseInt(this.orderDetailID);
        } else {
            System.out.println("OrderDetailID must have a value. ");
            editOrderDetailMenu();
        }

        collection.minusCheese(orderDetailIDint, orderIDint, false);
        System.out.println("Enter the new CheeseID: ");
        cheeseID = input.nextLine();
        if (validator.idValidator(this.cheeseID)) {
            this.cheeseIDint = Integer.parseInt(this.cheeseID);
        } else {
            System.out.println("CheeseID must have a value.");
            editOrderDetailMenu();
        }
        outputString = orderController.editOrderDetailCheese(orderDetailIDint, cheeseIDint);
        collection.minusCheese(orderDetailIDint, orderIDint, true);
        System.out.print(outputString);

        editOrderDetailMenu();
    }

    private void editCheeseQuantityInOrderDetail() {
        System.out.print("Edit orderDetail Cheese Ammount: ");
        System.out.println("Enter The OrderID from which you want to edit an OrderDetail: ");
        orderID = input.nextLine();
        if (validator.idValidator(this.orderID)) {
            this.orderIDint = Integer.parseInt(this.orderID);
        } else {
            System.out.println("OrderID must have a value. ");
            orderMenu();
        }

        System.out.println("Please select the OrderDetailID from the order you want to change:  ");
        orderDetailID = input.nextLine();
        if (validator.idValidator(this.orderDetailID)) {
            this.orderDetailIDint = Integer.parseInt(this.orderDetailID);
        } else {
            System.out.println("OrderDetailID must have a value. ");
            editOrderDetailMenu();
        }

        collection.minusCheese(orderDetailIDint, orderIDint, false);
        System.out.println("Enter the new quantity of cheese: ");
        cheeseAmmount = input.nextLine();
        if (validator.stockValidator(this.cheeseAmmount)) {
            this.cheeseAmmountint = Integer.parseInt(this.cheeseAmmount);
        } else {
            System.out.println("Quantity must have a value.");
            editOrderDetailMenu();
        }
        outputString = orderController.editOrderDetailAmmount(orderDetailIDint, cheeseAmmountint);
        collection.minusCheese(orderDetailIDint, orderIDint, true);
        System.out.print(outputString);

        editOrderDetailMenu();
    }

}
