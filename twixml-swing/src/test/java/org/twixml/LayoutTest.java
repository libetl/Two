/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.lang.reflect.Field;

import javax.swing.JPanel;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.twixml.technoproxy.swing.SwingTwiXML;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Test class running layout related tests.
 * 
 * @author Karl Tauber
 */
public class LayoutTest extends TestCase {

    private static final String DESCRIPTOR = "samples/swing/xml/layout.xml";

    /**
     * Asserts that two double[] are equal.
     */
    private static void assertEquals (final String message,
            final double [] expected, final double [] actual) {
        if (expected == null) {
            TestCase.assertNull (message, actual);
        } else {
            TestCase.assertNotNull (message, actual);
        }

        if (expected != null) {
            TestCase.assertEquals (message + ": length ", expected.length,
                    actual.length);
            for (int i = 0 ; i < expected.length ; i++) {
                TestCase.assertEquals (message + "[" + i + "] ", expected [i],
                        actual [i]);
            }
        }
    }

    /**
     * Asserts that two int[] are equal.
     */
    private static void assertEquals (final String message,
            final int [] expected, final int [] actual) {
        if (expected == null) {
            TestCase.assertNull (message, actual);
        } else {
            TestCase.assertNotNull (message, actual);
        }

        if (expected != null) {
            TestCase.assertEquals (message + ": length ", expected.length,
                    actual.length);
            for (int i = 0 ; i < expected.length ; i++) {
                TestCase.assertEquals (message + "[" + i + "] ", expected [i],
                        actual [i]);
            }
        }
    }

    /**
     * Asserts that two int[][] are equal.
     */
    private static void assertEquals (final String message,
            final int [][] expected, final int [][] actual) {
        if (expected == null) {
            TestCase.assertNull (message, actual);
        } else {
            TestCase.assertNotNull (message, actual);
        }

        if (expected != null) {
            TestCase.assertEquals (message + ": length ", expected.length,
                    actual.length);
            for (int i = 0 ; i < expected.length ; i++) {
                LayoutTest.assertEquals (message + "[" + i + "] ",
                        expected [i], actual [i]);
            }
        }
    }

    private static void checkBorderLayout (final String message,
            final JPanel borderPanel, final int hgap, final int vgap) {
        final LayoutManager layout = borderPanel.getLayout ();
        TestCase.assertTrue (message, layout instanceof BorderLayout);

        final BorderLayout borderLayout = (BorderLayout) layout;
        TestCase.assertEquals (message + ": hgap ", hgap,
                borderLayout.getHgap ());
        TestCase.assertEquals (message + ": vgap ", vgap,
                borderLayout.getVgap ());
    }

    private static void checkCardLayout (final String message,
            final JPanel cardPanel, final int hgap, final int vgap) {
        final LayoutManager layout = cardPanel.getLayout ();
        TestCase.assertTrue (message, layout instanceof CardLayout);

        final CardLayout cardLayout = (CardLayout) layout;
        TestCase.assertEquals (message + ": hgap ", hgap, cardLayout.getHgap ());
        TestCase.assertEquals (message + ": vgap ", vgap, cardLayout.getVgap ());
    }

    private static void checkFlowLayout (final String message,
            final JPanel flowPanel, final int align, final int hgap,
            final int vgap) {
        final LayoutManager layout = flowPanel.getLayout ();
        TestCase.assertTrue (message, layout instanceof FlowLayout);

        final FlowLayout flowLayout = (FlowLayout) layout;
        TestCase.assertEquals (message + ": alignment ", align,
                flowLayout.getAlignment ());
        TestCase.assertEquals (message + ": hgap ", hgap, flowLayout.getHgap ());
        TestCase.assertEquals (message + ": vgap ", vgap, flowLayout.getVgap ());
    }

