package am.nv.cafe.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;

public class MessageUtils {

    public static Map<String, String> toMap(Collection<? extends ConstraintViolation> violations) {
        return violations == null ? Collections.emptyMap() : violations.stream()
                .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }
}
