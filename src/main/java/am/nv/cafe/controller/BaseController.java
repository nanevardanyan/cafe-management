package am.nv.cafe.controller;

import am.nv.cafe.util.MessageUtils;
import java.util.Locale;
import java.util.Map;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

public class BaseController {

    @Autowired
    @Qualifier("validator")
    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    protected String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.ENGLISH);
    }

    protected String getMessage(String key, String... args) {
        return messageSource.getMessage(key, args, Locale.ENGLISH);
    }

    protected <T> Map<String, String> validate(T t) {
        return MessageUtils.toMap(validator.validate(t));
    }
}
