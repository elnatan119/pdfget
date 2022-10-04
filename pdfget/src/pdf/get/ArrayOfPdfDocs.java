package pdf.get;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public  class ArrayOfPdfDocs 
{

   

    static void lodeFilesindoc(ArrayList<File> FilesS, pdfDoc doc, DividingPages pagesneeds, JLabel l, boolean selected, ImageType imgetype) throws Exception 
    {
        pdfDoc azer = new pdfDoc();
        for ( var File: FilesS) 
        {
            azer.loudDoc(File, File.getAbsolutePath(), pagesneeds, l, selected, imgetype);
            for (int i = 0; i < azer.getPages().getCount(); i++) 
            {
               doc.addPage(azer.getPage(i));
            }
        }
       
    }

   
//    static void lodeFiles(ArrayList<File> FilesS, DividingPages pagesneeds, JLabel l, boolean selected, ImageType imgetype) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
       public static   ArrayList<pdfDoc> LodeAndMakeADocS(  File file, String absolutePath, DividingPages pagesneeds, JLabel l, boolean shrinkpdfnopicBool, ImageType imgetype, int splittype) throws IOException, Exception 
        {
        ArrayList<pdfDoc> doc = new ArrayList<pdfDoc>();
        PDDocument document = Loader.loadPDF(file);
        return splitAndMakeDOC(document, splittype, shrinkpdfnopicBool, l, imgetype, pagesneeds, doc);
    }

    public static ArrayList<pdfDoc> splitAndMakeDOC(PDDocument document, int splittype, boolean shrinkpdfnopicBool, JLabel l, ImageType imgetype, DividingPages pagesneeds, ArrayList<pdfDoc> doc) throws IOException {
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
            doc.set(i, pdffoc);
            
        }
        return doc;
    }
    
}
