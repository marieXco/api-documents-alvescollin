package io.swagger.service;

import io.swagger.model.Document;
import io.swagger.model.DocumentSummary;
import io.swagger.model.DocumentsList;
import io.swagger.model.Lock;
import io.swagger.repository.DocumentRepository;
import io.swagger.repository.LockRepository;
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
public class AddLockService {
    private final DocumentService documentService;
    private final LockRepository lockRepository;

    public Lock addLockOnDocument(String documentId, String user){
        Document document = documentService.getDocument(documentId);
        Lock lock = new Lock();
        Lock lockFound = lockRepository.findLockByDocumentId(documentId);

        if(!document.equals(null)) {
            if(lockFound.equals(null)) {
                lock.setOwner(user);
                lock.setDocumentId(documentId);
                lock = lockRepository.insert(lock);
            }
        }

        return lock;
    }
}
