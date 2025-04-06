package Ewallet.service.impl;

import Ewallet.service.DataValidationService;

public class DataValidationImpl implements DataValidationService {
    @Override
    public boolean validateUserName(String userName) {
        if(userName.length()>=3&&Character.isUpperCase(userName.charAt(0)))
            return true;
        else
            return false;
    }

    @Override
    public boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        if(password.matches(regex))
            return true;
        else
            return false;
    }
}