    private static void checkFormLayout (final String message,
            final JPanel formPanel, final String encodedColumnSpecs,
            final String encodedRowSpecs, final int [][] columnGroupIndices,
            final int [][] rowGroupIndices, final String... expectedConstraints) {
        final LayoutManager layout = formPanel.getLayout ();
        TestCase.assertTrue (message, layout instanceof FormLayout);

        final ColumnSpec [] expectedColumnSpecs = ColumnSpec
                .decodeSpecs (encodedColumnSpecs);
        final RowSpec [] expectedRowSpecs = RowSpec
                .decodeSpecs (encodedRowSpecs);

        final FormLayout formLayout = (FormLayout) layout;
        final int columnCount = formLayout.getColumnCount ();
        final int rowCount = formLayout.getRowCount ();
        final int componentCount = formPanel.getComponentCount ();

        TestCase.assertEquals (message + ": columnCount ",
                expectedColumnSpecs.length, columnCount);
        TestCase.assertEquals (message + ": rowCount ",
                expectedRowSpecs.length, rowCount);
        TestCase.assertEquals (message + ": componentCount ",
                expectedConstraints.length, componentCount);

        // check column specs
        for (int i = 0 ; i < columnCount ; i++) {
            final ColumnSpec spec = formLayout.getColumnSpec (i + 1);
            TestCase.assertEquals (message + ": column[" + i + "] ",
                    expectedColumnSpecs [i].toString (), spec.toString ());
        }

        // check row specs
        for (int i = 0 ; i < rowCount ; i++) {
            final RowSpec spec = formLayout.getRowSpec (i + 1);
            TestCase.assertEquals (message + ": row[" + i + "] ",
                    expectedRowSpecs [i].toString (), spec.toString ());
        }

        // check column and row groups
        LayoutTest.assertEquals (message + ": columnGroups ",
                columnGroupIndices, formLayout.getColumnGroups ());
        LayoutTest.assertEquals (message + ": rowGroups ", rowGroupIndices,
                formLayout.getRowGroups ());

        // check component constraints
        for (int i = 0 ; i < componentCount ; i++) {
            final CellConstraints cc = formLayout.getConstraints (formPanel
                    .getComponent (i));
            TestCase.assertEquals (message + ": cc[" + i + "] ",
                    new CellConstraints (expectedConstraints [i]).toString (),
                    cc.toString ());
        }
    }

    private static void checkGridBagConstraints (final String message,
            final GridBagConstraints expected, final GridBagConstraints actual) {
        TestCase.assertEquals (message + ": gridx ", expected.gridx,
                actual.gridx);
        TestCase.assertEquals (message + ": gridy ", expected.gridy,
                actual.gridy);
        TestCase.assertEquals (message + ": gridwidth ", expected.gridwidth,
                actual.gridwidth);
        TestCase.assertEquals (message + ": gridheight ", expected.gridheight,
                actual.gridheight);
        TestCase.assertEquals (message + ": weightx ", expected.weightx,
                actual.weightx);
        TestCase.assertEquals (message + ": weighty ", expected.weighty,
                actual.weighty);
        TestCase.assertEquals (message + ": anchor ", expected.anchor,
                actual.anchor);
        TestCase.assertEquals (message + ": fill ", expected.fill, actual.fill);
        TestCase.assertEquals (message + ": insets.top ", expected.insets.top,
                actual.insets.top);
        TestCase.assertEquals (message + ": insets.left ",
                expected.insets.left, actual.insets.left);
        TestCase.assertEquals (message + ": insets.bottom ",
                expected.insets.bottom, actual.insets.bottom);
        TestCase.assertEquals (message + ": insets.right ",
                expected.insets.right, actual.insets.right);
        TestCase.assertEquals (message + ": ipadx ", expected.ipadx,
                actual.ipadx);
        TestCase.assertEquals (message + ": ipady ", expected.ipady,
                actual.ipady);
    }

    private static void checkGridBagLayout (final String message,
            final JPanel gridBagPanel, final int columnWidths[],
            final int rowHeights[], final double columnWeights[],
            final double rowWeights[]) {
        final LayoutManager layout = gridBagPanel.getLayout ();
        TestCase.assertTrue (message, layout instanceof GridBagLayout);

        final GridBagLayout gridBagLayout = (GridBagLayout) layout;
        LayoutTest.assertEquals (message + ": columnWidths ", columnWidths,
                gridBagLayout.columnWidths);
        LayoutTest.assertEquals (message + ": rowHeights ", rowHeights,
                gridBagLayout.rowHeights);
        LayoutTest.assertEquals (message + ": columnWeights ", columnWeights,
                gridBagLayout.columnWeights);
        LayoutTest.assertEquals (message + ": rowWeights ", rowWeights,
                gridBagLayout.rowWeights);
    }

