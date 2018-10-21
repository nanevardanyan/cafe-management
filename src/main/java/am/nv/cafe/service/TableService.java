package am.nv.cafe.service;

import am.nv.cafe.dataaccess.model.CafeTable;
import am.nv.cafe.exception.InternalErrorException;

public interface TableService {

    CafeTable add(CafeTable table) throws InternalErrorException;

    CafeTable assignWaiter(String tableId, String waiterId) throws InternalErrorException;
}
