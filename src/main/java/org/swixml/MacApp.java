/*--
 $Id: MacApp.java,v 1.2 2004/10/05 21:32:35 tichy Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials provided
 with the distribution.

 3. The end-user documentation included with the redistribution,
 if any, must include the following acknowledgment:
        "This product includes software developed by the
         SWIXML Project (http://www.swixml.org/)."
 Alternately, this acknowledgment may appear in the software itself,
 if and wherever such third-party acknowledgments normally appear.

 4. The name "Swixml" must not be used to endorse or promote products
 derived from this software without prior written permission. For
 written permission, please contact <info_AT_swixml_DOT_org>

 5. Products derived from this software may not be called "Swixml",
 nor may "Swixml" appear in their name, without prior written
 permission from the Swixml Project Management.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE SWIXML PROJECT OR ITS
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.
 ====================================================================

 This software consists of voluntary contributions made by many
 individuals on behalf of the Swixml Project and was originally
 created by Wolf Paulus <wolf_AT_swixml_DOT_org>. For more information
 on the Swixml Project, please see <http://www.swixml.org/>.
 */
package org.swixml;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.Action;

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
	private static MacApp	INSTANCE	= null;

	public synchronized static MacApp getInstance () {
		if (MacApp.INSTANCE == null) {
			MacApp.INSTANCE = new MacApp ();
		}
		return MacApp.INSTANCE;
	}

	// ////////////////////////////////////////////////

	Action	aboutAction	 = null;
	Action	fileAction	 = null;
	Action	appAction	 = null;
	Action	prefAction	 = null;
	Action	printAction	 = null;
	Action	reopenAction	= null;
	Action	quitAction	 = null;
	int	   sequence;

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

	public void update (Map action_map) {

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
