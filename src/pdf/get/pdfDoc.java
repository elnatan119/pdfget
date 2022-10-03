/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdf.get;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.Sides;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.multipdf.LayerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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

/**
 *
 * @author User
 */
enum DividingPages {
    Eight,
    Four,
    Oldway
}

enum parts {
    four,
    three,
    two
}

public class pdfDoc extends PDDocument {

//    private static String hebREP(String subSequence) 
//    {
//      if(isitHeb(subSequence))
//      {
//          String replce="";
//          for (int i = subSequence.length(); i >=0 ; i--) 
//          {
//              replce= replce +subSequence.charAt(i);
//             
//          }
//          return replce;
//      }
//      return subSequence;
//    }
//
//    private static boolean isitHeb(String subSequence) 
//    {
//        for (int i = 0; i <subSequence.length() ; i++) 
//        {
//          char a =  subSequence.charAt(i);
//           if('א'<= a||a>='ת')
//           {
//               return  true;
//           }
//        }
//        return false;
//    }

    boolean srinknopic = false;
    PDDocument document = this;

    public boolean isSrinknopic() {
        return srinknopic;
    }

    public void setSrinknopic(boolean srinknopic) {
        this.srinknopic = srinknopic;
    }

    /**
     *
     * // * @param g-איזה סוג דף זה
     */
    

    public void lodepdf(File file) throws IOException {
        document = Loader.loadPDF(file);

    }

    public void addPageNumbers(String numberingFormat, int offset_X, int offset_Y, int size, int page_counter, boolean makeZEFR) throws IOException {

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

    }

    private void putNUMBER(PDPageContentStream contentStream, PDFont font, int size, PDPage page, int offset_Y, String numberingFormat, int page_counter) throws IOException {
        try (contentStream) {
            contentStream.setFont(font, size);

            contentStream.beginText();

            PDRectangle pageSize = page.getMediaBox();
            float x = pageSize.getLowerLeftX();
            float y = pageSize.getLowerLeftY();
            contentStream.newLineAtOffset(x + (pageSize.getWidth() / 2) /*- offset_X*/, y + offset_Y);
            String text = MessageFormat.format(numberingFormat, page_counter);

            contentStream.showText(text);
            contentStream.endText();
        }
    }

