package org.twixml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.JFrame;

import junit.framework.TestCase;

//Use that class to extract interfaces from every class of several packages
@Deprecated
public class ExtractInterfaceTest extends TestCase {

    public static String fromClass (final Class<?> clazz,
            final String newPackage) {
        return ExtractInterfaceTest.fromClass (clazz, newPackage,
                Arrays.<String> asList (clazz.getPackage ().getName ()),
                new HashMap<String, String> () {
                    /**
             * 
             */
                    private static final long serialVersionUID = -3357735440589669750L;

                    {
                        this.put (clazz.getPackage ().getName (), newPackage);
                    }
                });
    }

    public static String fromClass (Class<?> clazz, String newPackage,
            List<String> groupPackages, Map<String, String> replacePackages) {
        final StringBuffer sb = new StringBuffer ("package " + newPackage
                + ";\n\n");

        for (final String groupPackage : groupPackages) {
            sb.append ("import "
                    + ExtractInterfaceTest.replacePackageName (groupPackage,
                            replacePackages) + ".*;\n");
        }

        sb.append ("\n");

        sb.append ("public interface ");

        sb.append ("I" + clazz.getSimpleName ());

        if ( (clazz.getInterfaces ().length > 0)
                || ( (clazz.getSuperclass () != null) && !"java.lang.Object"
                        .equals (clazz.getSuperclass ().getName ()))) {

            sb.append (" extends ");
            for (int i = 0 ; i < clazz.getInterfaces ().length ; i++) {
                final Class<?> clazz1 = clazz.getInterfaces () [i];
                if (groupPackages.contains (clazz1.getPackage ().getName ())) {
                    sb.append ("I" + clazz1.getSimpleName ().replace ('$', '.'));
                } else {
                    final String s1 = ExtractInterfaceTest.replacePackageName (
                            clazz1.getName (), replacePackages);
                    if (s1.equals (clazz1.getName ())) {
                        if (clazz1.isArray ()) {
                            sb.append (s1.replace ('$', '.').substring (
                                    2,
                                    s1.substring (s1.lastIndexOf ('.') + 1)
                                            .length () - 1)
                                    + "[]");
                        } else {
                            sb.append (s1.replace ('$', '.'));

                        }
                    } else {
                        if (clazz1.isArray ()) {
                            sb.append (s1.substring (2, s1.lastIndexOf ('.'))
                                    + ".I"
                                    + s1.substring (s1.lastIndexOf ('.') + 1)
                                            .substring (
                                                    0,
                                                    s1.substring (
                                                            s1.lastIndexOf ('.') + 1)
                                                            .length () - 1)
                                    + "[]");
                        } else {
                            sb.append (s1.substring (0, s1.lastIndexOf ('.'))
                                    + ".I"
                                    + s1.substring (s1.lastIndexOf ('.') + 1));
                        }
                    }
                }
                if ( (i + 1) < clazz.getInterfaces ().length) {
                    sb.append (", ");
                }
            }

            if ( (clazz.getInterfaces ().length > 0)
                    && (clazz.getSuperclass () != null)
                    && !"java.lang.Object".equals (clazz.getSuperclass ()
                            .getName ())) {
                sb.append (", ");
            }

            if ( (clazz.getSuperclass () != null)
                    && !"java.lang.Object".equals (clazz.getSuperclass ()
                            .getName ())) {
                final Class<?> clazz1 = clazz.getSuperclass ();
                if (groupPackages.contains (clazz1.getPackage ().getName ())) {
                    sb.append ("I" + clazz1.getSimpleName ().replace ('$', '.'));
                } else {
                    final String s1 = ExtractInterfaceTest.replacePackageName (
                            clazz1.getName (), replacePackages);
                    if (s1.equals (clazz1.getName ())) {
                        if (clazz1.isArray ()) {
                            sb.append (s1.replace ('$', '.').substring (
                                    2,
                                    s1.substring (s1.lastIndexOf ('.') + 1)
                                            .length () - 1)
                                    + "[]");
                        } else {
                            sb.append (s1.replace ('$', '.'));

                        }
                    } else {
                        if (clazz1.isArray ()) {
                            sb.append (s1.substring (2, s1.lastIndexOf ('.'))
                                    + ".I"
                                    + s1.substring (s1.lastIndexOf ('.') + 1)
                                            .substring (
                                                    0,
                                                    s1.substring (
                                                            s1.lastIndexOf ('.') + 1)
                                                            .length () - 1)
                                    + "[]");
                        } else {
                            sb.append (s1.substring (0, s1.lastIndexOf ('.'))
                                    + ".I"
                                    + s1.substring (s1.lastIndexOf ('.') + 1));
                        }
                    }
                }

            }

            sb.append (" {");

            for (final Method m : clazz.getDeclaredMethods ()) {
                if (Modifier.isPublic (m.getModifiers ())) {
                    sb.append ("\n\n  public ");
                    if ( (m.getReturnType ().getPackage () != null)
                            && groupPackages.contains (m.getReturnType ()
                                    .getPackage ().getName ())) {
                        sb.append ("I"
                                + m.getReturnType ().getSimpleName ()
                                        .replace ('$', '.'));
                    } else {
                        final String s1 = ExtractInterfaceTest
                                .replacePackageName (m.getReturnType ()
                                        .getName (), replacePackages);
                        if (s1.equals (m.getReturnType ().getName ())) {
                            if (m.getReturnType ().isArray ()) {
                                if (s1.endsWith (";")) {
                                    sb.append (s1.replace ('$', '.').substring (
                                            2, s1.length () - 1)
                                            + "[]");
                                }
                            } else {
                                sb.append (s1.replace ('$', '.'));

                            }
                        } else {
                            if (m.getReturnType ().isArray ()) {
                                if (s1.endsWith (";")) {
                                    sb.append (s1.substring (2,
                                            s1.lastIndexOf ('.'))
                                            + ".I"
                                            + s1.substring (
                                                    s1.lastIndexOf ('.') + 1)
                                                    .substring (
                                                            0,
                                                            s1.substring (
                                                                    s1.lastIndexOf ('.') + 1)
                                                                    .length () - 1)
                                            + "[]");
                                }
                            } else {
                                sb.append (s1.substring (0,
                                        s1.lastIndexOf ('.'))
                                        + ".I"
                                        + s1.substring (s1.lastIndexOf ('.') + 1));
                            }
                        }
                    }
                    sb.append (" " + m.getName () + " (");

                    for (int i = 0 ; i < m.getParameterTypes ().length ; i++) {
                        final Class<?> parameter = m.getParameterTypes () [i];
                        if ( (parameter.getPackage () != null)
                                && groupPackages.contains (parameter
                                        .getPackage ().getName ())) {
                            sb.append ("I"
                                    + parameter.getSimpleName ().replace ('$',
                                            '.'));
                        } else {
                            final String s1 = ExtractInterfaceTest
                                    .replacePackageName (parameter.getName (),
                                            replacePackages);
                            if (s1.equals (parameter.getName ())) {
                                if (parameter.isArray ()) {
                                    if (s1.endsWith (";")) {
                                        sb.append (s1
                                                .replace ('$', '.')
                                                .substring (2, s1.length () - 1)
                                                + "[]");
                                    }
                                } else {
                                    sb.append (s1.replace ('$', '.'));

                                }
                            } else {
                                if (parameter.isArray ()) {
                                    if (s1.endsWith (";")) {
                                        sb.append (s1.substring (2,
                                                s1.lastIndexOf ('.'))
                                                + ".I"
                                                + s1.substring (
                                                        s1.lastIndexOf ('.') + 1)
                                                        .substring (
                                                                0,
                                                                s1.substring (
                                                                        s1.lastIndexOf ('.') + 1)
                                                                        .length () - 1)
                                                + "[]");
                                    }
                                } else {
                                    sb.append (s1.substring (0,
                                            s1.lastIndexOf ('.'))
                                            + ".I"
                                            + s1.substring (s1
                                                    .lastIndexOf ('.') + 1));
                                }
                            }
                        }
                        sb.append (" arg" + i);
                        if ( (i + 1) < m.getParameterTypes ().length) {
                            sb.append (", ");
                        }
                    }

                    sb.append (")");

                    if (m.getExceptionTypes ().length > 0) {
                        sb.append (" throws ");
                    }
                    for (int i = 0 ; i < m.getExceptionTypes ().length ; i++) {
                        final Class<?> exception = m.getExceptionTypes () [i];
                        if ( (exception.getPackage () != null)
                                && groupPackages.contains (exception
                                        .getPackage ().getName ())) {
                            sb.append (exception.getSimpleName ().replace ('$',
                                    '.'));
                        } else {
                            final String s1 = ExtractInterfaceTest
                                    .replacePackageName (exception.getName (),
                                            replacePackages);
                            if (s1.equals (exception.getName ())) {
                                if (exception.isArray ()) {
                                    if (s1.endsWith (";")) {
                                        sb.append (s1
                                                .replace ('$', '.')
                                                .substring (2, s1.length () - 1)
                                                + "[]");
                                    }
                                } else {
                                    sb.append (s1.replace ('$', '.'));

                                }
                            } else {
                                if (exception.isArray ()) {
                                    if (s1.endsWith (";")) {
                                        sb.append (s1.substring (2,
                                                s1.lastIndexOf ('.'))
                                                + ".I"
                                                + s1.substring (
                                                        s1.lastIndexOf ('.') + 1)
                                                        .substring (
                                                                0,
                                                                s1.substring (
                                                                        s1.lastIndexOf ('.') + 1)
                                                                        .length () - 1)
                                                + "[]");
                                    }
                                } else {
                                    sb.append (s1.substring (0,
                                            s1.lastIndexOf ('.'))
                                            + ".I"
                                            + s1.substring (s1
                                                    .lastIndexOf ('.') + 1));
                                }
                            }
                        }
                        if ( (i + 1) < m.getExceptionTypes ().length) {
                            sb.append (", ");
                        }
                    }
                    sb.append (";");
                }
            }
            sb.append ("\n}");
        } else {
            sb.append (" {}\n");
        }
        return sb.toString ();
    }

