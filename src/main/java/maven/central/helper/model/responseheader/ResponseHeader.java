package maven.central.helper.model.responseheader;

import jakarta.json.bind.annotation.JsonbProperty;
import maven.central.helper.model.responseheader.params.Params;

public class ResponseHeader {
    private int status;
    @JsonbProperty("QTime")
    private long queryTime;
    private Params params;

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public long getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(final long queryTime) {
        this.queryTime = queryTime;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(final Params params) {
        this.params = params;
    }

}
