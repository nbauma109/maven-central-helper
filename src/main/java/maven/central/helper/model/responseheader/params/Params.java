package maven.central.helper.model.responseheader.params;

import jakarta.json.bind.annotation.JsonbProperty;

public class Params {
    @JsonbProperty("q")
    private String query;
    private String core;
    private String defType;
    private String qf;
    private boolean spellcheck;
    private String indent;
    private String fl;
    private int start;
    @JsonbProperty("spellcheck.count")
    private int spellCheckCont;
    private String sort;
    private int rows;
    private String wt;
    private String version;

    public String getQuery() {
        return query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getCore() {
        return core;
    }

    public void setCore(final String core) {
        this.core = core;
    }

    public String getDefType() {
        return defType;
    }

    public void setDefType(final String defType) {
        this.defType = defType;
    }

    public String getQf() {
        return qf;
    }

    public void setQf(final String qf) {
        this.qf = qf;
    }

    public boolean isSpellcheck() {
        return spellcheck;
    }

    public void setSpellcheck(final boolean spellcheck) {
        this.spellcheck = spellcheck;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(final String indent) {
        this.indent = indent;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(final String fl) {
        this.fl = fl;
    }

    public int getStart() {
        return start;
    }

    public void setStart(final int start) {
        this.start = start;
    }

    public int getSpellCheckCont() {
        return spellCheckCont;
    }

    public void setSpellCheckCont(final int spellCheckCont) {
        this.spellCheckCont = spellCheckCont;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(final String sort) {
        this.sort = sort;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(final int rows) {
        this.rows = rows;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(final String wt) {
        this.wt = wt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

}
