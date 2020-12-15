package io.swagger.api;

import io.swagger.dto.DocumentDto;
import io.swagger.exception.*;
import io.swagger.model.*;
import io.swagger.service.DocumentService;
import io.swagger.service.LockService;
import io.swagger.utils.RestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@Slf4j
public class DocumentApiController {

    public DocumentService documentService;
    public LockService lockService;

    // GET A DOCUMENT
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") String documentId) {
        Document document = documentService.getDocument(documentId);
        try {
            document.equals(null);
        } catch (NullPointerException e){
            throw new NotFoundException("Any document with id " + documentId);
        }
        return new ResponseEntity<Document>(document, HttpStatus.FOUND);
    }

    // PUT DOCUMENT
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<DocumentDto> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @RequestBody DocumentDto body) {

        Document updatedDocument = null;
        Lock lock = lockService.getLock(documentId);
        boolean doIt = false;

        try {
            lock.equals(null);
        } catch (NullPointerException e) {
            doIt = true;
        }

        if(doIt || lock.getOwner().equals(RestUtils.returnLoggedUser())) {
            if(body.getVersion() != documentService.getDocument(documentId).getVersion()) {
                throw new ConflictException("Version conflict");
            }

            try {
                updatedDocument = documentService.updateDocument(documentId, body.toEntity(), RestUtils.returnLoggedUser());
            } catch (NullPointerException f) {
                throw new NotFoundException("Document Not Found");
            }

            DocumentDto updatedDocumentDto = updatedDocument.toDto();
            return new ResponseEntity<DocumentDto>(updatedDocumentDto, HttpStatus.OK);
        } else {
            throw new LockedException("Document is locked");
        }
    }

    // PUT DOCUMENT STATUS
    @RequestMapping(value = "/documents/{documentId}/status", produces = { "application/json" }, consumes = { "text/plain" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @RequestBody String body) {

        if(Document.StatusEnum.fromValue(body) != Document.StatusEnum.valueOf("VALIDATED")) {
            throw new BadRequestException("The status must be VALIDATED");
        }

        Document document;
        Document.StatusEnum bodyEnum = Document.StatusEnum.fromValue(body);
        try{
            document = documentService.updateStatus(documentId, bodyEnum);
        } catch (NullPointerException e) {
            throw new NotFoundException("Any document with id " + documentId);
        }

        if(document.equals(null)) {
            throw new ForbiddenException("This document is already validated");
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // GET DOCUMENTS
    @RequestMapping(value = "/documents", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<DocumentsList> documentsGet(@PageableDefault(page = 0, size = 20) Pageable pageable) {

        DocumentsList documents = documentService.getAll(pageable);
        return new ResponseEntity<DocumentsList>(documents, HttpStatus.FOUND);
    }

    // POST DOCUMENT
    @RequestMapping(value = "/documents", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<DocumentSummary> documentsPost(@RequestBody DocumentDto body) {

        try {
            documentService.getDocument(body.getDocumentId()).equals(null);
        } catch (NullPointerException e) {
            DocumentSummary createdDocumentSummary = documentService.createDocument(body.toEntity(), RestUtils.returnLoggedUser());
            return new ResponseEntity<DocumentSummary>(createdDocumentSummary, HttpStatus.CREATED);
        }
        throw new ConflictException("Already exist");

    }

}
