package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.DocumentRepository;
import io.swagger.repository.LockRepository;
import io.swagger.utils.DocumentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public DocumentSummary createDocument(Document document, String creator){
        document.setStatus(Document.StatusEnum.CREATED);
        document.setCreator(creator);
        document.setEditor(creator);

        documentRepository.insert(document);
        // Create a documentSummary for this document
        DocumentSummary documentSummary = DocumentUtils.summarize(document);

        return documentSummary;
    }

    public DocumentsList getAll(Pageable pageable){
        Page<Document> documents = documentRepository.findAll(pageable);

        List<DocumentSummary> documentSummaries = new ArrayList<DocumentSummary>();
        DocumentsList documentsList = new DocumentsList();

        Iterator<Document> iter = documents.iterator();

        while (iter.hasNext()) {
            Document doc = iter.next();
            documentSummaries.add(DocumentUtils.summarize(doc));
        }

        documentsList.setData(documentSummaries);
        documentsList.setNbElements(pageable.getPageSize());
        documentsList.setPage(pageable.getPageNumber());
        return documentsList;
    }

    public Document getDocument(String id){
        return documentRepository.findById(id).orElse(null);
    }

    public Document updateDocument(String id, Document updatedocument, String editor) {
        Document document = getDocument(id);

        try {
            lockService.getLock(id);
        } catch (NullPointerException e) {

        }

        if(!document.getStatus().equals(Document.StatusEnum.valueOf("VALIDATED"))) {
            Document documentOnBase = documentRepository.findById(id).orElse(null);
            if(!updatedocument.getBody().equals(null)) documentOnBase.setBody(updatedocument.getBody());
            if(!updatedocument.getTitle().equals(null)) documentOnBase.setTitle(updatedocument.getTitle());
            documentOnBase.setEditor(editor);

            return documentRepository.save(documentOnBase);
        } else {
            return null;
        }

    }

    public Document updateStatus(String id, Document.StatusEnum status){
        Document document = getDocument(id);
        if(document.getStatus().equals(Document.StatusEnum.valueOf("VALIDATED"))) return null;

        document.setStatus(status);
        return documentRepository.save(document);
    }
}
