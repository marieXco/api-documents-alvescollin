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

    public Lock addLockOnDocument(String documentId, String user){
        try {
            lockRepository.findLockByDocumentId(documentId).equals(null);
        } catch (NullPointerException e) {
            Lock lock = new Lock();
            lock.setOwner(user);
            lock.setDocumentId(documentId);
            lock = lockRepository.insert(lock);
            return lock;
        }

        return null;
    }

    public Lock getLock(String documentId){
        return lockRepository.findLockByDocumentId(documentId);
    }

    public Boolean deleteLockOnDocument(String documentId, String user){
        Lock lockFound = getLock(documentId);

        if(lockFound.getOwner().equals(user)) {
            lockRepository.deleteLockByDocumentId(documentId);
            return true;
        } else {
            return false;
        }

    }

}
