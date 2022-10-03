/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.get;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.Sides;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
//import org.apache.log4j.BasicConfigurator;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.multipdf.LayerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.opendope.questions.Response;

//needed jars: apache poi and it's dependencies
//             and additionally: ooxml-schemas-1.4.jar 
// import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPrDefault.getPPr();
/**
 *
 * @author User
 */
public class filechooser extends JFrame implements ActionListener {

    static JLabel l;
    static pdfDoc document;
    static pdfDoc[] documentS;
    // static JCheckBox pages4;
    // static final PDType1Font TIMES_ROMAN = new PDType1Font(Standard14Fonts.getMappedFontName("TIMES_ITALIC"));
    // a default constructor
    private static JCheckBox pages;
    static JCheckBox shrinkpdfPIC;
    static JCheckBox shrinkpdfnopic;
    private static JComboBox<ImageType> diagnosisList;
    private static JButton saveButton;
    private static JCheckBox splitCachck;
    private static JComboBox<parts> splitList;
    private static JComboBox<DividingPages> pageList;
    private static JButton openButton;
    private static JButton printButton;
    private static JTextArea myPanel;
    private static JFrame f;
    private static JPanel shrinkpanel;
    private static JPanel spltpanel;

    private static JPanel pagesPanel;
    private static JPanel chackBokloly;
    private static JPanel ButtonsPanel;

    filechooser() {
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        mainFormIns();
        formOfchackboxAndComboboxIns();
        formOfButoonsIns();
        addingtoMainform();
        Default();
        showBuotoon(true);
        megsse(l.getText() + " for new pdf plese enter new pdf");
    }

    private static void Default() {
        pages.setSelected(true);
    }

