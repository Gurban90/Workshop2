/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author Gerben
 */
//OPZET CLIENTPOJO
public class ClientPOJO {

    private int clientID;
    private String firstName;
    private String lastName;
    private String eMail;

    public ClientPOJO() {
    }

    public ClientPOJO(Integer clientID, String firstName, String lastName, String eMail) {
        this.clientID = clientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEMail() {
        return eMail;
    }

    @Override
    public String toString() {
        return "ClientID: " + clientID + ", First Name: " + firstName + ", Last Name: " + lastName + ", E-Mail: " + eMail;
    }
}
