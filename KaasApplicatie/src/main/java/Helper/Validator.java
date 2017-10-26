/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.math.BigDecimal;
import org.apache.commons.validator.routines.IntegerValidator;
import static org.apache.commons.validator.GenericValidator.isBlankOrNull;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class Validator {

    private int intValidation;
    private BigDecimal bdValidation;

    public boolean isIntegerValidator(String number) {
        boolean parsable = true;
        try {
            this.intValidation = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            parsable = false;
        }
        return parsable;
    }

    public boolean menuValidator(String number) {
        boolean check = true;
        if (isBlankOrNull(number)) {
            check = false;
        } else {
            try {
                this.intValidation = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                check = false;
            }
        }
        return check;
    }

    public boolean idValidator(String id) {
        if (isIntegerValidator(id)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 1, 1000);
        } else {
            return false;
        }
    }

    public boolean stringValidator(String name) {
        boolean check = true;
        if (isBlankOrNull(name)) {
            check = false;
        }
        return check;
    }

    public boolean priceValidator(String price) {
        boolean check = true;
        int dot = price.indexOf('.');
        String decimals = price.substring(dot + 1);
        try {
            this.bdValidation = new BigDecimal(price);
        } catch (NumberFormatException e) {
            check = false;
        }
        if (check == true) {
            check = false;
            try {
                decimals.charAt(2);
            } catch (IndexOutOfBoundsException e) {
                check = true;
            }
        }
        if (check == true) {
            BigDecimalValidator bigDecimalValidator = new BigDecimalValidator();
            return bigDecimalValidator.isInRange(bdValidation, 0, 1000);
        } else {
            return false;
        }
    }

    public boolean stockValidator(String stock) {
        if (isIntegerValidator(stock)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 0, 1000);
        } else {
            return false;
        }
    }

    public boolean statusValidator(String status) {
        if (isIntegerValidator(status)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 0, 5);
        } else {
            return false;
        }
    }

    public boolean eMailValidator(String eMail) {
        boolean check = true;
        EmailValidator emailValid = EmailValidator.getInstance(true);
        if (isBlankOrNull(eMail)) {
            check = false;
        }
        if (check == true) {
            return emailValid.isValid(eMail);
        } else {
            return false;
        }
    }

    public boolean yearValidator(String year) {
        if (isIntegerValidator(year)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 1990, 3000) || validator.isInRange(intValidation, 00, 99);
        } else {
            return false;
        }
    }

    public boolean monthValidator(String month) {
        if (isIntegerValidator(month)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 1, 12);
        } else {
            return false;
        }
    }

    public boolean dayValidator(String day) {
        if (isIntegerValidator(day)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 1, 366);
        } else {
            return false;
        }
    }

    public boolean hourValidator(String hour) {
        if (isIntegerValidator(hour)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 1, 24);
        } else {
            return false;
        }
    }

    public boolean minValidator(String min) {
        if (isIntegerValidator(min)) {
            IntegerValidator validator = new IntegerValidator();
            return validator.isInRange(intValidation, 0, 60);
        } else {
            return false;
        }
    }

    public boolean noValidator(String no) {
         boolean check;
         if (stringValidator(no)){
             check = false;
         }
       
        check = no.equalsIgnoreCase("no") || no.equalsIgnoreCase("n");

        return check;
    }
    
    public boolean yesValidator(String answer) {
         boolean check;
         if (stringValidator(answer)){
             check = false;
         }
       
        check = answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y");

        return check;
    }
     
    /*
    public static void main(String[] args) {
        String email = "iets@gmail.com";
        Validator valid = new Validator();
        boolean hoi = valid.eMailValidator(email);

        System.out.print(hoi);

    }
     */
}
