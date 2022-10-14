/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
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
import org.apache.pdfbox.pdmodel.PDPageTree;
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
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTheme;

/**
 *
 * @author User
 */
enum DividingPages {
    sixteen,
    Eight,
    Four,
    Oldway;

    int TOint() 
    {
       switch(this)
       {
           case Eight -> {
               return 8;
            }
            case Four -> {
                return 4;
            }
             case sixteen -> {
                 return 16;
            }
            case Oldway -> {
                return 4;
            }
       }
        return 0;
    }
}

class FillCL {

    enum Fill {
        fill,
        noFiill,;
    }
    Color g;
    Fill kj;

    public FillCL(Color g, Fill kj) {
        this.g = g;
        this.kj = kj;
    }

    public Color getG() {
        return g;
    }

    public void setG(Color g) {
        this.g = g;
    }

    public Fill getKj() {
        return kj;
    }

    public void setKj(Fill kj) {
        this.kj = kj;
    }

}

enum Parts {
    four,
    three,
    two, orginal, one;

     int TOint() {

        switch (this) 
        {
            case one:
                return 1;
            case two:
                return 2;
            case three:
                return 3;
            case four:
                return 4;
            case orginal:
                return -1;
            default:
                return -1;
        }

    }
}

public class pdfDoc extends PDDocument {

    static int updteNumOfPage(ArrayList<File> AbsolutePathS ) throws IOException {
        pdfDoc h = new pdfDoc();
       int  allPages = 0;
        for (var fileWay : AbsolutePathS) {
            if (itispdf(fileWay.getAbsolutePath())) {
                h.lodepdf(fileWay);
                allPages = allPages + h.getNumberOfPages();
                //  document.Chackboxtog(pages8, pages4);
            } else if (HaveTheEnd(fileWay.getAbsolutePath(), "pptx") || HaveTheEnd(fileWay.getAbsolutePath(), "ppt")) {
                 FileInputStream is = new FileInputStream(fileWay);
                 XMLSlideShow ppt = new XMLSlideShow(is);
              allPages=   ppt.getSlides().size();
            }
        }
        return allPages;
    }

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
    private PDDocument document = new PDDocument();
    private String Name = "filePdf";

    public void setDocument(PDDocument document) {
        this.document = document;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;

    }

    public void setNameFromAbsolutePath(String Name) {
        this.Name = MakeNameFromAbsolutePath(Name);

    }

    @Override
    public PDPage getPage(int pageIndex) {
        return document.getPage(pageIndex);
    }

    @Override
    public void addPage(PDPage page) {
        document.addPage(page);
    }

    /**
     *
     * // * @param g-איזה סוג דף זה
     */
    public void lodepdf(File file) throws IOException {
        document = Loader.loadPDF(file);

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
        return document;
    }

