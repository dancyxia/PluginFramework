package com.example.myplugindemo.plugin;

import com.example.myplugindemo.db.Plugin;

/**
 * Created by dancy on 5/21/14.
 */
public class PluginManagerFactory {
    private PluginManagerFactory() {
    }

    public static PluginManager getPluginManager(Plugin.PLUGINTYPE type, PluginController controller) {
        switch (type) {
            case OSGIPLUGIN:
                return new OSGIPluginManager(controller);
            case SHAREIDPLUGIN:
                return new ShareIDPluginManager(controller);
            default:
                return new RemoteAccessPluginManager(controller);
        }
    }
}
