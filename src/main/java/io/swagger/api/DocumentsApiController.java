package io.swagger.api;

import io.swagger.dto.DocumentDto;
import io.swagger.model.Document;
import io.swagger.model.DocumentsList;
import io.swagger.model.Lock;
import io.swagger.service.DocumentService;
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


    // ----------------------------- Get a document by id -----------------------------
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Document> documentsDocumentIdGet(@PathVariable("documentId") String documentId) {

        if (RestUtils.isJson(request)) {
            Document document = documentService.getDocument(documentId);
            if(!document.equals(null)) return new ResponseEntity<Document>(document, HttpStatus.FOUND);
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- Update a document by id -----------------------------
    @RequestMapping(value = "/documents/{documentId}", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<DocumentDto> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @RequestBody DocumentDto body) {

        if (RestUtils.isJson(request)) {
            Document updatedDocument = documentService.updateDocument(documentId, body.toEntity());
            DocumentDto updatedDocumentDto = updatedDocument.toDto();

            if(!updatedDocumentDto.equals(null)) return new ResponseEntity<DocumentDto>(updatedDocumentDto, HttpStatus.OK);
        }

        return new ResponseEntity<DocumentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- Update status of document by id -----------------------------
    @RequestMapping(value = "/documents/{documentId}/status", produces = { "application/json" }, consumes = { "text/plain" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @RequestBody String body) {

        if (RestUtils.isJson(request)) {
            if(Document.StatusEnum.fromValue(body) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

            Document.StatusEnum bodyEnum = Document.StatusEnum.fromValue(body);
            Document document = documentService.updateStatus(documentId, bodyEnum);

            if(!document.equals(null)) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- Get all documents -----------------------------
    @RequestMapping(value = "/documents", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<DocumentsList> documentsGet(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (RestUtils.isJson(request)) {
            DocumentsList documents = documentService.getAll();
            if(!documents.equals(null)) return new ResponseEntity<DocumentsList>(documents, HttpStatus.FOUND);
        }

        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- Create new document -----------------------------
    @RequestMapping(value = "/documents", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<DocumentDto> documentsPost(@RequestBody DocumentDto body) {

        if (RestUtils.isJson(request)) {
            Document createdDocument = documentService.createDocument(body.toEntity());
            DocumentDto createdTweetDto = createdDocument.toDto();

            if(!createdTweetDto.equals(null)) return new ResponseEntity<DocumentDto>(createdTweetDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<DocumentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    /*
     * Lock functions
     */
    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<Void> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Lock> documentsDocumentIdLockGet(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            /*try {
                return null;
                //return new ResponseEntity<Lock>(objectMapper.readValue("{\n  \"owner\" : \"owner\",\n  \"created\" : \"2000-01-23T04:56:07.000+00:00\"\n}", Lock.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }*/
            return null;
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/documents/{documentId}/lock", produces = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<Lock> documentsDocumentIdLockPut(@PathVariable("documentId") String documentId) {
        if (RestUtils.isJson(request)) {
            /*try {
                return new ResponseEntity<Lock>(objectMapper.readValue("{\n  \"owner\" : \"owner\",\n  \"created\" : \"2000-01-23T04:56:07.000+00:00\"\n}", Lock.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }*/
            return null;
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

}
