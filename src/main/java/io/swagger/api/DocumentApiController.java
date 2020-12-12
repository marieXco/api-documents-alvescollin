package io.swagger.api;

import io.swagger.dto.DocumentDto;
import io.swagger.model.*;
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
public class DocumentApiController {

    private final HttpServletRequest request;
    public DocumentService documentService;
    public LockService lockService;
    public AddLockService addLockService;

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
    public ResponseEntity<DocumentDto> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @RequestBody DocumentDto body) throws NotFoundException {

        if (RestUtils.isJson(request)) {
            Document updatedDocument = null;
            try {
                lockService.getLock(documentId).equals(null);
            } catch (NullPointerException e) {
                try {
                    updatedDocument = documentService.updateDocument(documentId, body.toEntity());
                } catch (NullPointerException f) {
                    throw new NotFoundException("Already validated");
                }
                DocumentDto updatedDocumentDto = updatedDocument.toDto();
                return new ResponseEntity<DocumentDto>(updatedDocumentDto, HttpStatus.OK);
            }
            throw new NotFoundException("Document is locked");
        }

        throw new NotFoundException("Is not JSON");
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

}
