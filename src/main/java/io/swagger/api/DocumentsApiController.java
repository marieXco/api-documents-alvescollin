package io.swagger.api;

import io.swagger.model.Document;
import io.swagger.model.DocumentsList;
import io.swagger.model.Lock;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.DocumentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-12-05T07:54:55.839Z[GMT]")
@RestController
@AllArgsConstructor
public class DocumentsApiController implements DocumentsApi {

    private static final Logger log = LoggerFactory.getLogger(DocumentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public DocumentService documentService;

    @Autowired
    public DocumentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    // ----------------------------- get a document by id -----------------------------
    public ResponseEntity<Document> documentsDocumentIdGet(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Document document = documentService.getDocument(documentId);
            return new ResponseEntity<Document>(document, HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> documentsDocumentIdLockDelete(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockGet(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Lock>(objectMapper.readValue("{\n  \"owner\" : \"owner\",\n  \"created\" : \"2000-01-23T04:56:07.000+00:00\"\n}", Lock.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Lock> documentsDocumentIdLockPut(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Lock>(objectMapper.readValue("{\n  \"owner\" : \"owner\",\n  \"created\" : \"2000-01-23T04:56:07.000+00:00\"\n}", Lock.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Lock>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Lock>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- update a document by id -----------------------------
    public ResponseEntity<Document> documentsDocumentIdPut(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Document body) {
        Document document = documentService.updateDocument(body);
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Document>(document, HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<Document>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- update status of document by id -----------------------------
    public ResponseEntity<Void> documentsDocumentIdStatusPut(@Parameter(in = ParameterIn.PATH, description = "identifiant du document à récupérer", required=true, schema=@Schema()) @PathVariable("documentId") String documentId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody String body) {
        Document.StatusEnum bodyEnum = Document.StatusEnum.fromValue(body);
        documentService.updateStatus(documentId, bodyEnum);
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- get all documents -----------------------------
    public ResponseEntity<DocumentsList> documentsGet(@Parameter(in = ParameterIn.QUERY, description = "numéro de la page à retourner" ,schema=@Schema()) @Valid @RequestParam(value = "page", required = false) Integer page,@Parameter(in = ParameterIn.QUERY, description = "nombre de documents par page" ,schema=@Schema()) @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        String accept = request.getHeader("Accept");
        DocumentsList alldocuments = documentService.getAll();
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<DocumentsList>(alldocuments, HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

    // ----------------------------- create new document -----------------------------
    public ResponseEntity<DocumentsList> documentsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Document body) {
        String accept = request.getHeader("Accept");
        DocumentsList createdDocument = documentService.createDocument(body);
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<DocumentsList>(createdDocument, HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

}
