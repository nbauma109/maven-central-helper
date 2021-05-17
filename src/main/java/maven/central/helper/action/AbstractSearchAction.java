package maven.central.helper.action;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;

import maven.central.helper.DocTask;
import maven.central.helper.Labels;
import maven.central.helper.ProxyConstants;

public abstract class AbstractSearchAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    protected transient Map<String, JTextField> textFields;

    protected AbstractSearchAction(final Map<String, JTextField> textFields) {
        super("Search");
        this.textFields = textFields;
    }

    public final String getSearchURL(final int start, final int rows, final String queryParameters) {
        final StringBuilder query = new StringBuilder();
        query.append("http://search.maven.org/solrsearch/select?q=");
        query.append(queryParameters);
        query.append("&start=");
        query.append(start);
        query.append("&rows=");
        query.append(rows);
        return query.toString();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        initPropertiesFromInput(textFields);
        final ProgressMonitor progressMonitor = new ProgressMonitor(null, "Loading results ...", "", 0, 100);
        final String queryParameters = buildQueryParameters();
        final DocTask docTask = new DocTask(this, queryParameters);
        docTask.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                final Integer progress = (Integer) evt.getNewValue();
                final String message = String.format("Completed %d%%.%n", progress);
                progressMonitor.setNote(message);
                progressMonitor.setProgress(progress);
                if (progressMonitor.isCanceled()) {
                    docTask.cancel(true);
                }
            }
        });
        docTask.execute();
    }

    public static void initPropertiesFromInput(final Map<String, JTextField> textFields) {
        final String proxyHost = textFields.get(Labels.PROXY_HOST_LABEL).getText();
        final String proxyPort = textFields.get(Labels.PROXY_PORT_LABEL).getText();
        final String proxyUser = textFields.get(Labels.PROXY_USER_LABEL).getText();
        final String proxyPass = textFields.get(Labels.PROXY_PASS_LABEL).getText();
        System.setProperty(ProxyConstants.PROXY_HTTP_HOST_PROPERTY, proxyHost);
        System.setProperty(ProxyConstants.PROXY_HTTPS_HOST_PROPERTY, proxyHost);
        System.setProperty(ProxyConstants.PROXY_HTTP_PORT_PROPERTY, proxyPort);
        System.setProperty(ProxyConstants.PROXY_HTTPS_PORT_PROPERTY, proxyPort);
        System.setProperty(ProxyConstants.PROXY_HTTP_USER_PROPERTY, proxyUser);
        System.setProperty(ProxyConstants.PROXY_HTTPS_USER_PROPERTY, proxyUser);
        System.setProperty(ProxyConstants.PROXY_HTTP_PASSWORD_PROPERTY, proxyPass);
        System.setProperty(ProxyConstants.PROXY_HTTPS_PASSWORD_PROPERTY, proxyPass);
    }

    protected abstract String buildQueryParameters();

}
