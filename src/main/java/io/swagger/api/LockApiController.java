package io.swagger.api;

import io.swagger.dto.LockDto;
import io.swagger.exception.BadRequestException;
import io.swagger.exception.ConflictException;
import io.swagger.exception.ForbiddenException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Document;
import io.swagger.model.Lock;
import io.swagger.service.AddLockService;
import io.swagger.service.DocumentService;
import io.swagger.service.LockService;
import io.swagger.utils.RestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;


@RestController
@AllArgsConstructor
@Slf4j
public class LockApiController {

    private final HttpServletRequest request;
    public LockService lockService;
    public AddLockService addLockService;
    public DocumentService documentService;


    // DELETE LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            documentExists(documentId);

            Boolean deleted;
            try {
                deleted = lockService.deleteLockOnDocument(documentId, RestUtils.returnLoggedUser());
            } catch (NullPointerException e) {
                throw new NotFoundException("There is no lock on this document");
            }

            if(deleted)  {
                return new ResponseEntity<Boolean>(HttpStatus.OK);
            } else {
                throw new ForbiddenException("You can't delete the lock on this document");
            }
        }

        throw new BadRequestException("Is not JSON");
    }

    // GET LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LockDto> documentsDocumentIdLockGet(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            documentExists(documentId);
            Lock lockedGotten = lockService.getLock(documentId);

            try {
                lockedGotten.equals(null);
            } catch (NullPointerException e) {
                throw new NotFoundException("This document is not locked");
            }

            LockDto lockDto = lockedGotten.toDto();
            return new ResponseEntity<LockDto>(lockDto, HttpStatus.FOUND);
        }

        throw new BadRequestException("Is not JSON");
    }

    // PUT LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<LockDto> documentsDocumentIdLockPut(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            documentExists(documentId);

            Lock addedLock = addLockService.addLockOnDocument(documentId, RestUtils.returnLoggedUser());
            try {
                addedLock.equals(null);
            } catch (NullPointerException e) {
                throw new ConflictException("This document is already locked");
            }
            LockDto lockDto = addedLock.toDto();

            return new ResponseEntity<LockDto>(lockDto, HttpStatus.CREATED);
        }

        throw new BadRequestException("Is not JSON");
    }

    public void documentExists(String documentId) {
        try {
            documentService.getDocument(documentId).equals(null);
        } catch (NullPointerException e) {
            throw new NotFoundException("Document Not Found");
        }
    }

}
