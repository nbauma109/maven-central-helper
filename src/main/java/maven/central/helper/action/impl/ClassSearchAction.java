package maven.central.helper.action.impl;

import java.util.Map;

import javax.swing.JTextField;

import maven.central.helper.Labels;
import maven.central.helper.action.AbstractSearchAction;

public class ClassSearchAction extends AbstractSearchAction {

    private static final long serialVersionUID = 1L;

    public ClassSearchAction(final Map<String, JTextField> textFields) {
        super(textFields);
    }

    @Override
    protected String buildQueryParameters() {
        final String text = textFields.get(Labels.CLASS).getText();
        final StringBuilder query = new StringBuilder();
        if (text.matches("\\w+")) {
            query.append("c:");
        } else {
            query.append("fc:");
        }
        query.append(text);
        return query.toString();
    }

}
