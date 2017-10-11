/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Gerben
 */
@Entity
@Table(name="Client")
public class ClientPOJO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int clientID;
    
    private String firstName;
    private String lastName;
    private String eMail;
    
    @OneToMany(mappedBy = "Address")
    List<AddressPOJO> addresses = new ArrayList<AddressPOJO>();
    

    public ClientPOJO() {
    }

    public ClientPOJO(Integer clientID, String firstName, String lastName, String eMail) {
        this.clientID = clientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }

    public List<AddressPOJO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressPOJO> addresses) {
        this.addresses = addresses;
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
