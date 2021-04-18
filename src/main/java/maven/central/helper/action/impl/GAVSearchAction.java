package maven.central.helper.action.impl;

import java.util.Map;

import javax.swing.JTextField;

import maven.central.helper.Labels;
import maven.central.helper.action.AbstractSearchAction;

public class GAVSearchAction extends AbstractSearchAction {

    private static final long serialVersionUID = 1L;

    private static final String AND = "+AND+";

    public GAVSearchAction(final Map<String, JTextField> textFields) {
        super(textFields);
    }

    @Override
    protected String buildQueryParameters() {
        final StringBuilder query = new StringBuilder();
        final String group = textFields.get(Labels.GROUP).getText();
        final String artifact = textFields.get(Labels.ARTIFACT).getText();
        final String version = textFields.get(Labels.VERSION).getText();
        if (!group.isEmpty()) {
            query.append("g:");
            query.append(group);
        }
        if (!artifact.isEmpty()) {
            if (query.length() > 0) {
                query.append(AND);
            }
            query.append("a:");
            query.append(artifact);
        }
        if (!version.isEmpty()) {
            if (query.length() > 0) {
                query.append(AND);
            }
            query.append("v:");
            query.append(version);
        }
        return query.toString();
    }

}