    public static URL getClassLocation (Class<?> c) {
        final URL url = c.getResource (c.getSimpleName () + ".class");
        if (url == null) {
            return null;
        }
        String s = url.toExternalForm ();
        // s most likely ends with a /, then the full class name with . replaced
        // with /, and .class. Cut that part off if present. If not also check
        // for backslashes instead. If that's also not present just return null

        String end = "/" + c.getName ().replaceAll ("\\.", "/") + ".class";
        if (s.endsWith (end)) {
            s = s.substring (0, s.length () - end.length ());
        } else {
            end = end.replaceAll ("/", "\\");
            if (s.endsWith (end)) {
                s = s.substring (0, s.length () - end.length ());
            } else {
                return null;
            }
        }
        // s is now the URL of the location, but possibly with jar: in front and
        // a trailing !
        if (s.startsWith ("jar:") && s.endsWith ("!")) {
            s = s.substring (4, s.length () - 1);
        }
        try {
            return new URL (s);
        } catch (final MalformedURLException e) {
            return null;
        }
    }

    public static String replacePackageName (String className,
            Map<String, String> replacePackages) {

        for (final String replacePackage : replacePackages.keySet ()) {
            if (className.contains (replacePackage)) {
                className = className.replaceAll (replacePackage,
                        replacePackages.get (replacePackage));
            }
        }
        return className;
    }

