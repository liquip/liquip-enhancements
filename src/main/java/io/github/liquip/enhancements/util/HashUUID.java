package io.github.liquip.enhancements.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class HashUUID {
    private HashUUID() {
    }

    public static UUID md5(String source) {
        return UUID.nameUUIDFromBytes(source.getBytes(StandardCharsets.UTF_8));
    }
}