    private static void checkGridLayout (final String message,
            final JPanel gridPanel, final int rows, final int cols,
            final int hgap, final int vgap) {
        final LayoutManager layout = gridPanel.getLayout ();
        TestCase.assertTrue (message, layout instanceof GridLayout);

        final GridLayout gridLayout = (GridLayout) layout;
        TestCase.assertEquals (message + ": rows ", rows, gridLayout.getRows ());
        TestCase.assertEquals (message + ": cols ", cols,
                gridLayout.getColumns ());
        TestCase.assertEquals (message + ": hgap ", hgap, gridLayout.getHgap ());
        TestCase.assertEquals (message + ": vgap ", vgap, gridLayout.getVgap ());
    }

    private Container container;
    // auto bound through Swixml
    private JPanel    borderPanel1;
    private JPanel    borderPanel2;
    private JPanel    borderPanel3;
    private JPanel    borderPanel10;
    private JPanel    cardPanel1;
    private JPanel    cardPanel2;
    private JPanel    cardPanel3;
    private JPanel    cardPanel10;
    private JPanel    flowPanel1;
    private JPanel    flowPanel2;
    private JPanel    flowPanel3;
    private JPanel    flowPanel4;
    private JPanel    gridPanel1;
    private JPanel    gridPanel2;
    private JPanel    gridPanel3;
    private JPanel    gridPanel4;

    private JPanel    gridPanel5;

    private JPanel    gridBagPanel1;

    private JPanel    gridBagPanel2;

    private JPanel    gridBagPanel3;

    private JPanel    gridBagPanel4;

    private JPanel    gridBagPanel5;

    private JPanel    gridBagPanel6;

    private JPanel    gridBagPanel10;

    private JPanel    formPanel1;

    private JPanel    formPanel2;

    public LayoutTest () {
        super ("Test layout related things");
    }

    /**
     * Renders the test GUI into the container field.<br>
     * Note: Like with every testcase, the setup method is going to be performed
     * before the execution of every test..() method.
     * 
     * @throws Exception
     */
    @Override
    public void setUp () throws Exception {
        AppConstants.DEBUG_MODE = true;
        final TwiXML se = new SwingTwiXML (this);
        this.container = (Container) se.render (LayoutTest.DESCRIPTOR);

    }

    /**
     * Clears the container
     */
    public void teardown () {
        this.container.removeAll ();
        this.container = null;
    }

    /**
     * Tests the BorderLayoutConverter
     */
    public void testBorderLayout () {
        TestCase.assertNotNull (
                "JPanel borderPanel1 auto bound through Swixml",
                this.borderPanel1);
        TestCase.assertNotNull (
                "JPanel borderPanel2 auto bound through Swixml",
                this.borderPanel2);
        TestCase.assertNotNull (
                "JPanel borderPanel3 auto bound through Swixml",
                this.borderPanel3);
        TestCase.assertNotNull (
                "JPanel borderPanel10 auto bound through Swixml",
                this.borderPanel10);

        LayoutTest.checkBorderLayout ("borderPanel1", this.borderPanel1, 0, 0);
        LayoutTest.checkBorderLayout ("borderPanel2", this.borderPanel2, 1, 2);
        LayoutTest.checkBorderLayout ("borderPanel3", this.borderPanel3, 1, 2);

        // check constraints
        final BorderLayout borderLayout10 = (BorderLayout) this.borderPanel10
                .getLayout ();
        TestCase.assertEquals (BorderLayout.NORTH, borderLayout10
                .getConstraints (this.borderPanel10.getComponent (0)));
        TestCase.assertEquals (BorderLayout.EAST, borderLayout10
                .getConstraints (this.borderPanel10.getComponent (1)));
        TestCase.assertEquals (BorderLayout.SOUTH, borderLayout10
                .getConstraints (this.borderPanel10.getComponent (2)));
        TestCase.assertEquals (BorderLayout.WEST, borderLayout10
                .getConstraints (this.borderPanel10.getComponent (3)));
        TestCase.assertEquals (BorderLayout.CENTER, borderLayout10
                .getConstraints (this.borderPanel10.getComponent (4)));
    }

