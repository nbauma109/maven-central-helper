package maven.central.helper;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import maven.central.helper.collection.IndexedMap;
import maven.central.helper.data.release.DocId;
import maven.central.helper.data.release.Release;
import maven.central.helper.data.release.ReleaseMap;

public class VersionChoiceEditor extends DefaultCellEditor {

    private static final long serialVersionUID = 1L;

    private transient Release version;
    private transient IndexedMap<DocId, ReleaseMap> releases;

    public VersionChoiceEditor(final IndexedMap<DocId, ReleaseMap> releases) {
        super(new JComboBox<>());
        this.releases = releases;
    }

    @Override
    public Object getCellEditorValue() {
        return version;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
        if (value instanceof Release) {
            version = (Release) value;
        }
        final ReleaseMap releaseMap = releases.get(table.convertRowIndexToModel(row));
        final JComboBox<Release> versionChoices = new JComboBox<>(releaseMap.getReleases().values().stream().toArray(Release[]::new));
        versionChoices.addActionListener(e -> {
            version = (Release) versionChoices.getSelectedItem();
        });
        return versionChoices;
    }
}