package io.github.kosyakmakc.socialBridgeTelegram.Utils;

import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Map;

public class ProxyDefinition {
    private static Map<Proxy.Type, Integer> defaultPorts = Map.of(
        Proxy.Type.SOCKS, 1080,
        Proxy.Type.HTTP, 443
    );

    Proxy.Type proxyType;
    String username;
    String password;
    String hostname;
    int port;

    public ProxyDefinition(String definition) {
        if (definition.isBlank()) {
            this.proxyType = Proxy.Type.DIRECT;
            this.username = "";
            this.password = "";
            this.hostname = "";
            this.port = -1;
            return;
        }

        var proxyTypeIndex = definition.indexOf("://");
        if (proxyTypeIndex >= 0) {
            var rawProxyType = definition.substring(0, proxyTypeIndex);
            definition = definition.substring(proxyTypeIndex + 3, definition.length());
            this.proxyType = recognizeType(rawProxyType);
        }
        else {
            this.proxyType = Proxy.Type.SOCKS;
        }

        String credentials = "";
        var credentialsIndex = definition.indexOf("@");
        if (credentialsIndex >= 0) {
            credentials = definition.substring(0, credentialsIndex);
            definition = definition.substring(credentialsIndex + 1, definition.length());
        }

        if (credentials.isBlank()) {
            this.username = "";
            this.password = "";
        }
        else {
            var credentialsSeparatorIndex = credentials.indexOf(":");
            if (credentialsSeparatorIndex < 0) {
                this.username = credentials;
                this.password = "";
            }
            else {
                this.username = credentials.substring(0, credentialsSeparatorIndex);
                this.password = credentials.substring(credentialsSeparatorIndex + 1, credentials.length());
            }
        }

        var hostSeparatorIndex = definition.indexOf(":");
        if (hostSeparatorIndex < 0) {
            this.hostname = definition;
            this.port = defaultPorts.get(proxyType);
        }
        else {
            this.hostname = definition.substring(0, hostSeparatorIndex);
            try {
                this.port = Integer.parseInt(definition.substring(hostSeparatorIndex + 1, definition.length()));
                if (this.port < 1 || port > 65535) {
                    throw new RuntimeException("Bad port number");
                }
            }
            catch (Exception e) {
                throw new NumberFormatException("Bad port number");
            }
        }
    }

    private Type recognizeType(String rawProxyType) {
        for (var proxyTypeItem : Proxy.Type.values()) {
            if (proxyTypeItem.name().equalsIgnoreCase(rawProxyType)) {
                return proxyTypeItem;
            }
        }

        return Proxy.Type.DIRECT;
    }

    public Proxy.Type getType() {
        return proxyType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        if (getType() == Proxy.Type.DIRECT) {
            return "";
        }

        var credentialsPart = getUsername().isBlank() ? "" : getUsername() + ":" + getPassword() + "@";
        return getType().toString() + "://" + credentialsPart + getHost() + ":" + getPort();
    }
}
