package com.wedding.backend.util.helper;

import java.util.UUID;

public class HashHelper {
    public static String generateEntityId() {
        // Generate a UUID (Universally Unique Identifier)
        UUID entityId = UUID.randomUUID();

        // Convert UUID to a string without hyphens
        String codeString = entityId.toString().replace("-", "");

        return codeString;
    }
}
