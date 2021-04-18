package maven.central.helper.model.spellcheck.suggestions;

import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;

public class Suggestion {
    private int numFound;
    private int startOffset;
    private int endOffset;
    @JsonbProperty("suggestion")
    private List<String> suggestions;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(final int numFound) {
        this.numFound = numFound;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(final int startOffset) {
        this.startOffset = startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(final int endOffset) {
        this.endOffset = endOffset;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(final List<String> suggestions) {
        this.suggestions = suggestions;
    }

}
