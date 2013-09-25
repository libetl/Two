$Id: readme.txt,v 1.3 2011/04/24, 05:05:06 LiBe $

Introduction
============

See the web site http://libe.toile-libre.org/twixml/


Installing the build tools
==========================

The TwiXML project uses maven.

The only thing that you have to make sure of is that the "JAVA_HOME"
environment property is set to match the top level directory containing the
JVM you want to use. 

Using TwiXML
============
Add in your pom.xml file the artifact dependency as written below : 
<dependency>
	<groupId>org.twixml</groupId>
	<artifactId>twixml</artifactId>
	<version>1.0</version>
</dependency>
Replace 1.0 with the version you wish to use.
           

Building instructions
=====================

Type mvn install to build from source.

The generated jar file is provided in the target directory.
if everything is right and all the required packages are visible, this action
will generate a file called "twixml.jar" in the "./target" directory.


Build targets
=============

The build system is not only responsible for compiling TwiXML into a jar file,
but is also responsible for creating the HTML documentation in the form of
javadocs.

These are the meaningful targets for this build file:

 - package [default] -> creates ./target/twixml.jar
 - compile -> compiles the source code
 - clean -> restores the distribution to its original and clean state

The TwiXML project
