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
public class AddLockService {
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
}