    public void testPackage () throws IOException, ClassNotFoundException {
        final String rootFolder = "/home/lionel/Public/workspace/twixml/src/main/java/";
        /*
         * final List<String> listPackages = Arrays.<String> asList (
         * "javax.swing", "java.awt", "sun.awt");
         */

        final List<String> listPackages = Collections.<String> emptyList ();
        final Map<String, String> replacePackages = new HashMap<String, String> () {
            /**
             * 
             */
            private static final long serialVersionUID = -4024301967045461008L;

            {
                for (final String package1 : listPackages) {
                    this.put (package1, "org.twixml.generic");
                }
            }
        };

        final String jarName = ExtractInterfaceTest.getClassLocation (
                JFrame.class).getFile ();

        final JarInputStream jis = new JarInputStream (new FileInputStream (
                jarName));
        JarEntry jarEntry;
        while ( (jarEntry = jis.getNextJarEntry ()) != null) {
            final String folder = !jarEntry.getName ().contains ("/") ? jarEntry
                    .getName () : jarEntry.getName ()
                    .substring (0, jarEntry.getName ().lastIndexOf ('/'))
                    .replaceAll ("/", ".");
            String isIn = null;
            for (final String package1 : listPackages) {
                if (folder.startsWith (package1)) {
                    isIn = folder.substring (package1.length ());
                }
            }
            if ( (isIn != null) && jarEntry.getName ().endsWith (".class")
                    && !jarEntry.getName ().contains ("$")) {
                final String className = jarEntry
                        .getName ()
                        .replaceAll ("/", ".")
                        .substring (0,
                                jarEntry.getName ().lastIndexOf (".class"));
                try {
                    final Class<?> c = Class.forName (className);
                    if (!Throwable.class.isAssignableFrom (c)) {
                        String jarEntryName = ExtractInterfaceTest
                                .replacePackageName (
                                        jarEntry.getName ().replace ('/', '.'),
                                        replacePackages).replace ('.', '/');
                        jarEntryName = jarEntryName.substring (0,
                                jarEntryName.lastIndexOf ('/'));
                        jarEntryName = jarEntryName.substring (0,
                                jarEntryName.lastIndexOf ('/'));
                        final String outputFolder = rootFolder + jarEntryName
                                + "/";
                        final String outputFileName = outputFolder + "I"
                                + c.getSimpleName () + ".java";
                        new File (outputFolder).mkdirs ();
                        final File output = new File (outputFileName);
                        final FileWriter fw = new FileWriter (output);
                        fw.write (ExtractInterfaceTest.fromClass (c,
                                "org.twixml.generic"
                                        + (isIn != null ? isIn : ""),
                                listPackages, replacePackages));
                        fw.close ();
                    }
                } catch (final java.lang.UnsatisfiedLinkError e) {

                }
            }
        }
        jis.close ();
    }
}
