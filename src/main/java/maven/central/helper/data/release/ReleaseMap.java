package maven.central.helper.data.release;

import java.util.Map;
import java.util.TreeMap;

public class ReleaseMap {
    private DocId docId;
    private final TreeMap<String, Release> releases;

    public ReleaseMap(final DocId docId) {
        this.docId = docId;
        releases = new TreeMap<>();
    }

    public DocId getDocId() {
        return docId;
    }

    public void setDocId(final DocId docId) {
        this.docId = docId;
    }

    public Map<String, Release> getReleases() {
        return releases;
    }

    public void add(final Release release) {
        releases.put(release.toString(), release);
    }

    public Release latest() {
        return releases.lastEntry().getValue();
    }

    public int size() {
        return releases.size();
    }
}
