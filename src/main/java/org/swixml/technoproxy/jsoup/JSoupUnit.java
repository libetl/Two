package org.swixml.technoproxy.jsoup;

import java.util.HashMap;
import java.util.Map;

import org.swixml.technoproxy.ProxyCode;
import org.swixml.technoproxy.PlatformUnit;
import org.swixml.technoproxy.jsoup.converter.FontConverter;
import org.swixml.technoproxy.jsoup.converter.ImageConverter;
import org.swixml.technoproxy.jsoup.converter.KeyStrokeConverter;
import org.swixml.technoproxy.jsoup.layoutconverters.FormLayoutConverter;
import org.swixml.technoproxy.jsoup.layoutconverters.GridBagLayoutConverter;

public class JSoupUnit implements PlatformUnit {

    private final Map<String, Class<? extends ProxyCode<?>>> proxyClasses = new HashMap<String, Class<? extends ProxyCode<?>>> () {
                                                                              /**
         * 
         */
                                                                              private static final long serialVersionUID = -6051639057164290313L;

                                                                              {
                                                                                  this.put (
                                                                                          org.swixml.SwingEngine.class
                                                                                                  .getName (),
                                                                                          SwingEngine.class);
                                                                                  this.put (
                                                                                          org.swixml.Parser.class
                                                                                                  .getName (),
                                                                                          Parser.class);
                                                                                  this.put (
                                                                                          org.swixml.converters.FontConverter.class
                                                                                                  .getName (),
                                                                                          FontConverter.class);
                                                                                  this.put (
                                                                                          org.swixml.converters.ImageConverter.class
                                                                                                  .getName (),
                                                                                                  ImageConverter.class);
                                                                                  this.put (
                                                                                          org.swixml.converters.KeyStrokeConverter.class
                                                                                                  .getName (),
                                                                                                  KeyStrokeConverter.class);
                                                                                  this.put (
                                                                                          org.swixml.layoutconverters.FormLayoutConverter.class
                                                                                                  .getName (),
                                                                                                  FormLayoutConverter.class);
                                                                                  this.put (
                                                                                          org.swixml.layoutconverters.GridBagLayoutConverter.class
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
    public org.swixml.technoproxy.TypeAnalyser getTypeAnalyser () {
        return this.typeAnalyser;

    }

}