    /**
     * Tests the CardLayoutConverter
     */
    public void testCardLayout () {
        TestCase.assertNotNull ("JPanel cardPanel1 auto bound through Swixml",
                this.cardPanel1);
        TestCase.assertNotNull ("JPanel cardPanel2 auto bound through Swixml",
                this.cardPanel2);
        TestCase.assertNotNull ("JPanel cardPanel3 auto bound through Swixml",
                this.cardPanel3);
        TestCase.assertNotNull ("JPanel cardPanel10 auto bound through Swixml",
                this.cardPanel10);

        LayoutTest.checkCardLayout ("cardPanel1", this.cardPanel1, 0, 0);
        LayoutTest.checkCardLayout ("cardPanel2", this.cardPanel2, 1, 2);
        LayoutTest.checkCardLayout ("cardPanel3", this.cardPanel3, 1, 2);

        // check card names
        final CardLayout cardLayout10 = (CardLayout) this.cardPanel10
                .getLayout ();
        try {
            // Because CardLayout has no API to access card names,
            // use reflection to access private field.
            final Field currentCardField = cardLayout10.getClass ()
                    .getDeclaredField ("currentCard");
            currentCardField.setAccessible (true);

            cardLayout10.show (this.cardPanel10, "card2");
            TestCase.assertEquals (1, currentCardField.getInt (cardLayout10));

            cardLayout10.show (this.cardPanel10, "card1");
            TestCase.assertEquals (0, currentCardField.getInt (cardLayout10));
        } catch (final Exception ex) {
            ex.printStackTrace ();
            throw new AssertionFailedError (ex.getMessage ());
        }
    }

    /**
     * Tests the FlowLayoutConverter
     */
    public void testFlowLayout () {
        TestCase.assertNotNull ("JPanel flowPanel1 auto bound through Swixml",
                this.flowPanel1);
        TestCase.assertNotNull ("JPanel flowPanel2 auto bound through Swixml",
                this.flowPanel2);
        TestCase.assertNotNull ("JPanel flowPanel3 auto bound through Swixml",
                this.flowPanel3);
        TestCase.assertNotNull ("JPanel flowPanel4 auto bound through Swixml",
                this.flowPanel4);

        LayoutTest.checkFlowLayout ("flowPanel1", this.flowPanel1,
                FlowLayout.CENTER, 5, 5);
        LayoutTest.checkFlowLayout ("flowPanel2", this.flowPanel2,
                FlowLayout.RIGHT, 5, 5);
        LayoutTest.checkFlowLayout ("flowPanel3", this.flowPanel3,
                FlowLayout.LEFT, 1, 2);
        LayoutTest.checkFlowLayout ("flowPanel4", this.flowPanel4,
                FlowLayout.LEFT, 1, 2);
    }

    public void testFormLayout () {
        TestCase.assertNotNull ("JPanel formPanel1 auto bound through Swixml",
                this.formPanel1);
        TestCase.assertNotNull ("JPanel formPanel2 auto bound through Swixml",
                this.formPanel2);

        LayoutTest.checkFormLayout ("formPanel1", this.formPanel1,
                "p, 3dlu, p, 3dlu, p", "p, 3dlu, p, 3dlu, p", new int [0] [],
                new int [0] [], "1,3", "3,3,3,1");

        LayoutTest.checkFormLayout ("formPanel2", this.formPanel2,
                "p, p, p, p, p, p, p, p, p, p", "p, p, p, p, p, p, p, p, p, p",
                new int [] [] { { 1, 3, 5, 7 }, { 9, 2, 4 }, { 6, 8, 10 } },
                new int [] [] { { 6, 8, 10 }, { 2, 4, 9 }, { 1, 3, 5, 7 } });
    }

