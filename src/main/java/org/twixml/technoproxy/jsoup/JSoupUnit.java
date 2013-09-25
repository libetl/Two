package org.twixml.technoproxy.jsoup;

import java.util.HashMap;
import java.util.Map;

import org.twixml.technoproxy.PlatformUnit;
import org.twixml.technoproxy.ProxyCode;
import org.twixml.technoproxy.jsoup.converter.FontConverter;
import org.twixml.technoproxy.jsoup.converter.ImageConverter;
import org.twixml.technoproxy.jsoup.converter.KeyStrokeConverter;
import org.twixml.technoproxy.jsoup.layoutconverters.FormLayoutConverter;
import org.twixml.technoproxy.jsoup.layoutconverters.GridBagLayoutConverter;

public class JSoupUnit implements PlatformUnit {

    private final Map<String, Class<? extends ProxyCode<?>>> proxyClasses = new HashMap<String, Class<? extends ProxyCode<?>>> () {
                                                                              /**
         * 
         */
                                                                              private static final long serialVersionUID = -6051639057164290313L;

                                                                              {
                                                                                  this.put (
                                                                                          org.twixml.technoproxy.jsoup.JSoupTwiXML.class
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
