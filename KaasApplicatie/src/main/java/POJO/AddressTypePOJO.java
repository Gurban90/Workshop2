package POJO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AddressType")
public class AddressTypePOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressTypeID;
    
    private String addressType;

    @OneToMany(mappedBy = "addresstype")
    private List<AddressPOJO> addresses = new ArrayList<AddressPOJO>();

    public AddressTypePOJO(){};

    public AddressTypePOJO(Integer addressTypeID, String addressType) {
        this.addressTypeID = addressTypeID;
        this.addressType = addressType;
    }

    public List<AddressPOJO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressPOJO> addresses) {
        this.addresses = addresses;
    }
    
    public int getAddressTypeID() {
        return addressTypeID;
    }

    public void setAddressTypeID(int addressTypeID) {
        this.addressTypeID = addressTypeID;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public String toString() {
        return "AddressTypeID: " + addressTypeID + ", AddressType: " + addressType;
    }
}
