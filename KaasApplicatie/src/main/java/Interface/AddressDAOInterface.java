
package Interface;

import POJO.AddressPOJO;
import POJO.AddressTypePOJO;
import POJO.ClientPOJO;
import java.util.List;

public interface AddressDAOInterface {
    
    public Integer addAddress(AddressPOJO adress);
    
    public List<AddressPOJO> getAllAddress();
    
    public AddressPOJO getAddress(AddressPOJO address);
    
    public void updateAddress(AddressPOJO address);
    
    public void deleteAddress (AddressPOJO address);
    
    public Integer addAddressType(AddressTypePOJO addressType);
    
    public List<AddressPOJO> getAddressWithClient(ClientPOJO client);
    
    public List<AddressTypePOJO> getAllAddressType();
    
    public AddressTypePOJO getAddressType(AddressTypePOJO addressType);
    
    public void updateAddressType(AddressTypePOJO addressType);
    
    public void deleteAddressType (AddressTypePOJO addressType);
}