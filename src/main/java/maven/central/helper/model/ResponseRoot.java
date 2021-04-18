package maven.central.helper.model;

import maven.central.helper.model.response.Response;
import maven.central.helper.model.responseheader.ResponseHeader;
import maven.central.helper.model.spellcheck.SpellCheck;

public class ResponseRoot {
    private ResponseHeader responseHeader;
    private Response response;
    private SpellCheck spellCheck;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public SpellCheck getSpellCheck() {
        return spellCheck;
    }

    public void setSpellCheck(SpellCheck spellCheck) {
        this.spellCheck = spellCheck;
    }

}
