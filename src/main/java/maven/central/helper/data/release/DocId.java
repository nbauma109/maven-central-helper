package maven.central.helper.data.release;

import maven.central.helper.model.response.docs.Docs;

public class DocId {
    private String group;
    private String artifact;

    public DocId(final String group, final String artifact) {
        this.group = group;
        this.artifact = artifact;
    }

    public DocId(final Docs doc) {
        this(doc.getGroup(), doc.getArtifact());
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


    @Override
    public String toString() {
        return String.join(":", group, artifact);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((artifact == null) ? 0 : artifact.hashCode());
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocId other = (DocId) obj;
        if (artifact == null) {
            if (other.artifact != null) {
                return false;
            }
        } else if (!artifact.equals(other.artifact)) {
            return false;
        }
        if (group == null) {
            if (other.group != null) {
                return false;
            }
        } else if (!group.equals(other.group)) {
            return false;
        }
        return true;
    }
}
