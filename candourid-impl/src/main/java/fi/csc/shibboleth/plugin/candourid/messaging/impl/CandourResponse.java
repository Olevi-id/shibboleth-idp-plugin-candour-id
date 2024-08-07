package fi.csc.shibboleth.plugin.candourid.messaging.impl;

public class CandourResponse {

    final private int code;

    final private String payload;

    CandourResponse(int code, String payload) {
        this.code = code;
        this.payload = payload;
    }

    public int getCode() {
        return code;
    }

    public String getPayload() {
        return payload;
    }

    public boolean indicateSuccess() {
        return (code >= 200 && code < 300);
    }

}
