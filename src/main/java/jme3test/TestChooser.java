/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jme3test;

import com.jme3.app.Application;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Class with a main method that displays a dialog to choose any jME demo to be
 * started.
 */
public class TestChooser extends JDialog {
    private static final Logger logger = Logger.getLogger(TestChooser.class
            .getName());

    private static final long serialVersionUID = 1L;

    /**
     * Only accessed from EDT
     */
    private Class<?> selectedClass = null;

    /**
     * Constructs a new TestChooser that is initially invisible.
     */
    public TestChooser() throws HeadlessException {
        super((JFrame) null, "TestChooser", true);
    }

    /**
     * @param classes
     *            vector that receives the found classes
     * @return classes vector, list of all the classes in a given package (must
     *         be found in classpath).
     */
    protected Vector<Class> find(String pckgname, boolean recursive,
            Vector<Class> classes) {
        URL url;

        // Translate the package name into an absolute path
        String name = pckgname;
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        name = name.replace('.', '/');

        // Get a File object for the package
        // URL url = UPBClassLoader.get().getResource(name);
        url = this.getClass().getResource(name);
        // URL url = ClassLoader.getSystemClassLoader().getResource(name);
        pckgname = pckgname + ".";

        File directory;
        try {
            directory = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e); // should never happen
        }

