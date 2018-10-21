package am.nv.cafe.service;

import am.nv.cafe.dataaccess.model.User;
import am.nv.cafe.exception.DuplicateEntryException;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User add(User user) throws InvalidObjectException, DuplicateEntryException, InternalErrorException;

}
