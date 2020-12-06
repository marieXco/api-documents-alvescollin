package io.swagger.service;

import io.swagger.model.Document;
import io.swagger.model.Lock;
import io.swagger.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {
    private final LockRepository lockRepository;

    public Lock addLockOnDocument(String documentId){
        // TODO : verify owner = user
        //  + add user as owner +
        Lock lock = new Lock();
        lock.setDocumentId(documentId);
        return lockRepository.insert(lock);
    }

    public Lock getLock(String documentId){
        // TODO : verify owner = user
        return lockRepository.findLockByDocumentId(documentId);
    }

    public Boolean deleteLockOnDocument(String documentId){
        // TODO : verify owner = user
        lockRepository.deleteLockByDocumentId(documentId);
        try {
            getLock(documentId).equals(null);
        } catch(NullPointerException e) {
            return true;
        }
        return false;
    }

}