        if (directory.exists()) {
            logger.info("Searching for Demo classes in \""
                    + directory.getName() + "\".");
            addAllFilesInDirectory(directory, classes, pckgname, recursive);
        } else {
            try {
                // It does not work with the filesystem: we must
                // be in the case of a package contained in a jar file.
                logger.info("Searching for Demo classes in \"" + url + "\".");
                URLConnection urlConnection = url.openConnection();
                if (urlConnection instanceof JarURLConnection) {
                    JarURLConnection conn = (JarURLConnection) urlConnection;

                    JarFile jfile = conn.getJarFile();
                    Enumeration e = jfile.entries();
                    while (e.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) e.nextElement();
                        Class result = load(entry.getName());
                        if (result != null && !classes.contains(result)) {
                            classes.add(result);
                        }
                    }
                }
            } catch (IOException e) {
                logger.logp(Level.SEVERE, this.getClass().toString(),
                        "find(pckgname, recursive, classes)", "Exception", e);
            } catch (Exception e) {
                logger.logp(Level.SEVERE, this.getClass().toString(),
                        "find(pckgname, recursive, classes)", "Exception", e);
            }
        }
        return classes;
    }

    /**
     * Load a class specified by a file- or entry-name
     *
     * @param name
     *            name of a file or entry
     * @return class file that was denoted by the name, null if no class or does
     *         not contain a main method
     */
    private Class load(String name) {
        if (name.endsWith(".class")
         && name.indexOf("Test") >= 0
         && name.indexOf('$') < 0) {
            String classname = name.substring(0, name.length()
                    - ".class".length());

            if (classname.startsWith("/")) {
                classname = classname.substring(1);
            }
            classname = classname.replace('/', '.');

            try {
                final Class<?> cls = Class.forName(classname);
                cls.getMethod("main", new Class[] { String[].class });
                if (!getClass().equals(cls)) {
                    return cls;
                }
            } catch (NoClassDefFoundError e) {
                // class has unresolved dependencies
                return null;
            } catch (ClassNotFoundException e) {
                // class not in classpath
                return null;
            } catch (NoSuchMethodException e) {
                // class does not have a main method
                return null;
            }
        }
        return null;
    }

    /**
     * Used to descent in directories, loads classes via {@link #load}
     *
     * @param directory
     *            where to search for class files
     * @param allClasses
     *            add loaded classes to this collection
     * @param packageName
     *            current package name for the diven directory
     * @param recursive
     *            true to descent into subdirectories
     */
    private void addAllFilesInDirectory(File directory,
            Collection<Class> allClasses, String packageName, boolean recursive) {
        // Get the list of the files contained in the package
        File[] files = directory.listFiles(getFileFilter());
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // we are only interested in .class files
                if (files[i].isDirectory()) {
                    if (recursive) {
                        addAllFilesInDirectory(files[i], allClasses,
                                packageName + files[i].getName() + ".", true);
                    }
                } else {
                    Class result = load(packageName + files[i].getName());
                    if (result != null && !allClasses.contains(result)) {
                        allClasses.add(result);
                    }
                }
            }
        }
    }

    /**
     * @return FileFilter for searching class files (no inner classes, only
     *         those with "Test" in the name)
     */
    private FileFilter getFileFilter() {
        return new FileFilter() {
            public boolean accept(File pathname) {
                return (pathname.isDirectory() && !pathname.getName().startsWith("."))
                        || (pathname.getName().endsWith(".class")
                            && (pathname.getName().indexOf("Test") >= 0)
                            && pathname.getName().indexOf('$') < 0);
            }
        };
    }

    private void startApp(final Class<?> appClass){
        if (appClass == null){
            JOptionPane.showMessageDialog(rootPane,
                                          "Please select a test from the list",
                                          "Error", 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        final Object app;
        try {
            app = appClass.newInstance();
            final Method mainMethod = appClass.getMethod("main", (new String[0]).getClass());
            new Thread(new Runnable(){
                public void run(){
                    try {
                        mainMethod.invoke(app, new Object[]{new String[0]});
                    } catch (IllegalAccessException ex) {
                        logger.log(Level.SEVERE, "Cannot access constructor: "+appClass.getName(), ex);
                    } catch (IllegalArgumentException ex) {
                        logger.log(Level.SEVERE, "main() had illegal argument: "+appClass.getName(), ex);
                    } catch (InvocationTargetException ex) {
                        logger.log(Level.SEVERE, "main() method had exception: "+appClass.getName(), ex);
                    }
                }
            }).start();
        } catch (InstantiationException ex) {
            logger.log(Level.SEVERE, "Failed to create app: "+appClass.getName(), ex);
        } catch (IllegalAccessException ex) {
            logger.log(Level.SEVERE, "Cannot access constructor: "+appClass.getName(), ex);
        } catch (NoSuchMethodException ex){
            logger.log(Level.SEVERE, "Test class doesn't have main method: "+appClass.getName(), ex);
        }
    }

    /**
     * Code to create components and action listeners.
     *
     * @param classes
     *            what Classes to show in the list box
     */
    private void setup(Vector<Class> classes) {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        final FilteredJList list = new FilteredJList();
        DefaultListModel model = new DefaultListModel();
        for (Class c : classes) {
            model.addElement(c);
        }
        list.setModel(model);

        mainPanel.add(createSearchPanel(list), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(list), BorderLayout.CENTER);

        list.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        selectedClass = (Class) list.getSelectedValue();
                    }
                });

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        final JButton okButton = new JButton("Ok");
        okButton.setMnemonic('O');
        buttonPanel.add(okButton);
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startApp(selectedClass);
                dispose();
            }
        });

        final JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic('C');
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        center();
    }

    private class FilteredJList extends JList {
        private static final long serialVersionUID = 1L;

        private String filter;
        private ListModel originalModel;

        public void setModel(ListModel m) {
            originalModel = m;
            super.setModel(m);
        }

        private void update() {
            if (filter == null || filter.length() == 0) {
                super.setModel(originalModel);
            }

            DefaultListModel v = new DefaultListModel();
            for (int i = 0; i < originalModel.getSize(); i++) {
                Object o = originalModel.getElementAt(i);
                String s = String.valueOf(o).toLowerCase();
                if (s.contains(filter)) {
                    v.addElement(o);
                }
            }
            super.setModel(v);
            revalidate();
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter.toLowerCase();
            update();
        }
    }

    /**
     * center the frame.
     */
    private void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /**
     * Start the chooser.
     *
     * @param args
     *            command line parameters
     */
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new TestChooser().start(args);
            }
        });
    }

    protected void start(String[] args) {
        final Vector<Class> classes = new Vector<Class>();
        logger.info("Composing Test list...");
        addDisplayedClasses(classes);
        setup(classes);
        Class<?> cls;
        setVisible(true);
    }

    protected void addDisplayedClasses(Vector<Class> classes) {
        find("jme3test", true, classes);
    }

    private JPanel createSearchPanel(final FilteredJList classes) {
        JPanel search = new JPanel();
        search.setLayout(new BorderLayout());
        search.add(new JLabel("Choose a Demo to start:      Find: "),
                BorderLayout.WEST);
        final javax.swing.JTextField jtf = new javax.swing.JTextField();
        jtf.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                classes.setFilter(jtf.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                classes.setFilter(jtf.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                classes.setFilter(jtf.getText());
            }
        });
        jtf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (classes.getModel().getSize() == 1) {
                    selectedClass = (Class) classes.getModel().getElementAt(0);
                    TestChooser.this.dispose();
                }
            }
        });

        search.add(jtf);
        return search;
    }
}