    public void generateSideBySidePDF(PDRectangle PDRhrinkpdfnopic, DividingPages g) {
        PDDocument pdf1 = document;
        var outPdf2 = new PDDocument();
        PDDocument outPdf;
        megsse(pdf1.getNumberOfPages() + "", filechooser.l);
        try {

            int pdfconter = 0;
            int key = 1;
            int shy = 1;
            int number = 0;

            // Create output PDF frame
            while (pdfconter + 1 < pdf1.getNumberOfPages()) {
                outPdf = new PDDocument();

                PDRectangle pdf1Frame = pdf1.getPage(pdfconter).getCropBox();
                PDRectangle pdf2Frame = pdf1.getPage(pdfconter + 1).getCropBox();

                PDRectangle outPdfFrame = new PDRectangle(pdf1Frame.getWidth() + pdf2Frame.getWidth(), Math.max(pdf1Frame.getHeight(), pdf2Frame.getHeight()));
                if (srinknopic) {
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

                // Source PDF DividingPages has to be imported as form XObjects to be able to insert them at a specific point in the output page
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

                if ((g == DividingPages.Four)) {
                    if (key == 1) {
                        key = 0;
                        number = 1;
                    } else {
                        key = 1;
                        number = 0;
                    }
                } else if (shy % 2 == 0 && g == DividingPages.Eight) {
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
        document = outPdf2;
    }

    public void generate4BySidePDF(PDRectangle A4) {
        PDDocument pdf1 = document;
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
                if (srinknopic) {
                    outPdfFrame = new PDRectangle(A4.getWidth() * 2, A4.getHeight() * 2);
                }

                COSDictionary dict = new COSDictionary();
                dict.setItem(COSName.TYPE, COSName.PAGE);
                dict.setItem(COSName.MEDIA_BOX, outPdfFrame);
                dict.setItem(COSName.CROP_BOX, outPdfFrame);
                dict.setItem(COSName.ART_BOX, outPdfFrame);
                PDPage outPdfPage = new PDPage(dict);
                outPdf.addPage(outPdfPage);
                // Source PDF DividingPages has to be imported as form XObjects to be able to insert them at a specific point in the output page
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
        document = outPdf2;
    }

    private void book8pages() {

        int start = 0;
        int replece = 0;
        int key = 0;
        int sharit = 8 - (document.getNumberOfPages() % 8);
        if (sharit == 8) {
            sharit = 0;
        }
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
        document = AD;
    }

    private PDDocument peges2(PDDocument doc) {
        PDDocument d = new PDDocument();
        d.addPage(doc.getPage(0));
        d.addPage(doc.getPage(1));
        return d;

    }

    private void book4pages() {
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
        document = AD;

    }

    public void ShrinkPDF(PDRectangle K, ImageType m) {
        PDDocument oDocument = document;
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
        document = pdDocument;

    }

    public void lodepowerpoint(PDRectangle K, ImageType m, String isk) throws Exception {
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
        document = pdDocument;
    }

    public void printfile(DividingPages g) throws PrinterException {

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintService(choosePrinter(g));

        job.setPageable(new PDFPageable(document));
        job.print();
    }

    public PrintService choosePrinter(DividingPages g) {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        hagdrodHadpsa(attributes, g);

        if (printJob.printDialog(attributes)) {
            return printJob.getPrintService();
        } else {
            return null;
        }
    }

    public void hagdrodHadpsa(PrintRequestAttributeSet attributes, DividingPages g) {
        //  attributes.add(MediaSizeName.);
        if (g == DividingPages.Four) {
            attributes.add(Sides.TWO_SIDED_SHORT_EDGE);
        } else {
            attributes.add(Sides.TWO_SIDED_LONG_EDGE);
        }

        attributes.add(PrintQuality.DRAFT);
        attributes.add(Chromaticity.MONOCHROME);
        // attributes.add( javax.print.attribute.standard.)

//         attributes.add((Attribute) saveButton);
    }

    private void eltrntveSaveing() throws PrinterException {

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

    public void bookops(PDDocument doc2, JLabel l, DividingPages g) throws IOException {
        PDPage p = new PDPage();
        System.out.println("document=" + document.getNumberOfPages());

        // function reArange()
        // function add DividingPages if needed 
        // function devideTo4()
//        for (int i = 0; i < (document.getNumberOfPages() % 2); i++) {
//
//            document.addPage(p);
//        }
        System.out.println("document  adding  A=" + document.getNumberOfPages());
        if (g == DividingPages.Eight) {
            megsse("Prepares  eight pages ", l);
            book8pages();

        }
        if (g == DividingPages.Four) {
            book4pages();

            megsse("Prepares four pages", l);
        }
        p = new PDPage();
//          for (int i = 0; i < (document.getNumberOfPages() % 2); i++) 
//        {
//
//            document.addPage(p);
//        }
        System.out.println("document after adding  A=" + document.getNumberOfPages());
        generateSideBySidePDF(PDRectangle.A4, g);
        System.out.println("document before adding b=" + document.getNumberOfPages());

        System.out.println("document after adding b=" + document.getNumberOfPages());
        if (g == DividingPages.Eight) {
            generate4BySidePDF(PDRectangle.A4);
        }
        System.out.println("document end =" + document.getNumberOfPages());
        addPageNumbers(" {0}", 60, 18, 40, 1, false);

    }

    public void regluerOpsinOFmulti(PDDocument doc2, PDDocument doc, DividingPages g) throws IOException {
        for (int i = 0; i < document.getNumberOfPages() % 2; i++) {
            doc2.addPage(document.getPage(((document.getNumberOfPages() / 2) * 2) + i));

        }
        System.out.println("doc2=" + doc2.getNumberOfPages());
        generateSideBySidePDF(PDRectangle.A4, g);
        for (int i = 0; i < document.getNumberOfPages() % 2; i++) {
            doc.addPage(document.getPage(((document.getNumberOfPages() / 2) * 2) + i));

        }
        System.out.println("doc1=" + doc2.getNumberOfPages());
        generate4BySidePDF(PDRectangle.A4);
        for (int i = 0; i < doc.getNumberOfPages(); i++) {
            document.addPage(doc.getPage(i));

        }
        for (int i = 0; i < doc2.getNumberOfPages(); i++) {

            document.addPage(doc2.getPage(i));
        }
        addPageNumbers(" {0}", 60, 18, 40, 1, false);
    }

    public void megsse(String g, JLabel l) {

        l.setText(g);
        l.paintImmediately(l.getVisibleRect());
    }

    @Override
    public void save(File file) throws IOException {
        document.save(file);
    }

    public void loudAndMakeDoc(File file, String getAbsolutePath, DividingPages g, JLabel l, boolean shrinkpdfPIC, ImageType diagnosisListgetSelectedItem) throws Exception {
        // set the label to the path of the selected file

        megsse("loading PDF.....................", l);
        if (itispdf(getAbsolutePath)) {

            lodepdf(file);
            //  document.Chackboxtog(pages8, pages4);
        } else {
            lodepowerpoint(PDRectangle.A4, ImageType.RGB, getAbsolutePath);
            // document.Chackboxtog(pages8, pages4);
// document = powerpointtopdf(PDRectangle.A4, ImageType.RGB, j.getSelectedFile().getAbsolutePath());
        }

        MakeADoc(shrinkpdfPIC, l, diagnosisListgetSelectedItem, g);

    }

    public void MakeADoc(boolean shrinkpdfPIC, JLabel l, ImageType diagnosisListgetSelectedItem, DividingPages g) throws IOException {
        var doc = new PDDocument();
        var doc2 = new PDDocument();
        if (shrinkpdfPIC) {
            megsse("Reducing pdf ", l);
            ShrinkPDF(PDRectangle.A4, (diagnosisListgetSelectedItem));
        }
        megsse("adding page numbers..............", l);
        addPageNumbers(" {0}", 60, 18, 20, 1, true);
        System.out.println("document =" + document);
        if (g == DividingPages.Eight || g == DividingPages.Four) {

            bookops(doc2, l, g);
        } else if (g == DividingPages.Oldway) {
            regluerOpsinOFmulti(doc2, doc, g);
        }

        megsse(
                "Finish Please save or print  the document", l);
    }

    public static boolean itispdf(String absolutePath) {
        return absolutePath.substring(absolutePath.length() - 5).contains(".pdf");
    }

    public static String makingPDFend(String absolutePath) {
        if (!itispdf(absolutePath)) {
            return absolutePath + ".pdf";
        }
        return absolutePath;
    }

        public static   pdfDoc[] LodeAndMakeADocS(  File file, String absolutePath, DividingPages pagesneeds, JLabel l, boolean shrinkpdfnopicBool, ImageType imgetype, int splittype) throws IOException, Exception 
        {
        pdfDoc[] doc = new pdfDoc[splittype];
        PDDocument document = Loader.loadPDF(file);
        int howMuchleft = document.getNumberOfPages();
        int TOgo = document.getNumberOfPages() / splittype;
        int part = document.getNumberOfPages() / splittype;
        int hdkdmot = 0;
        for (int i = 0; i < splittype; i++) {
            pdfDoc pdffoc = new pdfDoc();
            pdffoc.addPage(new PDPage());
         //   addingStraterPage(file.getName(), pdffoc, i);
          pdfDoc.addPages(hdkdmot, TOgo, document, pdffoc);
            hdkdmot = TOgo;
            if (i+2==splittype) 
            {
                TOgo = document.getNumberOfPages();

            } else {
                TOgo = TOgo + part;
            }
            pdffoc.MakeADoc(shrinkpdfnopicBool, l, imgetype, pagesneeds);
            doc[i] = pdffoc;

        }
        return doc;
    }

    public static void StraterPage( String name, pdfDoc pdffoc, int i) throws IOException {
        PDFont font = PDType0Font.load(pdffoc, new File("C:/windows/fonts/david.ttf"));
        try (PDPageContentStream contentStream = new PDPageContentStream(pdffoc, pdffoc.getPage(0), PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.setFont(font, 50);
            contentStream.beginText();
            
            PDRectangle pageSize = pdffoc.getPage(0).getMediaBox();
            float x = pageSize.getLowerLeftX();
            float y = pageSize.getLowerLeftY();
           // contentStream.newLineAtOffset(x + (pageSize.getWidth() / 2), y + (pageSize.getHeight() / 2));
               contentStream.newLineAtOffset(0,400);
               i++;
            String text =  ((String) name.subSequence(0, name.indexOf("."))) + "  part  " + i;
            
            contentStream.showText(text);
            contentStream.endText();
              contentStream.close(); // don't forget that one!

        }

    }

    

    private static void addPages(int hdkdmot, int TOgo, PDDocument documentMain,PDDocument docpdf) 
    {
        for (int i = hdkdmot; i < TOgo; i++) 
        {
           docpdf.addPage(documentMain.getPage(i));
        }
    }
}
