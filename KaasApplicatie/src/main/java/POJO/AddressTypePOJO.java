package POJO;

public class AddressTypePOJO {

    private int addressTypeID;
    private String addressType;

    public AddressTypePOJO() {};

    public AddressTypePOJO(Integer addressTypeID, String addressType) {
        this.addressTypeID = addressTypeID;
        this.addressType = addressType;
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