    /**
     * Tests the GridBagLayoutConverter
     */
    public void testGridBagLayout () {
        TestCase.assertNotNull (
                "JPanel gridBagPanel1 auto bound through Swixml",
                this.gridBagPanel1);
        TestCase.assertNotNull (
                "JPanel gridBagPanel2 auto bound through Swixml",
                this.gridBagPanel2);
        TestCase.assertNotNull (
                "JPanel gridBagPanel3 auto bound through Swixml",
                this.gridBagPanel3);
        TestCase.assertNotNull (
                "JPanel gridBagPanel4 auto bound through Swixml",
                this.gridBagPanel4);
        TestCase.assertNotNull (
                "JPanel gridBagPanel5 auto bound through Swixml",
                this.gridBagPanel5);
        TestCase.assertNotNull (
                "JPanel gridBagPanel6 auto bound through Swixml",
                this.gridBagPanel6);
        TestCase.assertNotNull (
                "JPanel gridBagPanel10 auto bound through Swixml",
                this.gridBagPanel10);

        LayoutTest.checkGridBagLayout ("gridBagPanel1", this.gridBagPanel1,
                null, null, null, null);
        LayoutTest.checkGridBagLayout ("gridBagPanel2", this.gridBagPanel2,
                new int [] { 1, 2, 3 }, null, null, null);
        LayoutTest.checkGridBagLayout ("gridBagPanel3", this.gridBagPanel3,
                null, new int [] { 4, 5, 6 }, null, null);
        LayoutTest.checkGridBagLayout ("gridBagPanel4", this.gridBagPanel4,
                null, null, new double [] { 0.1, 0.2, 1.0 }, null);
        LayoutTest.checkGridBagLayout ("gridBagPanel5", this.gridBagPanel5,
                null, null, null, new double [] { 0.3, 0.4 });
        LayoutTest.checkGridBagLayout ("gridBagPanel6", this.gridBagPanel6,
                new int [] { 1, 2, 3 }, new int [] { 4, 5, 6 }, new double [] {
                        0.1, 0.2, 1.0 }, new double [] { 0.3, 0.4 });

        final GridBagLayout gridBagLayout10 = (GridBagLayout) this.gridBagPanel10
                .getLayout ();
        LayoutTest.checkGridBagConstraints ("gridBagPanel10 [0]",
                new GridBagConstraints (), gridBagLayout10
                        .getConstraints (this.gridBagPanel10.getComponent (0)));
        LayoutTest.checkGridBagConstraints ("gridBagPanel10 [1]",
                new GridBagConstraints (1, 2, 3, 4, 0.1, 1.0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL, new Insets (1, 2, 3, 4),
                        5, 6), gridBagLayout10
                        .getConstraints (this.gridBagPanel10.getComponent (1)));
        final GridBagConstraints gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 4;
        LayoutTest.checkGridBagConstraints ("gridBagPanel10 [2]", gbc,
                gridBagLayout10.getConstraints (this.gridBagPanel10
                        .getComponent (2)));
    }

    /**
     * Tests the GridLayoutConverter
     */
    public void testGridLayout () {
        TestCase.assertNotNull ("JPanel gridPanel1 auto bound through Swixml",
                this.gridPanel1);
        TestCase.assertNotNull ("JPanel gridPanel2 auto bound through Swixml",
                this.gridPanel2);
        TestCase.assertNotNull ("JPanel gridPanel3 auto bound through Swixml",
                this.gridPanel3);
        TestCase.assertNotNull ("JPanel gridPanel4 auto bound through Swixml",
                this.gridPanel4);
        TestCase.assertNotNull ("JPanel gridPanel5 auto bound through Swixml",
                this.gridPanel5);

        LayoutTest.checkGridLayout ("gridPanel1", this.gridPanel1, 1, 0, 0, 0);
        LayoutTest.checkGridLayout ("gridPanel2", this.gridPanel2, 2, 3, 0, 0);
        LayoutTest.checkGridLayout ("gridPanel3", this.gridPanel3, 4, 5, 6, 7);
        LayoutTest.checkGridLayout ("gridPanel4", this.gridPanel4, 2, 3, 0, 0);
        LayoutTest.checkGridLayout ("gridPanel5", this.gridPanel5, 4, 5, 6, 7);
    }
}
