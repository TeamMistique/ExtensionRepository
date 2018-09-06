package com.teammistique.extensionrepository.exceptions;

import org.json.JSONException;

public class SyncException extends JSONException {
    public SyncException(String message) {
        super(message);
    }
}
