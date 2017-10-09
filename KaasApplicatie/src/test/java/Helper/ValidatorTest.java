/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gerben
 */
public class ValidatorTest {

    public ValidatorTest() {
    }

    @Test
    public void testMenuValidator() {
        String number = "1";
        Validator instance = new Validator();
        boolean expResult = true;
        boolean result = instance.menuValidator(number);
        assertEquals(expResult, result);
    }

    @Test
    public void testIdValidator() {
        String id = "g";
        Validator instance = new Validator();
        boolean expResult = false;
        boolean result = instance.idValidator(id);
        assertEquals(expResult, result);

    }

    @Test
    public void testStringValidator() {
        String name = "";
        Validator instance = new Validator();
        boolean expResult = false;
        boolean result = instance.stringValidator(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testPriceValidator() {
        String price = "12.200";
        Validator instance = new Validator();
        boolean expResult = false;
        boolean result = instance.priceValidator(price);
        assertEquals(expResult, result);
    }

    @Test
    public void testStockValidator() {
        String stock = "3";
        Validator instance = new Validator();
        boolean expResult = true;
        boolean result = instance.stockValidator(stock);
        assertEquals(expResult, result);
    }

    @Test
    public void testStatusValidator() {
        String status = "";
        Validator instance = new Validator();
        boolean expResult = false;
        boolean result = instance.statusValidator(status);
        assertEquals(expResult, result);
    }

    @Test
    public void testEMailValidator() {
        String eMail = "gerben@gmail.com";
        Validator instance = new Validator();
        boolean expResult = true;
        boolean result = instance.eMailValidator(eMail);
        assertEquals(expResult, result);
    }

}
