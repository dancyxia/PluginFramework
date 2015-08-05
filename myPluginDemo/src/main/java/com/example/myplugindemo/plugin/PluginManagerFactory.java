package com.example.myplugindemo.plugin;

/**
 * Created by dancy on 5/21/14.
 */
public class PluginManagerFactory {
    public final static int OSGI_PLUGIN = 0;
    public final static int SHAREID_PLUGIN = 1;
    public final static int REMOTEACCESS_PLUGIN = 2;
    public final static int TYPE_MAX = 3;

    private PluginManagerFactory() {
    }

    public static PluginManager getPluginManager(int type, PluginController controller) {
        switch (type) {
            case OSGI_PLUGIN:
                return new OSGIPluginManager(controller);
            case SHAREID_PLUGIN:
                return new ShareIDPluginManager(controller);
            default:
                return new RemoteAccessPluginManager(controller);
        }
    }
}
