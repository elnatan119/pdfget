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
    static JCheckBox pages4;
    // static final PDType1Font TIMES_ROMAN = new PDType1Font(Standard14Fonts.getMappedFontName("TIMES_ITALIC"));
    // a default constructor
    private static JCheckBox pages8;
    static JCheckBox shrinkpdfPIC;
    static JCheckBox shrinkpdfnopic;
    private static JComboBox<ImageType> diagnosisList;
    private static JButton saveButton;
    private static JButton openButton;
    private static JButton printButton;
    private static JTextArea myPanel;

    filechooser() {
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // frame to contains GUI elements
        JFrame f = new JFrame("file chooser");

        // set the size of the frame
        f.setSize(400, 400);

        // set the frame's visibility
        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel m = new JPanel();

        pages4 = new JCheckBox("4 pages ");
        pages4.setBounds(100, 100, 50, 50);
        pages8 = new JCheckBox("8 pages");
        pages8.setBounds(100, 100, 50, 50);
        shrinkpdfPIC = new JCheckBox("Reducing the page and changing colors");
        shrinkpdfPIC.setBounds(100, 100, 50, 50);
        shrinkpdfnopic = new JCheckBox(" pdf to A4");
        shrinkpdfnopic.setBounds(100, 100, 50, 50);
        diagnosisList = new JComboBox<>();
        diagnosisList.setModel(new DefaultComboBoxModel<>(ImageType.values()));
        myPanel = new JTextArea("drop in me", 5, 5);
        Font font = new Font("Verdana", Font.BOLD, 50);
        myPanel.setFont(font);
        myPanel.setForeground(Color.BLUE);
        dropMetue();
//         model = new DefaultListModel<>();
//
//        for (ImageType a : ImageType.values())
//            model.addElement(a);
        m.setLayout(new FlowLayout());
        m.add(pages4);
        m.add(shrinkpdfPIC);
        m.add(pages8);
        m.add(shrinkpdfnopic);
        m.add(diagnosisList);

        //    m.setSize(400,400);  
        // m.setVisible(true);
        // button to open save dialog
        saveButton = new JButton("save");

        // button to open open dialog
        openButton = new JButton("open");
        printButton = new JButton("print");
        // make an object of the class filechooser
        filechooser f1 = new filechooser();

        // add action listener to the button to capture user
        // response on buttons
        saveButton.addActionListener(f1);
        openButton.addActionListener(f1);
        printButton.addActionListener(f1);
        makeColoraApper();
        // make a panel to add the buttons and labels
        JPanel p = new JPanel();

        // add buttons to the frame
        p.add(saveButton);
        p.add(openButton);
        p.add(printButton);
        // set the label to its initial value
        l = new JLabel();

        // add panel to the frame
        p.add(l);
        f.getContentPane().add(p, BorderLayout.SOUTH);

        //  f.add(m);
        f.getContentPane().add(m, BorderLayout.CENTER);
        f.getContentPane().add(myPanel, BorderLayout.WEST);

        f.pack();
        f.setVisible(true);
        starter();

    }

    public static void starter() {
        diagnosisList.setVisible(false);
        saveButton.setVisible(false);
        pages8.setSelected(true);
        megsse("no file selected");
    }

    public static void dropMetue() throws HeadlessException {
        myPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        makeAdoc(file, file.getAbsolutePath());
                        showBuotoon(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void makeColoraApper() {
        shrinkpdfPIC.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    diagnosisList.setVisible(true);

                } else {//checkbox has been deselected
                    diagnosisList.setVisible(false);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();

        if (com.equals("save")) {
            savaAfile();
            // eltrntveSaveing();
            l.setText(l.getText() + " saved");
            starter();
            document = null;

        } // if the user presses the open dialog show the open dialog
        else if (com.equals("open")) {
            try {
                openAfile();
            } catch (Exception ex) {
                Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
            }

            showBuotoon(false);

        } else if (com.equals("print")) {
            try {
                document.printfile();

            } catch (PrinterException ex) {
                Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
            }
            showBuotoon(true);
            document = new pdfDoc();
        }

    }

    public static void showBuotoon(boolean rest) {
        openButton.setVisible(rest);
        saveButton.setVisible(!rest);
        myPanel.setVisible(rest);
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
            megsse("making the doc");
            File file = new File(j.getSelectedFile().getAbsolutePath());
            makeAdoc(file, file.getAbsolutePath());

        } // if the user cancelled the operation
        else {
            megsse("the user cancelled the operation");
        }
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

            l.setText(makingPDFend(elegidor.getSelectedFile().getAbsolutePath()));
            try {
                document.save(new File(l.getText()));
                l.setText(l.getText() + " saved");
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

    public static void makeAdoc(File file, String getAbsolutePath) throws Exception {
        // set the label to the path of the selected file

        var doc = new PDDocument();
        var doc2 = new PDDocument();
        megsse("loading PDF.....................");
        if (itispdf(getAbsolutePath)) {
            document = new pdfDoc();
            document.lodepdf( file);
            document.Chackboxtog(pages8, pages4);
        } else {
            document.lodepowerpoint(PDRectangle.A4, ImageType.RGB, getAbsolutePath);
            document.Chackboxtog(pages8, pages4);
// document = powerpointtopdf(PDRectangle.A4, ImageType.RGB, j.getSelectedFile().getAbsolutePath());
        }

        if (shrinkpdfPIC.isSelected()) {
            megsse("Reducing pdf ");
            document.ShrinkPDF(PDRectangle.A4, (ImageType) (diagnosisList.getSelectedItem()));
        }
        megsse("adding page numbers..............");
        document.addPageNumbers(" {0}", 60, 18, 20, 1, true);
        System.out.println("document =" + document);
        if (pages4.isSelected() || pages8.isSelected()) {

            document.bookops(doc2,l);
        } else {
            document.regluerOpsinOFmulti(doc2, doc);
        }

        megsse(
                "Finish Please save the document");

    }

    public static void megsse(String g) {

        l.setText(g);
        l.paintImmediately(l.getVisibleRect());
    }

    // בהתחלה לבדוק אם זוגי או לא זוגי.
    // אם לא זוגי להוסיף דף
    // לחלק
    // לבדוק אם מתחלק ב4 
    // אם לא להוסיף  עוד 2  דפים
    // להדפיס. 
    private String makingPDFend(String absolutePath) {
        if (!itispdf(absolutePath)) {
            return absolutePath + ".pdf";
        }
        return absolutePath;
    }

    public static boolean itispdf(String absolutePath) {
        return absolutePath.substring(absolutePath.length() - 5).contains(".pdf");
    }

}
