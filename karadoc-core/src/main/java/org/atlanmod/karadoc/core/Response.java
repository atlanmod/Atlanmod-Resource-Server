package org.atlanmod.karadoc.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represent an immutable response from the server to the client
 * @param <T> the type of data in this response
 */
public class Response<T> {

    /**
     * The status of the request. Can be a success or an error if any occurred.
     */
    private final ResponseType type;

    /**
     * The data payload on this response
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public Response(@NotNull ResponseType type) {
        this.type = type;
        this.data = null;
    }

    public Response(@NotNull ResponseType type, @Nullable T data) {
        this.type = type;
        this.data = data;
    }

    public ResponseType getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
