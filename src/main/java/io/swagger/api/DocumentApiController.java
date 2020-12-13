package io.swagger.api;

import com.mongodb.MongoWriteException;
import io.swagger.dto.DocumentDto;
import io.swagger.exception.BadRequestException;
import io.swagger.exception.ConflictException;
import io.swagger.exception.LockedException;
import io.swagger.exception.NotFoundException;
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
            try {
                document.equals(null);
            } catch (NullPointerException e){
                throw new NotFoundException("Any document with id " + documentId );
            }
            return new ResponseEntity<Document>(document, HttpStatus.FOUND);
        }

        throw new BadRequestException("Is not JSON");
    }

    // PUT DOCUMENT
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<DocumentDto> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @RequestBody DocumentDto body) {

        if (RestUtils.isJson(request)) {
            Document updatedDocument = null;
            try {
                lockService.getLock(documentId).equals(null);
            } catch (NullPointerException e) {
                try {
                    updatedDocument = documentService.updateDocument(documentId, body.toEntity());
                } catch (NullPointerException f) {
                    throw new NotFoundException("Document Not Found");
                }
                DocumentDto updatedDocumentDto = updatedDocument.toDto();
                return new ResponseEntity<DocumentDto>(updatedDocumentDto, HttpStatus.OK);
            }
            throw new LockedException("Document is locked");
        }

        throw new BadRequestException("Is not JSON");
    }

    // PUT DOCUMENT STATUS
    @RequestMapping(value = "/documents/{documentId}/status", produces = { "application/json" }, consumes = { "text/plain" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @RequestBody String body) {

        if (RestUtils.isJson(request)) {
            if(Document.StatusEnum.fromValue(body) != Document.StatusEnum.valueOf("VALIDATED")) {
                throw new BadRequestException("The status must be VALIDATED");
            }

            Document.StatusEnum bodyEnum = Document.StatusEnum.fromValue(body);
            try{
                Document document = documentService.updateStatus(documentId, bodyEnum);
            } catch (NullPointerException e) {
                throw new NotFoundException("Any document with id " + documentId );
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        throw new BadRequestException("Is not JSON");
    }

    // GET DOCUMENTS
    @RequestMapping(value = "/documents", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<DocumentsList> documentsGet(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (RestUtils.isJson(request)) {
            DocumentsList documents = documentService.getAll();
            return new ResponseEntity<DocumentsList>(documents, HttpStatus.FOUND);
        }

        throw new BadRequestException("Is not JSON");
    }

    // POST DOCUMENT
    @RequestMapping(value = "/documents", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<DocumentSummary> documentsPost(@RequestBody DocumentDto body) {

        if (RestUtils.isJson(request)) {
            try {
                documentService.getDocument(body.getDocumentId()).equals(null);
            } catch (NullPointerException e) {
                DocumentSummary createdDocumentSummary = documentService.createDocument(body.toEntity());
                return new ResponseEntity<DocumentSummary>(createdDocumentSummary, HttpStatus.CREATED);
            }
            throw new ConflictException("already exist");

        }

        throw new BadRequestException("Is not JSON");
    }

}
