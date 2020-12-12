package io.swagger.api;

import io.swagger.dto.DocumentDto;
import io.swagger.dto.LockDto;
import io.swagger.model.Document;
import io.swagger.model.DocumentSummary;
import io.swagger.model.DocumentsList;
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


@RestController
@AllArgsConstructor
@Slf4j
public class DocumentsApiController {

    private final HttpServletRequest request;
    public DocumentService documentService;
    public LockService lockService;
    public AddLockService addLockService;

    /*
     * Document functions
     */

    // GET A DOCUMENT
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") String documentId) {

        if (RestUtils.isJson(request)) {
            Document document = documentService.getDocument(documentId);
            if(!document.equals(null)) return new ResponseEntity<Document>(document, HttpStatus.FOUND);
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    // PUT DOCUMENT
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<DocumentDto> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @RequestBody DocumentDto body) {

        if (RestUtils.isJson(request)) {
            Document updatedDocument = documentService.updateDocument(documentId, body.toEntity());
            try {
                DocumentDto updatedDocumentDto = updatedDocument.toDto();
                return new ResponseEntity<DocumentDto>(updatedDocumentDto, HttpStatus.OK);
            } catch(NullPointerException e) {
                return new ResponseEntity<DocumentDto>(HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<DocumentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    // PUT DOCUMENT STATUS
    @RequestMapping(value = "/documents/{documentId}/status", produces = { "application/json" }, consumes = { "text/plain" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @RequestBody String body) {

        if (RestUtils.isJson(request)) {
            if(Document.StatusEnum.fromValue(body) != Document.StatusEnum.valueOf("VALIDATED")) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

            Document.StatusEnum bodyEnum = Document.StatusEnum.fromValue(body);
            Document document = documentService.updateStatus(documentId, bodyEnum);

            if(!document.equals(null)) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    // GET DOCUMENTS
    @RequestMapping(value = "/documents", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<DocumentsList> documentsGet(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (RestUtils.isJson(request)) {
            DocumentsList documents = documentService.getAll();
            if(!documents.equals(null)) return new ResponseEntity<DocumentsList>(documents, HttpStatus.FOUND);
        }

        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

    // POST DOCUMENT
    @RequestMapping(value = "/documents", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<DocumentSummary> documentsPost(@RequestBody DocumentDto body) {

        if (RestUtils.isJson(request)) {
            DocumentSummary createdDocumentSummary = documentService.createDocument(body.toEntity());

            if(!createdDocumentSummary.equals(null)) return new ResponseEntity<DocumentSummary>(createdDocumentSummary, HttpStatus.CREATED);
        }
        return new ResponseEntity<DocumentSummary>(HttpStatus.NOT_IMPLEMENTED);
    }

    /*
     * Lock functions
     */

    // DELETE LOCK
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            // TODO : delete lock if user is the writter
            Boolean deleted = lockService.deleteLockOnDocument(documentId, RestUtils.returnLoggedUser());
            if(deleted) return new ResponseEntity<Boolean>(deleted,HttpStatus.OK);
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
            // TODO : Update Lock if user is writter
            Lock addedLock = addLockService.addLockOnDocument(documentId, RestUtils.returnLoggedUser());
            LockDto lockDto = addedLock.toDto();
            return new ResponseEntity<LockDto>(lockDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<LockDto>(HttpStatus.NOT_IMPLEMENTED);
    }

}
