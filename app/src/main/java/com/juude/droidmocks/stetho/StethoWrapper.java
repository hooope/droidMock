package com.juude.droidmocks.stetho;

import android.app.Application;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder;
import com.juude.droidmocks.OkHttpPlugin;
import com.juude.droidmocks.alarm.AlarmPlugin;
import com.juude.droidmocks.display.DisplayPlugin;
import com.juude.droidmocks.power.PowerManagerPlugin;
import com.juude.droidmocks.files.StoragePlugin;
import com.juude.droidmocks.shortcut.ShortcutMocker;
import com.juude.droidmocks.su.SuPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juude on 16/8/5.
 */
public class StethoWrapper {

    private static List<DumperPlugin> sDumperList = new ArrayList<>();

    public static void onCreate(final Application application) {
        addPlugins(application);
        Stetho.initialize(Stetho.newInitializerBuilder(application)
                .enableWebKitInspector(new InspectorModulesProvider() {
                    @Override
                    public Iterable<ChromeDevtoolsDomain> get() {
                        return new Stetho.DefaultInspectorModulesBuilder(application).runtimeRepl(
                                new JsRuntimeReplFactoryBuilder(application)
                                        // Pass to JavaScript: var foo = "bar";
                                        .addVariable("foo", "bar")
                                        .build()
                        ).finish();
                    }
                })
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return sDumperList;
                    }
                })
                .build());
    }

    private static void addPlugins(Application application) {
        sDumperList.add(new AlarmPlugin(application));
        sDumperList.add(new StoragePlugin(application));
        sDumperList.add(new DisplayPlugin(application));
        sDumperList.add(new PowerManagerPlugin(application));
        sDumperList.add(new SuPlugin());
        sDumperList.add(new ShortcutMocker(application));
        sDumperList.add(new OkHttpPlugin());
    }
}
