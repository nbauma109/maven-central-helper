package maven.central.helper.action.impl;

import java.util.Map;

import javax.swing.JTextField;

import maven.central.helper.Labels;
import maven.central.helper.action.AbstractSearchAction;

public class SHA1SearchAction extends AbstractSearchAction {

    private static final long serialVersionUID = 1L;

    public SHA1SearchAction(final Map<String, JTextField> textFields) {
        super(textFields);
    }

    @Override
    protected String buildQueryParameters() {
        final String text = textFields.get(Labels.SHA1).getText();
        final StringBuilder query = new StringBuilder();
        query.append("1:");
        query.append(text);
        return query.toString();
    }

}
