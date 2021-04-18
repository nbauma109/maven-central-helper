package maven.central.helper.action.impl;

import java.util.Map;

import javax.swing.JTextField;

import maven.central.helper.Labels;
import maven.central.helper.action.AbstractSearchAction;

public class GlobalSearchAction extends AbstractSearchAction {

    private static final long serialVersionUID = 1L;

    public GlobalSearchAction(final Map<String, JTextField> textFields) {
        super(textFields);
    }

    @Override
    protected String buildQueryParameters() {
        return textFields.get(Labels.KEYWORD).getText();
    }

}
