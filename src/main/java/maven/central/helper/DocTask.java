package maven.central.helper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.oxbow.swingbits.list.CheckListRenderer;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import maven.central.helper.action.AbstractSearchAction;
import maven.central.helper.collection.IndexedMap;
import maven.central.helper.data.release.DocId;
import maven.central.helper.data.release.Release;
import maven.central.helper.data.release.ReleaseMap;
import maven.central.helper.model.ResponseRoot;
import maven.central.helper.model.response.Response;
import maven.central.helper.model.response.docs.Docs;

public class DocTask extends SwingWorker<List<Docs>, Docs> {

    private static final String VERSION = "Version";
    private static final String ARTIFACT = "Artifact";
    private static final String GROUP = "Group";

    private final AbstractSearchAction searchAction;
    private final String queryParameters;
    private final IndexedMap<DocId, ReleaseMap> releasesPerGroupAndArtifact = new IndexedMap<>();

    public DocTask(final AbstractSearchAction searchAction, final String queryParameters) {
        this.searchAction = searchAction;
        this.queryParameters = queryParameters;
    }

    @Override
    public List<Docs> doInBackground() {
        int start = 0;
        final int rows = 100;
        int numFound = 0;
        final List<Docs> allDocs = new ArrayList<>();
        do {
            final String searchURL = searchAction.getSearchURL(rows * (start++), rows, queryParameters);
            try (InputStream in = new URL(searchURL).openStream(); InputStreamReader isr = new InputStreamReader(in); Jsonb jsonb = JsonbBuilder.create()) {
                final ResponseRoot responseRoot = jsonb.fromJson(isr, ResponseRoot.class);
                final Response response = responseRoot.getResponse();
                numFound = response.getNumFound();
                final List<Docs> docs = response.getDocs();
                allDocs.addAll(docs);
                publish(docs.stream().toArray(Docs[]::new));
                final double sizeAsDouble = allDocs.size();
                setProgress((int) Math.round(100 * sizeAsDouble / numFound));
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        } while (allDocs.size() < numFound);
        return allDocs;
    }

    @Override
    protected void process(final List<Docs> chunks) {
        for (final Docs doc : chunks) {
            final DocId docId = new DocId(doc);
            if (doc.getVersion() == null) {
                final String[] idTokens = doc.getId().split(":");
                for (final String idToken : idTokens) {
                    if (!Arrays.asList(doc.getGroup(), doc.getArtifact(), doc.getPackaging()).contains(idToken)) {
                        doc.setVersion(idToken);
                    }
                }
            }
            releasesPerGroupAndArtifact.computeIfAbsent(docId, ReleaseMap::new);
            releasesPerGroupAndArtifact.get(docId).put(doc.getVersion(), new Release(doc));
        }
    }

    @Override
    protected void done() {
        final Object[] columnNames = { GROUP, ARTIFACT, VERSION };
        final Object[][] rowData = new Object[releasesPerGroupAndArtifact.size()][columnNames.length];
        int i = 0;
        boolean multipleReleases = false;
        for (final Entry<DocId, ReleaseMap> releaseEntry : releasesPerGroupAndArtifact.entrySet()) {
            final Object[] row = rowData[i++];
            final DocId docId = releaseEntry.getKey();
            final ReleaseMap releaseMap = releaseEntry.getValue();
            if (releaseMap.size() > 1) {
                multipleReleases = true;
            }
            row[0] = docId.getGroup();
            row[1] = docId.getArtifact();
            row[2] = releaseMap.latest();
        }
        final JXTable table = new JXTable(rowData, columnNames) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(final int row, final int column) {
                return VERSION.equals(getColumnName(column));
            }
        };
        table.setColumnControlVisible(true);
        table.setHighlighters(HighlighterFactory.createSimpleStriping());
        TableRowFilterSupport.forTable(table).actions(true).searchable(true).checkListRenderer(new CheckListRenderer()).apply();
        if (multipleReleases) {
            table.getColumn(VERSION).setCellEditor(new VersionChoiceEditor(releasesPerGroupAndArtifact));
        }
        final JFrame frame = new JFrame(queryParameters);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

}