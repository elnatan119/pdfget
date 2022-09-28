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
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.TreeMap;
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
    static PDDocument document;
    static JCheckBox pages4;
    // static final PDType1Font TIMES_ROMAN = new PDType1Font(Standard14Fonts.getMappedFontName("TIMES_ITALIC"));
    // a default constructor
    private static JCheckBox pages8;
    static JCheckBox shrinkpdfPIC;
    static JCheckBox shrinkpdfnopic;
    private static JComboBox<ImageType> diagnosisList;
    private static DefaultListModel<ImageType> model;
    private static JButton saveButton;
    private static JButton openButton;
    private static JButton printButton;
    private static JTextArea myPanel;

    filechooser() {
    }

    /**
     * @param args the command line arguments
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
        l = new JLabel("no file selected");

        // add panel to the frame
        p.add(l);
        f.getContentPane().add(p, BorderLayout.SOUTH);

        //  f.add(m);
        f.getContentPane().add(m, BorderLayout.CENTER);
        f.getContentPane().add(myPanel, BorderLayout.WEST);

        f.pack();
        f.setVisible(true);
        diagnosisList.setVisible(false);
        saveButton.setVisible(false);
        pages8.setSelected(true);

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

    public static PDDocument addPageNumbers(PDDocument document, String numberingFormat, int offset_X, int offset_Y, int size, int page_counter, boolean makeZEFR) throws IOException {

        PDFont font = new PDType1Font(Standard14Fonts.FontName.COURIER);

        for (PDPage page : document.getPages()) {
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            putNUMBER(contentStream, font, size, page, offset_Y, numberingFormat, page_counter);
            ++page_counter;
        }
//        if (checkBox1.isSelected() && makeZEFR) {
//            return bookAopsin();
//
//        }
//        if (pages8.isSelected() && makeZEFR) {
//            return book8pages();
//
//        }
        return filechooser.document;

    }

    private static PDDocument book8pages() {

        int start = 0;
        int replece = 0;
        int key = 0;
        int sharit = 8 - (document.getNumberOfPages() % 8);
        int end = document.getNumberOfPages() - 1 + sharit;
        int a[] = new int[end + 1];
        PDDocument AD = new PDDocument();
        PDPage p = new PDPage();
        System.out.println("start=" + start + "end= " + end);
        while (start <= end) {
            //System.out.println("start=" + start + "end= " + end);
            if (a[start] == 0) {
                AD.addPage(document.getPage(start));
                a[start] = 1;

            } else {
                key = 1;
            }
            if (end > document.getNumberOfPages() - 1 && a[end] == 0) {
                AD.addPage(p);
                a[end] = 1;
            } else if (a[end] == 0) {
                AD.addPage(document.getPage(end));
                a[end] = 1;
            } else {
                key = 1;
            }

            if (replece % 2 == 0) {
                start = start + 2;
                end = end - 2;

            } else {
                start = start - 1;
                end = end + 1;

            }
            if (key != 1) {
                replece++;
            } else {
                key = 0;
            }

            System.out.println("start=" + start + "end= " + end);

        }
//        AD.addPage(document.getPage(document.getNumberOfPages() / 2 - 1));
//        AD.addPage(document.getPage(document.getNumberOfPages() / 2));
        return AD;
    }

    private static void putNUMBER(PDPageContentStream contentStream, PDFont font, int size, PDPage page, int offset_Y, String numberingFormat, int page_counter) throws IOException {
        contentStream.setFont(font, size);

        contentStream.beginText();

        PDRectangle pageSize = page.getMediaBox();
        float x = pageSize.getLowerLeftX();
        float y = pageSize.getLowerLeftY();
        contentStream.newLineAtOffset(x + (pageSize.getWidth() / 2) /*- offset_X*/, y + offset_Y);
        String text = MessageFormat.format(numberingFormat, page_counter);

        contentStream.showText(text);
        contentStream.endText();
        contentStream.close();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();

        if (com.equals("save")) {
            savaAfile();
            // eltrntveSaveing();
            l.setText(l.getText() + " saved");
            showBuotoon(true);
            document = new PDDocument();

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
                printfile();
              
            } catch (PrinterException ex) {
                Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
            }
            showBuotoon(true);
            document = new PDDocument();
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
        String g = new String();
        megsse("loading PDF.....................");
        if (itispdf(getAbsolutePath)) {
            document = Loader.loadPDF(file);
        } else {
            document = powerpointtopdf(PDRectangle.A4, ImageType.RGB, getAbsolutePath);
// document = powerpointtopdf(PDRectangle.A4, ImageType.RGB, j.getSelectedFile().getAbsolutePath());
        }

        if (shrinkpdfPIC.isSelected()) {
            megsse("Reducing pdf ");
            document = ShrinkPDF(document, PDRectangle.A4, (ImageType) (diagnosisList.getSelectedItem()));
        }
        megsse("adding page numbers..............");
        document = addPageNumbers(document, " {0}", 60, 18, 20, 1, true);
        System.out.println("document =" + document);
        if (pages4.isSelected() || pages8.isSelected()) {

            bookops(doc2);
        } else {
            regluerOpsinOFmulti(doc2, doc);
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
    public static void bookops(PDDocument doc2) throws IOException {
        PDPage p = new PDPage();
        System.out.println("document=" + document.getNumberOfPages());

        // function reArange()
        // function add pages if needed 
        // function devideTo4()
//        for (int i = 0; i < (document.getNumberOfPages() % 2); i++) {
//
//            document.addPage(p);
//        }
        System.out.println("document  adding  A=" + document.getNumberOfPages());
        if (pages8.isSelected()) {
            megsse("Prepares  eight pages ");
            document = book8pages();

        }
        if (pages4.isSelected()) {
            document = book4pages();

            megsse("Prepares four pages");
        }
        p = new PDPage();
//          for (int i = 0; i < (document.getNumberOfPages() % 2); i++) 
//        {
//
//            document.addPage(p);
//        }
        System.out.println("document after adding  A=" + document.getNumberOfPages());
        document = generateSideBySidePDF(document, PDRectangle.A4);
        System.out.println("document before adding b=" + document.getNumberOfPages());

        System.out.println("document after adding b=" + document.getNumberOfPages());
        if (pages8.isSelected()) {
            document = generate4BySidePDF(document, PDRectangle.A4);
        }
        System.out.println("document end =" + document.getNumberOfPages());
        addPageNumbers(document, " {0}", 60, 18, 40, 1, false);

    }

    public static void regluerOpsinOFmulti(PDDocument doc2, PDDocument doc) throws IOException {
        for (int i = 0; i < document.getNumberOfPages() % 2; i++) {
            doc2.addPage(document.getPage(((document.getNumberOfPages() / 2) * 2) + i));

        }
        System.out.println("doc2=" + doc2.getNumberOfPages());
        document = generateSideBySidePDF(document, PDRectangle.A4);
        for (int i = 0; i < document.getNumberOfPages() % 2; i++) {
            doc.addPage(document.getPage(((document.getNumberOfPages() / 2) * 2) + i));

        }
        System.out.println("doc1=" + doc2.getNumberOfPages());
        document = generate4BySidePDF(document, PDRectangle.A4);
        for (int i = 0; i < doc.getNumberOfPages(); i++) {
            document.addPage(doc.getPage(i));

        }
        for (int i = 0; i < doc2.getNumberOfPages(); i++) {

            document.addPage(doc2.getPage(i));
        }
        addPageNumbers(document, " {0}", 60, 18, 40, 1, false);
    }

    public static PDDocument generateSideBySidePDF(PDDocument pdf1, PDRectangle PDRhrinkpdfnopic) {
        var outPdf2 = new PDDocument();
        PDDocument outPdf;
        try {

            int pdfconter = 0;
            int stoper = 0;
            int key = 1;
            int shy = 1;
            int number = 0;
            // Create output PDF frame
            while (pdfconter + 1 < pdf1.getPages().getCount()) {
                outPdf = new PDDocument();

                PDRectangle pdf1Frame = pdf1.getPage(pdfconter).getCropBox();
                PDRectangle pdf2Frame = pdf1.getPage(pdfconter + 1).getCropBox();

                PDRectangle outPdfFrame = new PDRectangle(pdf1Frame.getWidth() + pdf2Frame.getWidth(), Math.max(pdf1Frame.getHeight(), pdf2Frame.getHeight()));
                if (shrinkpdfnopic.isSelected()) {
                    outPdfFrame = new PDRectangle(PDRhrinkpdfnopic.getWidth() * 2, PDRhrinkpdfnopic.getHeight());
                }
                // Create output page with calculated frame and add it to the document
                COSDictionary dict = new COSDictionary();
                dict.setItem(COSName.TYPE, COSName.PAGE);
                dict.setItem(COSName.MEDIA_BOX, outPdfFrame);
                dict.setItem(COSName.CROP_BOX, outPdfFrame);
                dict.setItem(COSName.ART_BOX, outPdfFrame);
                PDPage outPdfPage = new PDPage(dict);

                outPdf.addPage(outPdfPage);

                // Source PDF pages has to be imported as form XObjects to be able to insert them at a specific point in the output page
                LayerUtility layerUtility = new LayerUtility(outPdf);
                PDFormXObject formPdf1 = layerUtility.importPageAsForm(pdf1, pdfconter + key);
                PDFormXObject formPdf2 = layerUtility.importPageAsForm(pdf1, pdfconter + number);

                // Add form objects to output page
                AffineTransform afLeft = AffineTransform.getTranslateInstance(pdf1Frame.getWidth(), 0.0);
                layerUtility.appendFormAsLayer(outPdfPage, formPdf1, afLeft, "left");
                AffineTransform afRight = new AffineTransform();
                layerUtility.appendFormAsLayer(outPdfPage, formPdf2, afRight, "right");

                pdfconter += 2;
                outPdf2.addPage(outPdf.getPage(0));

                if ((pages4.isSelected())) {
                    if (key == 1) {
                        key = 0;
                        number = 1;
                    } else {
                        key = 1;
                        number = 0;
                    }
                } else if (shy % 2 == 0 && pages8.isSelected()) {
                    if (key == 1) {
                        key = 0;
                        number = 1;
                    } else {
                        key = 1;
                        number = 0;
                    }
                }
                shy++;
            }

            //  outPdf.save(outPdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pdf1 != null) {
                    pdf1.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outPdf2;
    }

    public static PDDocument generate4BySidePDF(PDDocument pdf1, PDRectangle A4) {
        var outPdf2 = new PDDocument();
        PDDocument outPdf;
        try {

            int pdfconter = 0;
            // Create output PDF frame
            while (pdfconter + 1 < pdf1.getPages().getCount()) {
                outPdf = new PDDocument();
                PDRectangle pdf1Frame = pdf1.getPage(pdfconter).getCropBox();
                PDRectangle pdf2Frame = pdf1.getPage(pdfconter + 1).getCropBox();

                PDRectangle outPdfFrame = new PDRectangle(Math.max(pdf1Frame.getWidth(), pdf2Frame.getWidth()), pdf1Frame.getHeight() + pdf2Frame.getHeight());
                if (shrinkpdfnopic.isSelected()) {
                    outPdfFrame = new PDRectangle(A4.getWidth() * 2, A4.getHeight() * 2);
                }

                COSDictionary dict = new COSDictionary();
                dict.setItem(COSName.TYPE, COSName.PAGE);
                dict.setItem(COSName.MEDIA_BOX, outPdfFrame);
                dict.setItem(COSName.CROP_BOX, outPdfFrame);
                dict.setItem(COSName.ART_BOX, outPdfFrame);
                PDPage outPdfPage = new PDPage(dict);
                outPdf.addPage(outPdfPage);
                // Source PDF pages has to be imported as form XObjects to be able to insert them at a specific point in the output page
                LayerUtility layerUtility = new LayerUtility(outPdf);
                PDFormXObject formPdf1 = layerUtility.importPageAsForm(pdf1, pdfconter);
                PDFormXObject formPdf2 = layerUtility.importPageAsForm(pdf1, pdfconter + 1);

                // Add form objects to output page
                // 0.0, pdf1Frame.getHeight()
                AffineTransform afup = AffineTransform.getTranslateInstance(0.0, pdf1Frame.getHeight());;
                layerUtility.appendFormAsLayer(outPdfPage, formPdf1, afup, "up");
                AffineTransform afdown = new AffineTransform();
                layerUtility.appendFormAsLayer(outPdfPage, formPdf2, afdown, "down");

                pdfconter += 2;
                outPdf2.addPage(outPdf.getPage(0));
            }

            //  outPdf.save(outPdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pdf1 != null) {
                    pdf1.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outPdf2;
    }

    private PDDocument peges2(PDDocument doc) {
        PDDocument d = new PDDocument();
        d.addPage(doc.getPage(0));
        d.addPage(doc.getPage(1));
        return d;

    }

    private static PDDocument book4pages() {
        int start = 0;
        int replece = 0;
        int key = 0;
        PDPage p = new PDPage();
        int sharit = 4 - (document.getNumberOfPages() % 4);
        if (sharit == 4) {
            sharit = 0;
        }
        int end = document.getNumberOfPages() - 1 + sharit;

        int a[] = new int[end + 1];
        PDDocument AD = new PDDocument();

        while (start <= end) {
            System.out.println("start=" + start + "end= " + end);
            if (a[start] == 0) {
                AD.addPage(document.getPage(start));
                a[start] = 1;

            }
            if (end > document.getNumberOfPages() - 1 && a[end] == 0) {

                AD.addPage(p);
                a[end] = 1;
            } else if (a[end] == 0) {
                AD.addPage(document.getPage(end));
                a[end] = 1;
            }

            start = start + 1;
            end = end - 1;

        }
//        AD.addPage(document.getPage(document.getNumberOfPages() / 2 - 1));
//        AD.addPage(document.getPage(document.getNumberOfPages() / 2));
        return AD;

    }

    public static PDDocument ShrinkPDF(PDDocument oDocument, PDRectangle K, ImageType m) {
        PDDocument pdDocument = new PDDocument();
        try {

            PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
            int numberOfPages = oDocument.getNumberOfPages();
            PDPage page = null;

            for (int i = 0; i < numberOfPages; i++) {
                page = new PDPage(K);
                BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300, m);
                PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
                PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
                float newHeight = K.getHeight();
                float newWidth = K.getWidth();
                contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
                contentStream.close();

                pdDocument.addPage(page);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdDocument;

    }

    public static PDDocument powerpointtopdf(PDRectangle K, ImageType m, String isk) throws Exception {
        FileInputStream is = new FileInputStream(isk);
        PDDocument pdDocument = new PDDocument();
        XMLSlideShow ppt = new XMLSlideShow(is);
        //is.close();

        Dimension pgsize = ppt.getPageSize();

        List<XSLFSlide> slide = ppt.getSlides();

        PDPage page = null;
        for (int i = 0; i < slide.size(); i++) {
            page = new PDPage(K);
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
            Graphics2D graphics = img.createGraphics();
            //clear the drawing area
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            //render
            slide.get(i).draw(graphics);
            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
            PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, img);
            float newHeight = K.getHeight();
            float newWidth = K.getWidth();
            contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
            contentStream.close();

            pdDocument.addPage(page);

        }
        return pdDocument;
    }

    private String makingPDFend(String absolutePath) {
        if (!itispdf(absolutePath)) {
            return absolutePath + ".pdf";
        }
        return absolutePath;
    }

    public static boolean itispdf(String absolutePath) {
        return absolutePath.substring(absolutePath.length() - 5).contains(".pdf");
    }
    public static PrintService choosePrinter() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
                        hagdrodHadpsa(attributes);

        if (printJob.printDialog(attributes)) {
            return printJob.getPrintService();
        } else {
            return null;
        }
    }

    public static void hagdrodHadpsa(PrintRequestAttributeSet attributes) {
      //  attributes.add(MediaSizeName.);
        if (pages4.isSelected()) 
        {
            attributes.add(Sides.TWO_SIDED_SHORT_EDGE);
        }
         else
        {
            attributes.add(Sides.TWO_SIDED_LONG_EDGE);
        }
        
        attributes.add(PrintQuality.DRAFT);
                attributes.add(Chromaticity.MONOCHROME);
               // attributes.add( javax.print.attribute.standard.)
        
//         attributes.add((Attribute) saveButton);
    }
    private void printfile() throws PrinterException 
    {
       
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintService(choosePrinter() );
    
    job.setPageable(new PDFPageable(document));
   job.print();
    }
     private  static  void eltrntveSaveing() throws PrinterException 
    {
      
            String printerNameDesired = "Microsoft Print to PDF";

javax.print.PrintService[] service = PrinterJob.lookupPrintServices();

DocPrintJob docPrintJob = null;


int count = service.length;
for (int i = 0; i < count; i++) {
    if (service[i].getName().contains(printerNameDesired)) {
        docPrintJob = service[i].createPrintJob();
        i = count;
    }
}
 PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
  attributes.add(MediaSizeName.ISO_A4);
     //    attributes.add(Chromaticity.MONOCHROME);

PrinterJob pjob = PrinterJob.getPrinterJob();
pjob.setPrintService(docPrintJob.getPrintService());
pjob.setJobName("job");
PDFPrintable printable = new PDFPrintable(document, Scaling.SCALE_TO_FIT);
pjob.setPrintable(printable);
   // PageFormat pageFormat = job.getPageFormat(null);
pjob.print(attributes);

    }
     
    
   
    

}

