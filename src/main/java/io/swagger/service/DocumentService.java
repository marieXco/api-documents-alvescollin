package io.swagger.service;

import io.swagger.model.Document;
import io.swagger.model.DocumentSummary;
import io.swagger.model.DocumentsList;
import io.swagger.repository.DocumentRepository;
import io.swagger.utils.DocumentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final LockService lockService;

    public DocumentSummary createDocument(Document document){
        document.setStatus(Document.StatusEnum.CREATED);

        // Create a documentSummary for this document
        DocumentSummary documentSummary = DocumentUtils.summarize(document);

        return documentSummary;
    }

    public DocumentsList getAll(){
        List<Document> documents = documentRepository.findAll();
        List<DocumentSummary> documentSummaries = new ArrayList<DocumentSummary>();
        DocumentsList documentsList = new DocumentsList();

        int i = 0;
        Iterator<Document> iter = documents.iterator();

        while (iter.hasNext()) {
            Document doc = iter.next();
            documentSummaries.add(DocumentUtils.summarize(doc));
            i++;
        }

        // TODO : gérer les pages
        documentsList.setData(documentSummaries);
        documentsList.setNbElements(i);
        return documentsList;
    }

    public Document getDocument(String id){
        return documentRepository.findById(id).orElse(null);
    }

    public Document updateDocument(String id, Document updatedocument){
        Document document = getDocument(id);
        // TODO : Ajouter editeur + try / catch
        if(!document.getStatus().equals(Document.StatusEnum.valueOf("VALIDATED")) && lockService.getLock(id).equals(null)) {
            if(!updatedocument.getBody().equals(null)) document.setBody(updatedocument.getBody());
            if(!updatedocument.getTitle().equals(null)) document.setTitle(updatedocument.getTitle());

            return documentRepository.save(document);
        } else {
            return null;
        }
    }

    public Document updateStatus(String id, Document.StatusEnum status){
        Document document = getDocument(id);
        document.setStatus(status);
        return documentRepository.save(document);
    }
}