    public static void mainFormIns() throws HeadlessException {
        // frame to contains GUI elements
        f = new JFrame("file chooser");

        // set the size of the frame
        f.setSize(400, 400);

        // set the frame's visibility
        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void addingtoMainform() {
        f.setLayout(new FlowLayout());
        f.getContentPane().add(ButtonsPanel);
        f.getContentPane().add(shrinkpanel);
        f.getContentPane().add(spltpanel);
        f.getContentPane().add(pagesPanel);
        f.getContentPane().add(chackBokloly);
        f.getContentPane().add(myPanel);

        f.pack();
        f.setVisible(true);
    }

    public static void formOfButoonsIns() {
        saveButton = new JButton("save");
        openButton = new JButton("open");
        printButton = new JButton("print");
        // make an object of the class filechooser
        filechooser f1 = new filechooser();
        // add action listener to the button to capture user
        // response on buttons
        saveButton.addActionListener(f1);
        openButton.addActionListener(f1);
        printButton.addActionListener(f1);

        // make a panel to add the buttons and labels
        ButtonsPanel = new JPanel();

        // add buttons to the frame
        ButtonsPanel.add(saveButton);
        ButtonsPanel.add(openButton);
        ButtonsPanel.add(printButton);
        // set the label to its initial value
        l = new JLabel();

        // add panel to the frame
        ButtonsPanel.add(l);
    }

    public static void formOfchackboxAndComboboxIns() throws HeadlessException {
        chackBokloly = new JPanel();
        shrinkpdfPIC = new JCheckBox("Reducing the page and changing colors");
        shrinkpdfPIC.setBounds(100, 100, 50, 50);
        shrinkpdfnopic = new JCheckBox(" pdf to A4");
        shrinkpdfnopic.setBounds(100, 100, 50, 50);
        shrinkpdfPIC = new JCheckBox();
        diagnosisList = new JComboBox<>();
        shrinkpanel = panelofcomoandcheckbox(shrinkpdfPIC, diagnosisList, "srink and cange colors", ImageType.values());
        splitCachck = new JCheckBox();
        splitList = new JComboBox<>();
        spltpanel = panelofcomoandcheckbox(splitCachck, splitList, "split docmnet to parts", parts.values());
        pages = new JCheckBox();
        pageList = new JComboBox<>();
        pagesPanel = panelofcomoandcheckbox(pages, pageList, "how much pages you want?", DividingPages.values());
        myPanel = new JTextArea("drop in me", 5, 5);
        Font font = new Font("Verdana", Font.BOLD, 50);
        myPanel.setFont(font);
        myPanel.setForeground(Color.BLUE);
        dropMetue();
        chackBokloly.setLayout(new FlowLayout());

        chackBokloly.add(shrinkpdfnopic);
    }

    public static JPanel panelofcomoandcheckbox(JCheckBox chackbox, JComboBox m, String injChckbox, Object[] getEnume) {
        JPanel splitpanel = new JPanel();
        splitpanel.setLayout(new FlowLayout());
        chackbox.setText(injChckbox);
        m.setModel(new DefaultComboBoxModel<>(getEnume));
        m.setVisible(false);
        makeColoraApper(chackbox, m);
        splitpanel.add(chackbox);
        splitpanel.add(m);
        return splitpanel;
    }

    public static void chackboxStarting() {

    }

//
//    }
    public static void dropMetue() throws HeadlessException {
        myPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        doTheWork(file);
                        
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void makeColoraApper(JCheckBox g, JComboBox m) {
        g.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                m.setVisible(true);
                f.pack();

            } else {//checkbox has been deselected
                m.setVisible(false);
                f.pack();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();

        switch (com) {
            case "save" -> {
                savaAfile();
                // eltrntveSaveing();
                l.setText(l.getText() + " saved");
                showBuotoon(true);
                document = null;
            }
            case "open" -> {
                try {
                    openAfile();
                } catch (Exception ex) {
                    Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
                }
                showBuotoon(false);
            }
            case "print" -> {
                try {
                    document.printfile(getpagesneeds());

                } catch (PrinterException ex) {
                    Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
                }
                showBuotoon(true);
                document = new pdfDoc();
            }
            default -> {
            }
        }
        // if the user presses the open dialog show the open dialog

    }

    public static void showBuotoon(boolean rest) {
        openButton.setVisible(rest);
        saveButton.setVisible(!rest);
        printButton.setVisible(!rest);
        myPanel.setVisible(rest);
        shrinkpdfPIC.setVisible(rest);
        shrinkpdfnopic.setVisible(rest);
        // diagnosisList.setVisible(rest);
        shrinkpanel.setVisible(rest);
        spltpanel.setVisible(rest);

        pagesPanel.setVisible(rest);
        f.pack();

    }

     public void openAfile() throws HeadlessException, Exception {
        // create an object of JFileChooser class
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // resctrict the user to select files of all types
        j.setAcceptAllFileFilterUsed(false);

        // set a title for the dialog
        j.setDialogTitle("Select a .pdf or pptx file");

//         only allow files of .txt extension
//        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .pdf files", "pdf");
//        j.addChoosableFileFilter(restrict);
//         invoke the showsOpenDialog function to show the save dialog
        int r = j.showOpenDialog(null);

        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            diagnosisList.setVisible(false);
            megsse("making the doc");
            File file = new File(j.getSelectedFile().getAbsolutePath());
            doTheWork(file);

        } // if the user cancelled the operation
        else {
            megsse("the user cancelled the operation");
        }
    }

    public static  void doTheWork(File file) throws Exception {
        document = new pdfDoc();
        if (splitCachck.isSelected())
        {
            documentS = pdfDoc.LodeAndMakeADocS(file, file.getAbsolutePath(), getpagesneeds(), l, shrinkpdfnopic.isSelected(), getimgetype(), getsplittype());
            
        } else {
            document.loudAndMakeDoc(file, file.getAbsolutePath(), getpagesneeds(), l, shrinkpdfnopic.isSelected(), getimgetype());
        }
        showBuotoon(false);
    }

    public void savaAfile() throws HeadlessException {
        // create an object of JFileChooser class
        JFileChooser elegidor = new JFileChooser();
        File originalFile = new File(System.getProperty("user.dir"), "filePDF.pdf");
        // set a title for the dialog
        elegidor.setDialogTitle("save aa .pdf file");

        // only allow files of .txt extension
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .pdf files", "pdf");
        elegidor.addChoosableFileFilter(restrict);
        elegidor.setCurrentDirectory(new File(System.getProperty("user.dir")));

        elegidor.setSelectedFile(originalFile);

        elegidor.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // invoke the showsSaveDialog function to show the save dialog
        int r = elegidor.showSaveDialog(null);

        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // set the label to the path of the selected file

            l.setText(pdfDoc.makingPDFend(elegidor.getSelectedFile().getAbsolutePath()));
            try {
                if(documentS!=null)
                {
                    for (int i = 0; i < documentS.length; i++) 
                    {
                        String name =makingPartEnd(l.getText(),i);
                        pdfDoc.StraterPage( name, documentS[i], i);
                        documentS[i].save(new File(name));
                    }
                }
                else
                {
                document.save(new File(l.getText()));
                l.setText(l.getText() + " saved");
                }
            } catch (IOException ex) {
                Logger.getLogger(filechooser.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                document.close();
            } catch (IOException ex) {
                Logger.getLogger(filechooser.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } // if the user cancelled the operation
        else {
            l.setText("the user cancelled the operation");
        }
    }

    public static void megsse(String g) {

        l.setText(g);
        l.paintImmediately(l.getVisibleRect());
        f.pack();
    }

    // בהתחלה לבדוק אם זוגי או לא זוגי.
    // אם לא זוגי להוסיף דף
    // לחלק
    // לבדוק אם מתחלק ב4 
    // אם לא להוסיף  עוד 2  דפים
    // להדפיס. 
    private static DividingPages getpagesneeds() {
        return (DividingPages) pageList.getSelectedItem();
    }

    private static ImageType getimgetype() {
        return (ImageType) diagnosisList.getSelectedItem();
    }

    private static int getsplittype() {
        parts p = (parts) splitList.getSelectedItem();

        if (p == parts.four) {
            return 4;
        }
        if (p == parts.three) {
            return 3;
        }
        if (p == parts.two) {
            return 2;
        }
        return -1;
    }

    private String makingPartEnd(String text,int part) 
    {
        part++;
       return text.substring(0,text.indexOf('.'))+" part " +part +text.substring(text.indexOf('.'),text.length());
    }
}
