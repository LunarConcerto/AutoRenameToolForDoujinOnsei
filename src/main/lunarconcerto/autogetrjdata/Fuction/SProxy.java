package main.lunarconcerto.autogetrjdata.Fuction;

import main.lunarconcerto.autogetrjdata.Util.DataBase;

import java.util.Properties;

public class SProxy {

    private String proxyHost = "127.0.0.1";
    private String proxyPort = "7890";

    public SProxy() {
        Properties properties = DataBase.getSETTING();
        String proxy_port = properties.getProperty("proxy_port");
        if (proxy_port!=null){
            setProxyPort(proxy_port);
        }
        setProxy();
    }

    private void setProxy(){
        System.getProperties().setProperty("proxySet" , "true");
        System.setProperty("http.proxyHost" , proxyHost);
        System.setProperty("http.proxyPort" , proxyPort);
        System.setProperty("https.proxyHost" , proxyHost);
        System.setProperty("https.proxyPort" , proxyPort);
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        setProxy();
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
        setProxy();
    }
}
