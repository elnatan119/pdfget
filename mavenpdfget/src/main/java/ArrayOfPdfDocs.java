
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
public class ArrayOfPdfDocs {

    static void lodeFilesindoc(ArrayList<File> FilesS, pdfDoc doc, DividingPages pagesneeds, JLabel l, boolean shrinkpdfPIC, ImageType imgetype) throws Exception {

        pdfDoc azer = new pdfDoc();
        for (int i = 0; i < FilesS.size(); i++) {

            azer.loudDoc(FilesS.get(i), l);
            if (i == 0) {
                doc.setName(azer.getName());
            }
            for (int g = 0; g < azer.getPages().getCount(); g++) {
                doc.addPage(azer.getPage(g));
            }
        }

    }

//    static void lodeFiles(ArrayList<File> FilesS, DividingPages pagesneeds, JLabel l, boolean selected, ImageType imgetype) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public static ArrayList<pdfDoc> LodeAndMakeADocS(File file, String absolutePath, DividingPages pagesneeds, JLabel l, boolean shrinkpdfnopicBool, ImageType imgetype, int splittype) throws IOException, Exception {
        ArrayList<pdfDoc> doc = new ArrayList<pdfDoc>();
        pdfDoc document =new pdfDoc();
        document.loudDoc(file, l);
        return splitAndMakeDOC(document, splittype, shrinkpdfnopicBool, l, imgetype, pagesneeds, doc);
    }

    public static ArrayList<pdfDoc> splitAndMakeDOC(pdfDoc document, int splittype, boolean shrinkpdfnopicBool, JLabel l, ImageType imgetype, DividingPages pagesneeds, ArrayList<pdfDoc> doc) throws IOException {
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
            if (i + 2 == splittype) {
                TOgo = document.getNumberOfPages();

            } else {
                TOgo = TOgo + part;
            }
            pdffoc.MakeADoc(shrinkpdfnopicBool, l, imgetype, pagesneeds);
            doc.add(pdffoc);
            if(i==0)
            {
                doc.get(0).setName(document.getName());
            }

        }
        return doc;
    }

}
