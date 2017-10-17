package POJO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Address")
public class AddressPOJO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int addressID;
    
    @ManyToOne
    @JoinColumn(name="ClientID", referencedColumnName="clientID")
    private ClientPOJO client;
    
    @Transient
    private int clientIdentifier;
    
    @ManyToOne
    @JoinColumn(name="AddressTypeID", referencedColumnName="addressTypeID")
    private AddressTypePOJO addresstype;
    
    @Transient
    private int addressTypeIdentifier;
    
    private int housenumber;
    private String houseNumberAddition;
    private String streetname;
    private String postalCode;
    private String city;

    public AddressPOJO() {
    }

    public AddressPOJO(Integer addressID, String Streetname, Integer HouseNumber, String HouseNumberAddition,
            String PostalCode, String City, Integer ClientID, Integer AddressTypeID) {
        this.addressID = addressID;
        this.streetname = Streetname;
        this.housenumber = HouseNumber;
        this.houseNumberAddition = HouseNumberAddition;
        this.postalCode = PostalCode;
        this.city = City;
        this.clientIdentifier = ClientID;
        this.addressTypeIdentifier = AddressTypeID;
    }

    public AddressTypePOJO getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(AddressTypePOJO addresstype) {
        this.addresstype = addresstype;
    }

    public ClientPOJO getClient() {
        return client;
    }

    public void setClient(ClientPOJO client) {
        this.client = client;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getHouseNumber() {
        return housenumber;
    }

    public void setHouseNumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public void setHouseNumberAddition(String houseNumberAddition) {
        this.houseNumberAddition = houseNumberAddition;
    }

    public String getStreetName() {
        return streetname;
    }

    public void setStreetName(String streetname) {
        this.streetname = streetname;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(int ClientID) {
        this.clientIdentifier = ClientID;
    }

    public int getAddressTypeIdentifier() {
        return addressTypeIdentifier;
    }

    public void setAddressTypeIdentifier(int addressTypeIdentifier) {
        this.addressTypeIdentifier = addressTypeIdentifier;
    }

    @Override
    public String toString() {
        return "AddressID: " + addressID + ", Housenumber: " + housenumber + ", Housenumber Addition: " + houseNumberAddition + ", Streetname: " + streetname
                + ", Postal Code: " + postalCode + ", City: " + city + ", ClientID: " + client.getClientID() + ", AddressType: " + addresstype + "\n";
    }

}
