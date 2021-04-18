package maven.central.helper.model.response.docs;

import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;

public class Docs {
    private String id;
    @JsonbProperty("g")
    private String group;
    @JsonbProperty("a")
    private String artifact;
    @JsonbProperty("latestVersion")
    private String version;
    private String repositoryId;
    @JsonbProperty("p")
    private String packaging;
    private long timestamp;
    private int versionCount;
    private List<String> text;
    private List<String> ec;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(final String artifact) {
        this.artifact = artifact;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(final String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(final String packaging) {
        this.packaging = packaging;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public int getVersionCount() {
        return versionCount;
    }

    public void setVersionCount(final int versionCount) {
        this.versionCount = versionCount;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(final List<String> text) {
        this.text = text;
    }

    public List<String> getEc() {
        return ec;
    }

    public void setEc(final List<String> ec) {
        this.ec = ec;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return String.join(":", group, artifact, version);
    }

}
