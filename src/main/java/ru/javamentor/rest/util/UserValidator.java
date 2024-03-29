package ru.javamentor.rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javamentor.rest.model.User;
import ru.javamentor.rest.service.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        User userFind = userService.findByLogin(user.getLogin());
        if (userFind == null || user.getId() == userFind.getId()) {
            return; // Всё ОК
        }
        errors.rejectValue("login", "", "Такой email уже существует");
    }
}
