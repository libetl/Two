/*--
 $Id: MacApp.java,v 1.2 2004/10/05 21:32:35 tichy Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.swing;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.Action;

import org.twixml.Parser;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

/**
 * A MacApp object acompanies a SwingEngine generated UI, when run on a Mac. The
 * MacApp dispatches MacMenu requests to the registered actions.<BR>
 * It remains in the responsibility of the main application that the registered
 * Action are available and fully funtional (loaded).
 */
@SuppressWarnings ("deprecation")
public class MacApp extends Application {

    private static MacApp INSTANCE = null;

    public synchronized static MacApp getInstance () {
        if (MacApp.INSTANCE == null) {
            MacApp.INSTANCE = new MacApp ();
        }
        return MacApp.INSTANCE;
    }

    // ////////////////////////////////////////////////

    Action aboutAction  = null;
    Action fileAction   = null;
    Action appAction    = null;
    Action prefAction   = null;
    Action printAction  = null;
    Action reopenAction = null;
    Action quitAction   = null;
    int    sequence;

    private MacApp () {
        this.sequence = 0;
        this.addApplicationListener (new ApplicationAdapter () {

            @Override
            public void handleAbout (ApplicationEvent event) {
                if (MacApp.this.aboutAction != null) {
                    event.setHandled (true);
                    MacApp.this.aboutAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_ABOUT));
                } else {
                    super.handleAbout (event);
                }
            }

            @Override
            public void handleOpenApplication (ApplicationEvent event) {
                if (MacApp.this.appAction != null) {
                    MacApp.this.appAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_OPENAPP));
                    event.setHandled (true);
                } else {
                    super.handleOpenApplication (event);
                }
            }

            @Override
            public void handleOpenFile (ApplicationEvent event) {
                if (MacApp.this.fileAction != null) {
                    MacApp.this.fileAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_OPENFILE));
                    event.setHandled (true);
                } else {
                    super.handleOpenFile (event);
                }
            }

            @Override
            public void handlePreferences (ApplicationEvent event) {
                if (MacApp.this.prefAction != null) {
                    MacApp.this.prefAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_PREF));
                    event.setHandled (true);
                } else {
                    super.handlePreferences (event);
                }
            }

            @Override
            public void handlePrintFile (ApplicationEvent event) {
                if (MacApp.this.printAction != null) {
                    MacApp.this.printAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_PRINT));
                    event.setHandled (true);
                } else {
                    super.handlePrintFile (event);
                }
            }

            @Override
            public void handleQuit (ApplicationEvent event) {
                if (MacApp.this.quitAction != null) {
                    MacApp.this.quitAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_QUIT));
                    event.setHandled (true);
                } else {
                    super.handleQuit (event);
                }
            }

            @Override
            public void handleReOpenApplication (ApplicationEvent event) {
                if (MacApp.this.reopenAction != null) {
                    MacApp.this.reopenAction.actionPerformed (new ActionEvent (
                            event.getSource (), MacApp.this.sequence++,
                            Parser.ATTR_MACOS_OPENAPP));
                    event.setHandled (true);
                } else {
                    super.handleReOpenApplication (event);
                }
            }

        });
    }

    public void update (Map<String, Object> action_map) {

        if (action_map.containsKey (Parser.ATTR_MACOS_ABOUT)) {
            this.aboutAction = (Action) action_map
                    .get (Parser.ATTR_MACOS_ABOUT);
            this.setEnabledAboutMenu (this.aboutAction != null);
        }
        if (action_map.containsKey (Parser.ATTR_MACOS_PREF)) {
            this.prefAction = (Action) action_map.get (Parser.ATTR_MACOS_PREF);
            this.setEnabledPreferencesMenu (this.prefAction != null);
        }
        if (action_map.containsKey (Parser.ATTR_MACOS_OPENAPP)) {
            this.appAction = (Action) action_map
                    .get (Parser.ATTR_MACOS_OPENAPP);
        }
        if (action_map.containsKey (Parser.ATTR_MACOS_REOPEN)) {
            this.reopenAction = (Action) action_map
                    .get (Parser.ATTR_MACOS_REOPEN);
        }
        if (action_map.containsKey (Parser.ATTR_MACOS_OPENFILE)) {
            this.fileAction = (Action) action_map
                    .get (Parser.ATTR_MACOS_OPENFILE);
        }
        if (action_map.containsKey (Parser.ATTR_MACOS_PRINT)) {
            this.printAction = (Action) action_map
                    .get (Parser.ATTR_MACOS_PRINT);
        }
        if (action_map.containsKey (Parser.ATTR_MACOS_QUIT)) {
            this.quitAction = (Action) action_map.get (Parser.ATTR_MACOS_QUIT);
        }
    }

}
