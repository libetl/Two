package org.twixml.technoproxy.swing;

import java.util.HashMap;
import java.util.Map;

import org.twixml.technoproxy.PlatformUnit;
import org.twixml.technoproxy.ProxyCode;
import org.twixml.technoproxy.swing.converter.FontConverter;
import org.twixml.technoproxy.swing.converter.ImageConverter;
import org.twixml.technoproxy.swing.converter.KeyStrokeConverter;
import org.twixml.technoproxy.swing.layoutconverters.BorderLayoutConverter;
import org.twixml.technoproxy.swing.layoutconverters.FormLayoutConverter;
import org.twixml.technoproxy.swing.layoutconverters.GridBagLayoutConverter;

public class SwingUnit implements PlatformUnit {

    private final Map<String, Class<? extends ProxyCode<?>>> proxyClasses = new HashMap<String, Class<? extends ProxyCode<?>>> () {
                                                                              /**
         * 
         */
                                                                              private static final long serialVersionUID = -6051639057164290313L;

                                                                              {
                                                                                  this.put (
                                                                                          org.twixml.technoproxy.swing.SwingTwiXML.class
                                                                                                  .getName (),
                                                                                          TwiXML.class);
                                                                                  this.put (
                                                                                          org.twixml.Parser.class
                                                                                                  .getName (),
                                                                                          Parser.class);
                                                                                  this.put (
                                                                                          org.twixml.SimpleTagLibrary.class
                                                                                                  .getName (),
                                                                                          SimpleTagLibrary.class);
                                                                                  this.put (
                                                                                          org.twixml.converters.FontConverter.class
                                                                                                  .getName (),
                                                                                          FontConverter.class);
                                                                                  this.put (
                                                                                          org.twixml.converters.ImageConverter.class
                                                                                                  .getName (),
                                                                                          ImageConverter.class);
                                                                                  this.put (
                                                                                          org.twixml.converters.KeyStrokeConverter.class
                                                                                                  .getName (),
                                                                                          KeyStrokeConverter.class);
                                                                                  this.put (
                                                                                          org.twixml.layoutconverters.FormLayoutConverter.class
                                                                                                  .getName (),
                                                                                          FormLayoutConverter.class);
                                                                                  this.put (
                                                                                          org.twixml.layoutconverters.GridBagLayoutConverter.class
                                                                                                  .getName (),
                                                                                          GridBagLayoutConverter.class);
                                                                                  this.put (
                                                                                          org.twixml.layoutconverters.BorderLayoutConverter.class
                                                                                                  .getName (),
                                                                                          BorderLayoutConverter.class);
                                                                              }
                                                                          };

    private final TypeAnalyser                               typeAnalyser = new TypeAnalyser ();

    @Override
    public Map<String, Class<? extends ProxyCode<?>>> getProxyClasses () {
        return this.proxyClasses;
    }

    @Override
    public org.twixml.technoproxy.TypeAnalyser getTypeAnalyser () {
        return this.typeAnalyser;

    }

}
