package io.swagger.service;

import io.swagger.api.NotFoundException;
import io.swagger.model.Document;
import io.swagger.model.DocumentSummary;
import io.swagger.model.DocumentsList;
import io.swagger.repository.DocumentRepository;
import io.swagger.repository.DocumentSummaryRepository;
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
    private final DocumentSummaryRepository documentSummaryRepository;

    public DocumentsList createDocument(Document document){
        Document insertedDocument = documentRepository.insert(document);

        // Create a documentSummary for this document
        DocumentSummary documentSummary = documentSummaryRepository.insert(summarize(document));

        List<DocumentSummary> listsummary = new ArrayList<DocumentSummary>();
        listsummary.add(documentSummary);

        // Create a document list for this document
        DocumentsList documentsList = new DocumentsList();
        documentsList.setData(listsummary);
        return documentsList;
    }

    public DocumentsList getAll(){
        List<Document> documents = documentRepository.findAll();
        List<DocumentSummary> documentSummaryList = new ArrayList<DocumentSummary>();
        DocumentsList documentsList = new DocumentsList();
        int i=0;
        Iterator<Document> iter = documents.iterator();
        while (iter.hasNext()) {
            Document doc = iter.next();
            documentSummaryList.add(this.summarize(doc));
            i++;
        }
        documentsList.setData(documentSummaryList);
        return documentsList;
    }

    public Document getDocument(String id){
        System.out.println("jlkj" + id);
        return documentRepository.findById(id).orElse(null);
    }

    public Document updateDocument(Document updatedocument){
        Document document = getDocument(updatedocument.getDocumentId());
        document.setBody(updatedocument.getBody());
        document.setCreator(updatedocument.getCreator());
        document.setEditor(updatedocument.getEditor());
        document.setStatus(updatedocument.getStatus());
        document.setCreated(updatedocument.getCreated());
        document.setTitle(updatedocument.getTitle());
        document.setUpdated(updatedocument.getUpdated());
        return documentRepository.save(document);

    }

    public void updateStatus(String id, Document.StatusEnum status){
        Document document = getDocument(id);
        document.setStatus(status);
        documentRepository.save(document);
        //return document.getStatus().toString();
    }

    public DocumentSummary summarize(Document document){
        DocumentSummary documentSummary = new DocumentSummary();
        documentSummary.setCreated(document.getCreated());
        documentSummary.setDocumentId(document.getDocumentId());
        documentSummary.setUpdated(document.getUpdated());
        documentSummary.setTitle(document.getTitle());
        return documentSummary;
    }
}
