package maven.central.helper.model.response;

import java.util.List;

import maven.central.helper.model.response.docs.Docs;

public class Response {
    private int numFound;
    private int start;
    private List<Docs> docs;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Docs> getDocs() {
        return docs;
    }

    public void setDocs(List<Docs> docs) {
        this.docs = docs;
    }

}
