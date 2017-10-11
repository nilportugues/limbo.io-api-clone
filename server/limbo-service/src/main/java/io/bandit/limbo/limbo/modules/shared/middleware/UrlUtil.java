package io.bandit.limbo.limbo.modules.shared.middleware;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class for Url creation.
 */
public final class UrlUtil {

    private UrlUtil(){
    }


    public static String getBaseUrl(HttpServletRequest context) {
        String serverUrl = "";

        try {
            int port = context.getServerPort();

            if (context.getScheme().equals("http") && port == 80) {
                port = -1;
            } else if (context.getScheme().equals("https") && port == 443) {
                port = -1;
            }

            serverUrl = new URL(context.getScheme(), context.getServerName(), port, "").toString();
        } catch (MalformedURLException ignored) {

        }

        return serverUrl;
    }
}