    private static void putNUMBER(PDPageContentStream contentStream, PDFont font, int size, PDPage page, int offset_Y, String numberingFormat, int page_counter) throws IOException {
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

    public static PDDocument generateSideBySidePDF(PDDocument document, PDRectangle PDRhrinkpdfnopic, DividingPages g, FillCL fille) {
        PDDocument pdf1 = document;
        var outPdf2 = new PDDocument();
        PDDocument outPdf;
        // megsse(pdf1.getNumberOfPages() + "", filechooser.l);
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
//                if (srinknopic) {
//                    outPdfFrame = new PDRectangle(PDRhrinkpdfnopic.getWidth() * 2, PDRhrinkpdfnopic.getHeight());
//                }
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
                AffineTransform afLeft = AffineTransform.getTranslateInstance(pdf2Frame.getWidth(), 0.0);
                layerUtility.appendFormAsLayer(outPdfPage, formPdf1, afLeft, "left");
                AffineTransform afRight = new AffineTransform();
                layerUtility.appendFormAsLayer(outPdfPage, formPdf2, afRight, "right");

                pdfconter += 2;
                outPdf2.addPage(outPdf.getPage(0));

                pdfDoc.borderPage(outPdf2.getNumberOfPages() - 1, outPdf2, Color.BLACK);

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

                COSDictionary dict = MakePageOfTowPages(pdf1Frame, pdf2Frame);
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

                pdfDoc.borderPage(outPdf2.getNumberOfPages() - 1, outPdf2, Color.BLACK);
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

    public static COSDictionary MakePageOfTowPages(PDRectangle pdf1Frame, PDRectangle pdf2Frame) {
        PDRectangle outPdfFrame = new PDRectangle(Math.max(pdf1Frame.getWidth(), pdf2Frame.getWidth()), pdf1Frame.getHeight() + pdf2Frame.getHeight());
//                if (srinknopic) {
//                    outPdfFrame = new PDRectangle(A4.getWidth() * 2, A4.getHeight() * 2);
//                }
        COSDictionary dict = new COSDictionary();
        dict.setItem(COSName.TYPE, COSName.PAGE);
        dict.setItem(COSName.MEDIA_BOX, outPdfFrame);
        dict.setItem(COSName.CROP_BOX, outPdfFrame);
        dict.setItem(COSName.ART_BOX, outPdfFrame);
        return dict;
    }

    public static COSDictionary MakePageinSemeRectangle(PDRectangle pdf1Frame) {
        COSDictionary dict = new COSDictionary();
        dict.setItem(COSName.TYPE, COSName.PAGE);
        dict.setItem(COSName.MEDIA_BOX, pdf1Frame);
        dict.setItem(COSName.CROP_BOX, pdf1Frame);
        dict.setItem(COSName.ART_BOX, pdf1Frame);
        return dict;
    }

    private static PDDocument book8pages(PDDocument document) {

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
        PDPage p;
        System.out.println("start=" + start + "end= " + end);
        while (start <= end) {
            //System.out.println("start=" + start + "end= " + end);
            if (start > document.getNumberOfPages() - 1 && a[start] == 0) {
                if (end < document.getNumberOfPages() - 1 && a[end] == 0) {
                    AD.addPage(new PDPage(MakePageinSemeRectangle(document.getPage(end).getCropBox())));
                } else {
                    AD.addPage(new PDPage(MakePageinSemeRectangle(PDRectangle.A4)));
                }
                a[start] = 1;
            } else if (a[start] == 0) {
                AD.addPage(document.getPage(start));
                a[start] = 1;

            } else {
                key = 1;
            }
            if (end > document.getNumberOfPages() - 1 && a[end] == 0) {
                if (start < document.getNumberOfPages() - 1 && a[start] == 0) {
                    AD.addPage(new PDPage(MakePageinSemeRectangle(document.getPage(start).getCropBox())));
                } else {
                    AD.addPage(new PDPage(MakePageinSemeRectangle(PDRectangle.A4)));
                }
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

    private PDDocument peges2(PDDocument doc) {
        PDDocument d = new PDDocument();
        d.addPage(doc.getPage(0));
        d.addPage(doc.getPage(1));
        return d;

    }

    private static PDDocument book4pages(PDDocument document) {
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
                if (start < document.getNumberOfPages() - 1) {
                    AD.addPage(new PDPage(MakePageinSemeRectangle(document.getPage(start).getCropBox())));
                } else {
                    AD.addPage(new PDPage(MakePageinSemeRectangle(PDRectangle.A4)));
                }

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

    public static PDDocument ShrinkPDF(PDRectangle K, ImageType m, PDDocument document) {
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
        return pdDocument;

    }

    public void lodepowerpoint(PDRectangle K, ImageType m, String isk) throws Exception {
        FileInputStream is = new FileInputStream(isk);
        PDDocument pdDocument = new PDDocument();
        XMLSlideShow ppt = new XMLSlideShow(is);
        if (ppt.getSlideMasters().size() > 0) {
            XSLFSlideMaster master = ppt.getSlideMasters().get(0);
            XSLFTheme theme = master.getTheme();
            PowerPointChangeThemeFonts.setMajorFont(theme, PowerPointChangeThemeFonts.Script.CS, "Times New Roman");
            PowerPointChangeThemeFonts.setMinorFont(theme, PowerPointChangeThemeFonts.Script.CS, "Times New Roman");

            Dimension pgsize = ppt.getPageSize();

            List<XSLFSlide> slide = ppt.getSlides();

            PDPage page = null;
            for (int i = 0; i < slide.size(); i++) {
                page = new PDPage(K);
                BufferedImage img = new BufferedImage(pgsize.width + 40, pgsize.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();

                //set scaling transform
                //  graphics.setTransform(at);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics.setRenderingHint(
                        RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                graphics.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(
                        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                graphics.setRenderingHint(
                        RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

                //clear the drawing area
                graphics.setPaint(Color.white);

                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                //render

                slide.get(i).draw(graphics);
                PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
                PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, img);
                float newHeight = K.getHeight();
                float newWidth = K.getWidth();
                contentStream.drawImage(pdImage, 0, 0, K.getWidth(), K.getHeight());
                contentStream.close();

                pdDocument.addPage(page);

            }
            document = pdDocument;
        }
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

    public static PDDocument bookops(PDDocument document, PDDocument doc2, JLabel l, DividingPages g, FillCL Four) throws IOException {
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
        if (null != g) {
            switch (g) {
                case Eight:
                    //megsse("Prepares  eight pages ", l);
                    document = book8pages(document);
                    break;
                case Four:
                    document = book4pages(document);
                    //    megsse("Prepares four pages", l);
                    break;

                default:
                    break;
            }

        }
        p = new PDPage();
//          for (int i = 0; i < (document.getNumberOfPages() % 2); i++) 
//        {
//
//            document.addPage(p);
//        }
        System.out.println("document after adding  A=" + document.getNumberOfPages());
        document = generateSideBySidePDF(document, PDRectangle.A4, g, Four);
        System.out.println("document before adding b=" + document.getNumberOfPages());

        System.out.println("document after adding b=" + document.getNumberOfPages());
        if (g == DividingPages.Eight) {
            document = generate4BySidePDF(document, PDRectangle.A4);
        }
        System.out.println("document end =" + document.getNumberOfPages());
        document = addPageNumbers(document, " {0}", 60, 18, 40, 1, false);
        return document;
    }

    private static PDDocument regluerOpsinOFmulti(PDDocument document, PDDocument doc2, PDDocument doc, DividingPages g, boolean srinkNopic) throws IOException {
        for (int i = 0; i < document.getNumberOfPages() % 2; i++) {
            doc2.addPage(document.getPage(((document.getNumberOfPages() / 2) * 2) + i));

        }
        System.out.println("doc2=" + doc2.getNumberOfPages());
        generateSideBySidePDF(document, PDRectangle.A4, g, new FillCL(Color.gray, FillCL.Fill.fill));
        for (int i = 0; i < document.getNumberOfPages() % 2; i++) {
            doc.addPage(document.getPage(((document.getNumberOfPages() / 2) * 2) + i));

        }
        System.out.println("doc1=" + doc2.getNumberOfPages());
        generate4BySidePDF(document, PDRectangle.A4);
        for (int i = 0; i < doc.getNumberOfPages(); i++) {
            document.addPage(doc.getPage(i));

        }
        for (int i = 0; i < doc2.getNumberOfPages(); i++) {

            document.addPage(doc2.getPage(i));
        }
        addPageNumbers(document, " {0}", 60, 18, 40, 1, false);
        return document;
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
        // set the label to the path of the selected fileWay

        loudDoc(file, l);
//        megsse("loading PDF.....................", l);
//        if (itispdf(getAbsolutePath)) {
//
//            lodepdf(fileWay);
//            //  document.Chackboxtog(pages8, pages4);
//        } else {
//            lodepowerpoint(PDRectangle.A4, ImageType.RGB, getAbsolutePath);
//            // document.Chackboxtog(pages8, pages4);
//// document = powerpointtopdf(PDRectangle.A4, ImageType.RGB, j.getSelectedFile().getAbsolutePath());
//        }

        MakeADoc(shrinkpdfPIC, l, diagnosisListgetSelectedItem, g);

    }

    public void loudDoc(File file, JLabel l) throws Exception {

        // set the label to the path of the selected fileWay
        String getAbsolutePath = file.getAbsolutePath();
        Name = MakeNameFromAbsolutePath(getAbsolutePath);
        megsse("loading   " + Name + ".................", l);
        if (itispdf(file.getAbsolutePath())) {

            lodepdf(file);
            //  document.Chackboxtog(pages8, pages4);
        } else if (HaveTheEnd(getAbsolutePath, "pptx") || HaveTheEnd(getAbsolutePath, "ppt")) {
            lodepowerpoint(PDRectangle.A4, ImageType.RGB, file.getAbsolutePath());
            // document.Chackboxtog(pages8, pages4);
// document = powerpointtopdf(PDRectangle.A4, ImageType.RGB, j.getSelectedFile().getAbsolutePath());
        }

        //    MakeADoc(shrinkpdfPIC, l, diagnosisListgetSelectedItem, g);
    }

    @Override
    public PDPageTree getPages() {
        return document.getPages();
    }

    public void MakeADoc(boolean shrinkpdfPIC, JLabel l, ImageType diagnosisListgetSelectedItem, DividingPages g) throws IOException {
        var doc = new PDDocument();
        var doc2 = new PDDocument();
        if (shrinkpdfPIC) {
            megsse("Reducing " + Name, l);
            document = ShrinkPDF(PDRectangle.A4, (diagnosisListgetSelectedItem), document);
        }
        megsse("adding page numbers.............." + Name, l);
        addPageNumbers(document, " {0}", 60, 18, 20, 1, true);
        System.out.println("document =" + document);
        if (null != g) {
            switch (g) {
                case Eight:
                case Four:
                    document = bookops(document, doc2, l, g, new FillCL(null, FillCL.Fill.fill));
                    break;
                case sixteen:
                    document = bookops(document, doc2, l, DividingPages.Eight, new FillCL(null, FillCL.Fill.fill));
                    doc2 = new PDDocument();
                    doc = new PDDocument();
                    document = bookops(document, doc2, l, DividingPages.Four, new FillCL(Color.BLACK, FillCL.Fill.fill));
                    break;
                case Oldway:
                    document = regluerOpsinOFmulti(document, doc2, doc, g, shrinkpdfPIC);
                    break;
                default:
                    break;
            }
        }

    }

    public static boolean itispdf(String absolutePath) {
        return absolutePath.substring(absolutePath.length() - 5).contains(".pdf");
    }

    public static boolean HaveTheEnd(String absolutePath, String end) {
        return absolutePath.substring(absolutePath.indexOf('.')).contains(end);
    }

    public static String makingPDFend(String absolutePath) {
        if (!itispdf(absolutePath)) {
            return absolutePath + ".pdf";
        }
        return absolutePath;
    }

    public static void StraterPage(String name, pdfDoc pdffoc, int i) throws IOException {
        PDFont font = PDType0Font.load(pdffoc, new File("C:/windows/fonts/david.ttf"));
        try (PDPageContentStream contentStream = new PDPageContentStream(pdffoc, pdffoc.getPage(0), PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.setFont(font, 50);
            contentStream.beginText();

            PDRectangle pageSize = pdffoc.getPage(0).getMediaBox();
            float x = pageSize.getLowerLeftX();
            float y = pageSize.getLowerLeftY();
            // contentStream.newLineAtOffset(x + (pageSize.getWidth() / 2), y + (pageSize.getHeight() / 2));
            contentStream.newLineAtOffset(0, 400);
            i++;
            String text = ((String) name.subSequence(0, name.indexOf("."))) + "  part  " + i;

            contentStream.showText(text);
            contentStream.endText();
            contentStream.close(); // don't forget that one!

        }

    }

    public static void addPages(int hdkdmot, int TOgo, PDDocument documentMain, PDDocument docpdf) {
        for (int i = hdkdmot; i < TOgo; i++) {
            docpdf.addPage(documentMain.getPage(i));
        }
    }

    @Override
    public int getNumberOfPages() {
        return document.getPages().getCount();
    }

    public static void borderPage(int pageCout, PDDocument document, Color j) throws IOException {

        try (PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(pageCout), PDPageContentStream.AppendMode.APPEND, true, true)) {

            PDRectangle pageSize = document.getPage(pageCout).getMediaBox();

            drawRect(contentStream, j, pageSize, false);
            contentStream.close(); // don't forget that one!

        }

    }

    static void drawRect(PDPageContentStream content, Color color,
            PDRectangle rect, boolean fill) throws IOException {
        content.addRect(rect.getLowerLeftX(), rect.getLowerLeftY(), rect.getWidth(), rect.getHeight());
        if (fill) {
            content.setNonStrokingColor(color);
            content.fill();
        } else {
            content.setStrokingColor(color);
            content.stroke();
        }
    }

    private String MakeNameFromAbsolutePath(String absolutePath) {

        String key = absolutePath.substring(getStartofname(absolutePath), getEndname(absolutePath));

        return key;
    }

    public static int getStartofname(String absolutePath) {
        int endname = absolutePath.indexOf(".");
        for (int i = endname; i >= 0;) {
            if (absolutePath.charAt(i) == '\\') {

                return i + 1;
            }
            i--;
        }
        return -1;
    }

    public static int getEndname(String absolutePath) {

        return absolutePath.indexOf(".");
    }

}
