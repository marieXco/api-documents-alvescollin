package io.swagger.utils;

import io.swagger.model.Document;
import io.swagger.model.DocumentSummary;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DocumentUtils {

    public static DocumentSummary summarize(Document document){
        DocumentSummary documentSummary = new DocumentSummary(document.getDocumentId(),
                document.getCreated(),
                document.getUpdated(),
                document.getTitle());
        return documentSummary;
    }

}
