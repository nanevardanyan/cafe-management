package am.nv.cafe.service.impl;

import am.nv.cafe.dataaccess.model.CafeTable;
import am.nv.cafe.dataaccess.model.User;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import am.nv.cafe.repository.TableRepository;
import am.nv.cafe.repository.UserRepository;
import am.nv.cafe.service.TableService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableServiceImpl.class);

    @Autowired
    private TableRepository repository;

    @Autowired
    private UserRepository userRepository;

    public CafeTable add(CafeTable table) throws InternalErrorException {
        try {
            return repository.saveAndFlush(table);
        } catch (Exception e) {
            String message = String.format("Failed to add the [ Table: %s ]", table);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }

    public CafeTable assignWaiter(String tableId, String waiterId) throws InternalErrorException {
        try {
            Optional<CafeTable> table = repository.findById(tableId);
            Optional<User> waiter = userRepository.findById(waiterId);

            if (table.isPresent() && waiter.isPresent()) {
                table.get().setWaiter(waiter.get());
                repository.saveAndFlush(table.get());
                return table.get();
            } else {
                String message = String.format("Failed to find table/waiter with the specified id " +
                        "[TableId: %s, WaiterId: %s]", tableId, waiterId);
                LOGGER.error(message);
                throw new InvalidObjectException(message);
            }
        } catch (Exception e) {
            String message = String.format("Failed to set waiter to  the table " +
                    "[TableId: %s, WaiterId: %s]", tableId, waiterId);
            LOGGER.error(message, e);
            throw new InternalErrorException(message);
        }
    }

    public List<CafeTable> getAssignedTables(String waiterId) throws InvalidObjectException, InternalErrorException {

        if (!userRepository.exists(waiterId)) {
            String message = String.format("Failed to find waiter with the specified id " +
                    "[WaiterId: %s]", waiterId);
            LOGGER.error(message);
            throw new InvalidObjectException(message);
        }

        try {
            return repository.findAllByWaiterId(waiterId);
        } catch (Exception e) {
            String message = String.format("Failed to find tables for the [ WaiterId: %s]", waiterId);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }
}
