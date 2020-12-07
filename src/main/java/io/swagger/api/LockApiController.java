package io.swagger.api;

import io.swagger.dto.LockDto;
import io.swagger.model.Lock;
import io.swagger.service.AddLockService;
import io.swagger.service.LockService;
import io.swagger.utils.RestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@Slf4j
public class LockApiController {

    private final HttpServletRequest request;
    public LockService lockService;
    public AddLockService addLockService;


    // DELETE LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            Boolean deleted = lockService.deleteLockOnDocument(documentId, RestUtils.returnLoggedUser());
            if(deleted) return new ResponseEntity<Boolean>(HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(HttpStatus.NOT_IMPLEMENTED);
    }

    // GET LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LockDto> documentsDocumentIdLockGet(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            Lock getLock = lockService.getLock(documentId);
            LockDto lockDto = getLock.toDto();
            return new ResponseEntity<LockDto>(lockDto, HttpStatus.FOUND);
        }

        return new ResponseEntity<LockDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    // PUT LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<LockDto> documentsDocumentIdLockPut(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            Lock addedLock = addLockService.addLockOnDocument(documentId, RestUtils.returnLoggedUser());
            LockDto lockDto = addedLock.toDto();
            return new ResponseEntity<LockDto>(lockDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<LockDto>(HttpStatus.NOT_IMPLEMENTED);
    }

}
