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
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.commons.compress.utils.Lists;
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
    static ArrayList<pdfDoc> documentS;
    static ArrayList<File> FilesS= new ArrayList<File>();
    // static JCheckBox pages4;
    // static final PDType1Font TIMES_ROMAN = new PDType1Font(Standard14Fonts.getMappedFontName("TIMES_ITALIC"));
    // a default constructor
    private static JCheckBox pages;
    static JCheckBox shrinkpdfPIC;
    static JCheckBox shrinkpdfnopic;
    private static JComboBox<ImageType> diagnosisList;
    private static JButton saveButton;
    private static JCheckBox partCachck;
    private static JComboBox<Parts> partsList;
    private static JComboBox<DividingPages> pageList;
    private static JButton openButton;
    private static JButton printButton;
    private static JButton WorkButton;
    private static JTextArea TextPanel;
    private static JFrame f;
    private static JPanel shrinkpanel;
    private static JPanel spltpanel;

    private static JPanel pagesPanel;
    private static JPanel chackBokloly;
    private static JPanel ButtonsPanel;
     private static String  maxSparte="----------------------";
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
        f.getContentPane().add(TextPanel);

        f.pack();
        f.setVisible(true);
    }

    public static void formOfButoonsIns() {
        saveButton = new JButton("save");
        openButton = new JButton("open");
        printButton = new JButton("print");
        WorkButton = new JButton("strat Work");
        // make an object of the class filechooser
        filechooser f1 = new filechooser();
        // add action listener to the button to capture user
        // response on buttons
        saveButton.addActionListener(f1);
        openButton.addActionListener(f1);
        printButton.addActionListener(f1);
        WorkButton.addActionListener(f1);
        // make a panel to add the buttons and labels
        ButtonsPanel = new JPanel();

        // add buttons to the frame
        ButtonsPanel.add(saveButton);
        ButtonsPanel.add(openButton);
        ButtonsPanel.add(printButton);
        ButtonsPanel.add(WorkButton);

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
        partCachck = new JCheckBox();
        partsList = new JComboBox<>();
        spltpanel = panelofcomoandcheckbox(partCachck, partsList, "split docmnet to parts", Parts.values());
        pages = new JCheckBox();
        pageList = new JComboBox<>();
        pagesPanel = panelofcomoandcheckbox(pages, pageList, "how much pages you want?", DividingPages.values());
        TextPanel = new JTextArea("drop in me", 5, 5);
        
        Font font = new Font("C:/windows/fonts/david.ttf", Font.PLAIN, 20);
        TextPanel.setFont(font);
        TextPanel.setForeground(Color.BLUE);
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
        TextPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                 
                   ArrayList<File> droppedFilesS = new ArrayList<>(droppedFiles); 
 
                   
                        PutTheNameAndInThelist(droppedFilesS);
                        
                        showTheButoonOfDoWork();
                        

                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        
        });
        TextPanel.setEditable(false);
    }
        private  static  void PutTheNameAndInThelist(ArrayList<File> droppedFiles) 
            {
                 for (File file : droppedFiles) 
                 {
                    String closer="|";
                String sprate ="\n" + MakSparte(closer+file.getAbsolutePath()+closer)+"\n";
               
               FilesS.add(file);
               TextPanel.setText(TextPanel.getText()+sprate+closer+file.getAbsolutePath()+closer+sprate);
                }
                 if(FilesS.size()>1)
                 {
                      partCachck.setSelected(true);
                     partCachck.setText("how much pdf? you want in the output ");
                     partCachck.setEnabled(false);
                 }
                //ArrayList<String>
                 
            }

            private static  void showTheButoonOfDoWork() 
            {
               WorkButton.setVisible(true);
               f.pack();
            }

            private static String MakSparte(String absolutePath) 
            {
                if(maxSparte.length()<absolutePath.length())
                {
                   
                    
                    String k ="";
                    maxSparte="";
                    for (int i = 0; i <absolutePath.length()+10 ; i++) 
                    {
                    k+="-";    
                    }
                  
                 
                  return k;
                }
                return absolutePath;
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
            case "strat Work" -> {
            try {
               doTheWork();
            } catch (Exception ex) {
                Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        TextPanel.setVisible(rest);
        shrinkpdfPIC.setVisible(rest);
        shrinkpdfnopic.setVisible(rest);
        // diagnosisList.setVisible(rest);
        shrinkpanel.setVisible(rest);
        spltpanel.setVisible(rest);

        pagesPanel.setVisible(rest);
        WorkButton.setVisible(false);
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
            File[] k =j.getSelectedFiles();
            ArrayList<File> mkm=  new ArrayList<>(Arrays.asList(k));     
            PutTheNameAndInThelist(mkm);
                        showTheButoonOfDoWork();

        } // if the user cancelled the operation
        else {
            megsse("the user cancelled the operation");
        }
    }

    public static void doTheWork() throws Exception {
        document = new pdfDoc();
        if (FilesS.size()>1) {
            switch (getsplittype()) 
           {
                case one:
                    ArrayOfPdfDocs.lodeFilesindoc(FilesS,document, getpagesneeds(), l, shrinkpdfPIC.isSelected(), getimgetype());
                    document.MakeADoc(shrinkpdfPIC.isSelected(), l, getimgetype(), getpagesneeds());
                    break;
                default:
            
            documentS = LodeAndMakeADocS(FilesS, getpagesneeds(), l, true, getimgetype(),getsplittype() );
            }
        } else {
            document.loudAndMakeDoc(FilesS.get(0), FilesS.get(0).getAbsolutePath(), getpagesneeds(), l, shrinkpdfnopic.isSelected(), getimgetype());
        }
        showBuotoon(false);
    }
     static ArrayList<pdfDoc> LodeAndMakeADocS(ArrayList<File> FilesS, DividingPages pagesneeds, JLabel l, boolean selected, ImageType imgetype, Parts part) throws Exception {
        ArrayList<pdfDoc> doc = new ArrayList<pdfDoc>();
        
            
            
          
           switch (part) 
           {
                case orginal:
                    for (var file : FilesS) 
                    {
                          pdfDoc a = new pdfDoc();
               a.loudAndMakeDoc(file,file.getAbsolutePath(), pagesneeds, l, selected, imgetype);
                    doc.add(a);
                    }
                    break;
                case one:
                    ArrayOfPdfDocs.lodeFilesindoc(FilesS,document, pagesneeds, l, selected, imgetype);
                    document.MakeADoc(shrinkpdfPIC.isSelected(), l, imgetype, pagesneeds);
                    break;
                default:
                   return lodeFilesAndSplitThem(FilesS, Parts.getsplittype(part), selected, l, imgetype, pagesneeds, doc);
                  
                   
            
           
           
          
        }
        return doc;
    }
      static ArrayList<pdfDoc> lodeFilesAndSplitThem(ArrayList<File> FilesS, int splittype, boolean selected, JLabel l, ImageType imgetype, DividingPages pagesneeds, ArrayList<pdfDoc> doc) throws Exception 
    {
        
      ArrayOfPdfDocs.lodeFilesindoc(FilesS,document, pagesneeds, l, selected, imgetype);
       documentS= ArrayOfPdfDocs.splitAndMakeDOC(document, splittype, selected, l, imgetype, pagesneeds, doc);
        return  doc;
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
                if (documentS != null) {
                    for (int i = 0; i < documentS.size(); i++) {
                        String name = makingPartEnd(l.getText(), i);
                        pdfDoc.StraterPage(name, documentS.get(i), i);
                        documentS.get(i).save(new File(name));
                    }
                } else {
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

   

    private String makingPartEnd(String text, int part) {
        part++;
        return text.substring(0, text.indexOf('.')) + " part " + part + text.substring(text.indexOf('.'), text.length());
    }

    private File getFormTextArea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private static Parts getsplittype() 
    {
        return (Parts) partsList.getSelectedItem();
    }
    
}
