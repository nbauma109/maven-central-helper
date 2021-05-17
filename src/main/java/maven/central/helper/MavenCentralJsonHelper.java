package maven.central.helper;

import static maven.central.helper.Labels.ARTIFACT;
import static maven.central.helper.Labels.CLASS;
import static maven.central.helper.Labels.GROUP;
import static maven.central.helper.Labels.KEYWORD;
import static maven.central.helper.Labels.PROXY_HOST_LABEL;
import static maven.central.helper.Labels.PROXY_PASS_LABEL;
import static maven.central.helper.Labels.PROXY_PORT_LABEL;
import static maven.central.helper.Labels.PROXY_USER_LABEL;
import static maven.central.helper.Labels.SHA1;
import static maven.central.helper.Labels.VERSION;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.github.markusbernhardt.proxy.ProxySearch;
import com.github.markusbernhardt.proxy.selector.misc.BufferedProxySelector.CacheScope;

import maven.central.helper.action.impl.ClassSearchAction;
import maven.central.helper.action.impl.GAVSearchAction;
import maven.central.helper.action.impl.GlobalSearchAction;
import maven.central.helper.action.impl.SHA1SearchAction;

public class MavenCentralJsonHelper {

    private static final String MAVEN_SEARCH_URL = "http://search.maven.org";

    public static void main(final String[] args) throws Exception {

        initProxy();

        final JFrame searchFrame = new JFrame("Maven Central Search");
        searchFrame.getContentPane().setLayout(new BoxLayout(searchFrame.getContentPane(), BoxLayout.Y_AXIS));
        final Map<String, JTextField> textFields = new HashMap<>();
        searchFrame.getContentPane().add(createFormPanel(new String[] { KEYWORD }, textFields, "Global Search", new JButton(new GlobalSearchAction(textFields))));
        searchFrame.getContentPane().add(createFormPanel(new String[] { GROUP, ARTIFACT, VERSION }, textFields, "GAV Search", new JButton(new GAVSearchAction(textFields))));
        searchFrame.getContentPane().add(createFormPanel(new String[] { CLASS }, textFields, "Class Search", new JButton(new ClassSearchAction(textFields))));
        searchFrame.getContentPane().add(createFormPanel(new String[] { SHA1 }, textFields, "SHA-1 Search", new JButton(new SHA1SearchAction(textFields))));
        searchFrame.getContentPane().add(createFormPanel(new String[] { PROXY_HOST_LABEL, PROXY_PORT_LABEL, PROXY_USER_LABEL, PROXY_PASS_LABEL }, textFields, "Proxy Settings"));
        searchFrame.setResizable(false);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.pack();
        searchFrame.setSize(300, searchFrame.getHeight());

    }

    public static void initProxy() throws URISyntaxException {
        System.setProperty("java.net.useSystemProxies", "true");
        // Use proxy vole to find the default proxy
        final ProxySearch ps = ProxySearch.getDefaultProxySearch();
        ps.setPacCacheSettings(32, TimeUnit.MINUTES.toMillis(5), CacheScope.CACHE_SCOPE_URL);
        final URI mavenSearchURI = new URI(MAVEN_SEARCH_URL);
        final List<Proxy> proxyList = ps.getProxySelector().select(mavenSearchURI);
        for (final Proxy proxy : proxyList) {
            System.out.println("proxy hostname : " + proxy.type());
            final InetSocketAddress addr = (InetSocketAddress) proxy.address();
            if (addr != null) {
                final String proxyUser = System.getProperty("user.name");
                final String proxyHost = addr.getHostName();
                final String proxyPort = String.valueOf(addr.getPort());
                System.setProperty(ProxyConstants.PROXY_HTTP_HOST_PROPERTY, proxyHost);
                System.setProperty(ProxyConstants.PROXY_HTTPS_HOST_PROPERTY, proxyHost);
                System.setProperty(ProxyConstants.PROXY_HTTP_PORT_PROPERTY, proxyPort);
                System.setProperty(ProxyConstants.PROXY_HTTPS_PORT_PROPERTY, proxyPort);
                System.setProperty(ProxyConstants.PROXY_HTTP_USER_PROPERTY, proxyUser);
                System.setProperty(ProxyConstants.PROXY_HTTPS_USER_PROPERTY, proxyUser);
            }
        }
    }

    private static Component createFormPanel(final String[] labels, final Map<String, JTextField> textFields, final String title) {
        return createFormPanel(labels, textFields, title, null);
    }

    private static JPanel createFormPanel(final String[] labels, final Map<String, JTextField> textFields, final String title, final JButton searchButton) {
        final JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder(title));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        final JPanel gridPanel = new JPanel(new GridLayout(labels.length, 2));
        for (final String labelText : labels) {
            final JLabel label = new JLabel(labelText, SwingConstants.TRAILING);
            gridPanel.add(label);
            final JTextField textField;
            switch (labelText) {
            case PROXY_PASS_LABEL:
                textField = new JPasswordField(System.getProperty(ProxyConstants.PROXY_HTTP_PASSWORD_PROPERTY));
                break;
            case PROXY_USER_LABEL:
                textField = new JTextField(System.getProperty(ProxyConstants.PROXY_HTTP_USER_PROPERTY));
                break;
            case PROXY_HOST_LABEL:
                textField = new JTextField(System.getProperty(ProxyConstants.PROXY_HTTP_HOST_PROPERTY));
                break;
            case PROXY_PORT_LABEL:
                textField = new JTextField(System.getProperty(ProxyConstants.PROXY_HTTP_PORT_PROPERTY));
                break;
            default:
                textField = new JTextField();
                break;
            }
            textFields.put(labelText, textField);
            label.setLabelFor(textField);
            gridPanel.add(textField);
        }
        searchPanel.add(gridPanel);
        if (searchButton != null) {
            final JPanel searchButtonPanel = new JPanel(new BorderLayout());
            searchButtonPanel.add(searchButton, BorderLayout.EAST);
            searchPanel.add(searchButtonPanel);
        }
        return searchPanel;
    }
}
