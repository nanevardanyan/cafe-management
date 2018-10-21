package am.nv.cafe.controller;

import am.nv.cafe.controller.data.Response;
import am.nv.cafe.dataaccess.model.CafeTable;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import am.nv.cafe.service.impl.TableServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TableController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableController.class);

    @Autowired
    private TableServiceImpl tableService;

    @PostMapping(path = "/table/add")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseBody
    public Response<CafeTable> createUser(String tableNumber) {
        Response<CafeTable> response = new Response<>();

        CafeTable table = new CafeTable();
        table.setTableNumber(tableNumber);

        try {
            CafeTable tableAdded = tableService.add(table);
            response.setData(tableAdded).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(getMessage("msg.table.failed.to.add", tableNumber));
        }

        return response;
    }

    @PostMapping(path = "/table/assign")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseBody
    public Response<CafeTable> assignUser(String tableId, String userId) {
        Response<CafeTable> response = new Response<>();

        try {
            CafeTable table = tableService.assignWaiter(tableId, userId);
            response.setData(table).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(getMessage("msg.table.failed.assign.waiter", tableId, userId));
        }
        return response;
    }

    @GetMapping(path = "table/assigned")
    @ResponseBody
    public Response<List<CafeTable>> getAssignedTables(String waiterId) {
        Response<List<CafeTable>> response = new Response<>();

        try {
            List<CafeTable> assignedTables = tableService.getAssignedTables(waiterId);
            response.setData(assignedTables).setStatus(HttpStatus.OK);
        } catch (InvalidObjectException e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
        } catch (InternalErrorException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(getMessage("msg.table.failed.to.load", waiterId));
        }
        return response;
    }
}
