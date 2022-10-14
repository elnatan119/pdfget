/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import org.apache.pdfbox.rendering.ImageType;

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
    static ArrayList<File> FilesS;
    // static JCheckBox pages4;
    // static final PDType1Font TIMES_ROMAN = new PDType1Font(Standard14Fonts.getMappedFontName("TIMES_ITALIC"));
    // a default constructor
    private static JCheckBox CHpages;
    static JCheckBox CHshrinkpdfPIC;
    static JCheckBox CHshrinkpdfnopic;
    private static JComboBox<ImageType> COMOdiagnosisList;
    private static JButton saveButton;
    private static JCheckBox CHpartCachck;
    private static JComboBox<Parts> COMOpartsList;
    private static JComboBox<DividingPages> COMODividingPagesList;
    private static JButton openButton;
    private static JButton printButton;
    private static JButton WorkButton;
    private static JButton ClearBotton;
    private static JButton SHOWwhitePagesButton;
    private static JTextArea TextPanel;
    private static JFrame f;
    private static JPanel shrinkpanel;
    private static JPanel spltpanel;

    private static JPanel pagesPanel;
    private static JPanel chackBokloly;
    private static JPanel ButtonsPanel;
    private static String maxSparte = "----------------------";
    private static JPanel textFieldWithButtonsPanel;
    private static int AllPdfPages = 0;

    filechooser() {
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    //fg
    public static void main(String[] args) throws IOException {
        mainFormIns();
        Inseverting();
        megsse(l.getText() + " for new pdf plese enter new pdf");
    }

    public static void Inseverting() throws HeadlessException {
        formOfchackboxAndComboboxIns();
        formOfButoonsIns();
        addingtoMainform();
        Default();
        showBuotoon(true);
    }

    private static void Default() {
        CHpages.setSelected(true);
        COMOpartsList.setSelectedItem(Parts.one);
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
        // f.getContentPane().add(TextPanel);
        f.getContentPane().add(textFieldWithButtonsPanel);
        f.pack();
        f.setVisible(true);
    }

    public static void formOfButoonsIns() {
        saveButton = new JButton("save");
        openButton = new JButton("open");
        printButton = new JButton("print");
        WorkButton = new JButton("strat Work");
        SHOWwhitePagesButton = new JButton("Show White Pages");

        // make an object of the class filechooser
        filechooser f1 = new filechooser();
        // add action listener to the button to capture user
        // response on buttons
        saveButton.addActionListener(f1);
        openButton.addActionListener(f1);
        printButton.addActionListener(f1);
        WorkButton.addActionListener(f1);
        ClearBotton.addActionListener(f1);
        SHOWwhitePagesButton.addActionListener(f1);
        // make a panel to add the buttons and labels
        ButtonsPanel = new JPanel();

        // add buttons to the frame
        ButtonsPanel.add(saveButton);
        ButtonsPanel.add(openButton);
        ButtonsPanel.add(printButton);
        ButtonsPanel.add(WorkButton);
        ButtonsPanel.add(SHOWwhitePagesButton);
        // set the label to its initial value
        l = new JLabel();

        // add panel to the frame
        ButtonsPanel.add(l);
    }

    public static void formOfchackboxAndComboboxIns() throws HeadlessException {
        chackBokloly = new JPanel();
        CHshrinkpdfPIC = new JCheckBox("Reducing the page and changing colors");
        CHshrinkpdfPIC.setBounds(100, 100, 50, 50);
        CHshrinkpdfnopic = new JCheckBox(" pdf to A4");
        CHshrinkpdfnopic.setBounds(100, 100, 50, 50);
        CHshrinkpdfPIC = new JCheckBox();
        COMOdiagnosisList = new JComboBox<>();
        shrinkpanel = panelofcomoandcheckbox(CHshrinkpdfPIC, COMOdiagnosisList, "srink and cange colors", ImageType.values());
        CHpartCachck = new JCheckBox();
        COMOpartsList = new JComboBox<>();
        spltpanel = panelofcomoandcheckbox(CHpartCachck, COMOpartsList, "split docmnet to parts", Parts.values());
        CHpages = new JCheckBox();
        COMODividingPagesList = new JComboBox<>();
   

        pagesPanel = panelofcomoandcheckbox(CHpages, COMODividingPagesList, "how much pages you want?", DividingPages.values());
        textFieldWithButtonsPanel = new JPanel(new FlowLayout(
                SwingConstants.LEADING, 5, 1));

        TextPanel = new JTextArea("drop in me", 5, 5);
        ClearBotton = new JButton("Clear List");
        textFieldWithButtonsPanel.add(ClearBotton);

        TextPanel.setBorder(null);
        //ClearBotton = new JButton("Clear");
        //TextPanel.add(ClearBotton);
        ClearBotton.setVisible(true);
        FilesS = new ArrayList<>();
        Font font = new Font("C:/windows/fonts/david.ttf", Font.PLAIN, 20);
        TextPanel.setFont(font);
        TextPanel.setForeground(Color.BLUE);
        dropMetue();
        chackBokloly.setLayout(new FlowLayout());

        chackBokloly.add(CHshrinkpdfnopic);
        textFieldWithButtonsPanel.add(TextPanel);

    }
//     private static void addButtonToPanel(JPanel panel, int height) {
//        BufferedImage bi = new BufferedImage(
//                // find the size of an icon from the system, 
//                // this is just a guess
//                24, height, BufferedImage.TYPE_INT_RGB);
//        JButton b = new JButton(new ImageIcon(bi));
//        b.setContentAreaFilled(false);
//        //b.setBorderPainted(false);
//        b.setMargin(new Insets(0,0,0,0));
//        panel.add(b);
//    }

  

    public static void WhitePage() {
        DividingPages h = (DividingPages) COMODividingPagesList.getSelectedItem();
        int Pages = h.TOint();
        int sharit = Pages - (AllPdfPages % Pages);

        if (sharit > 0 && sharit < Pages) {
            megsse(" to the fiile will add  " + sharit + " white Pages");
        }
        else
        {
             megsse(" to the fiile will add  " + sharit + " white Pages");
        }
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

    private static void PutTheNameAndInThelist(ArrayList<File> droppedFiles) {
        for (File file : droppedFiles) {
            String closer = "|";
            String sprate = "\n" + MakSparte(closer + file.getAbsolutePath() + closer) + "\n";

            FilesS.add(file);
            TextPanel.setText(TextPanel.getText() + sprate + closer + file.getAbsolutePath() + closer + sprate);
        }
        if (FilesS.size() > 1) {
            CHpartCachck.setSelected(true);
            CHpartCachck.setText("how much pdf? you want in the output ");
            CHpartCachck.setEnabled(false);
        }
        //ArrayList<String>

    }

    private static void showTheButoonOfDoWork() {
        WorkButton.setVisible(true);
       f.pack();
    }

    private static String MakSparte(String absolutePath) {
        if (maxSparte.length() < absolutePath.length()) {

            String k = "";
            maxSparte = "";
            for (int i = 0; i < absolutePath.length() + 10; i++) {
                k += "-";
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

                document = null;
            }
            case "open" -> {
                try {
                    openAfile();
                } catch (Exception ex) {
                    Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
                }

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
                    document.megsse("Finish Please save or print  the document", l);

                } catch (Exception ex) {
                    Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case "Clear List" -> {
                TextPanel.setText("drop in me ");
                FilesS = new ArrayList<>();
                megsse("Add File ");
                f.pack();

            }

            case "Show White Pages" -> {

                try {
                    AllPdfPages = pdfDoc.updteNumOfPage(FilesS);
                } catch (IOException ex) {
                    Logger.getLogger(filechooser.class.getName()).log(Level.SEVERE, null, ex);
                }
                WhitePage();

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
        textFieldWithButtonsPanel.setVisible(rest);
        CHshrinkpdfPIC.setVisible(rest);
        CHshrinkpdfnopic.setVisible(rest);
        // COMOdiagnosisList.setVisible(rest);
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
            File[] k = j.getSelectedFiles();
            ArrayList<File> mkm = new ArrayList<>(Arrays.asList(k));
            PutTheNameAndInThelist(mkm);
            showTheButoonOfDoWork();

        } // if the user cancelled the operation
        else {
            // megsse("the user cancelled the operation");
            //  restGui();
        }
    }

    public static void doTheWork() throws Exception {
        document = new pdfDoc();
        if (FilesS.size() > 1) {
            switch (getsplittype()) {
                case one:

                    ArrayOfPdfDocs.lodeFilesindoc(FilesS, document, getpagesneeds(), l, CHshrinkpdfPIC.isSelected(), getimgetype());
                    document.MakeADoc(CHshrinkpdfPIC.isSelected(), l, getimgetype(), getpagesneeds());
                    break;
                default:

                    documentS = LodeAndMakeADocS(FilesS, getpagesneeds(), l, getimgetype(), getsplittype());
            }
        } else {
            if (CHpartCachck.isSelected()) {
                switch (getsplittype()) {
                    case one:

                        document.loudAndMakeDoc(FilesS.get(0), FilesS.get(0).getAbsolutePath(), getpagesneeds(), l, CHshrinkpdfPIC.isSelected(), getimgetype());
                        break;
                    default:
                        document.loudDoc(FilesS.get(0), l);
                        documentS = new ArrayList<pdfDoc>();
                        documentS = ArrayOfPdfDocs.splitAndMakeDOC(document, getsplittype().TOint(), CHshrinkpdfnopic.isSelected(), l, getimgetype(), getpagesneeds(), documentS);
                }
            } else {
                document.loudAndMakeDoc(FilesS.get(0), FilesS.get(0).getAbsolutePath(), getpagesneeds(), l, CHshrinkpdfPIC.isSelected(), getimgetype());
            }

        }
        showBuotoon(false);
    }

    static ArrayList<pdfDoc> LodeAndMakeADocS(ArrayList<File> FilesS, DividingPages pagesneeds, JLabel l, ImageType imgetype, Parts part) throws Exception {
        ArrayList<pdfDoc> doc = new ArrayList<pdfDoc>();

        switch (part) {
            case orginal:
                for (var file : FilesS) {
                    pdfDoc a = new pdfDoc();
                    a.loudAndMakeDoc(file, file.getAbsolutePath(), pagesneeds, l, CHshrinkpdfPIC.isSelected(), imgetype);
                    doc.add(a);
                }
                break;
            case one:
                ArrayOfPdfDocs.lodeFilesindoc(FilesS, document, pagesneeds, l, CHshrinkpdfPIC.isSelected(), imgetype);
                document.MakeADoc(CHshrinkpdfPIC.isSelected(), l, imgetype, pagesneeds);
                break;
            default:
                int key = part.TOint();
                return lodeFilesAndSplitThem(FilesS, key, CHshrinkpdfPIC.isSelected(), l, imgetype, pagesneeds, doc);

        }
        return doc;
    }

    static ArrayList<pdfDoc> lodeFilesAndSplitThem(ArrayList<File> FilesS, int splittype, boolean selected, JLabel l, ImageType imgetype, DividingPages pagesneeds, ArrayList<pdfDoc> doc) throws Exception {

        ArrayOfPdfDocs.lodeFilesindoc(FilesS, document, pagesneeds, l, selected, imgetype);
        //  int g= document.getPages().getCount();

        documentS = ArrayOfPdfDocs.splitAndMakeDOC(document, splittype, selected, l, imgetype, pagesneeds, doc);
        return doc;
    }

    public void savaAfile() throws HeadlessException {
        // create an object of JFileChooser class
        JFileChooser elegidor = new JFileChooser();
        String name;
        File originalFile = elegidorWork(elegidor);

        // only allow files of .txt extension
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .pdf files", "pdf");

        elegidor.addChoosableFileFilter(restrict);

        elegidor.setCurrentDirectory(
                new File(System.getProperty("user.dir")));

        elegidor.setSelectedFile(originalFile);

        elegidor.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // invoke the showsSaveDialog function to show the save dialog
        int r = elegidor.showSaveDialog(null);

        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // set the label to the path of the selected file
// שים לבבבבבבבבבבבבבבבבב
            String SELAbsolutePath = pdfDoc.makingPDFend(elegidor.getSelectedFile().getAbsolutePath());
            try {
                if (documentS != null) {
                    if (getsplittype() == Parts.orginal) {
                        l.setText("[");
                        for (int i = 0; i < documentS.size(); i++) {
                            String nameO = SELAbsolutePath.substring(0, pdfDoc.getStartofname(SELAbsolutePath)) + documentS.get(i).getName() + ".pdf";

                            documentS.get(i).save(new File(nameO));
                            documentS.get(i).setNameFromAbsolutePath(nameO);
                            l.setText(documentS.get(i).getName() + ", ");
                        }
                        l.setText("] ");
                    } else {
                        l.setText("[");
                        for (int i = 0; i < documentS.size(); i++) {
                            String nameO = makingPartEnd(SELAbsolutePath, i);

                            documentS.get(i).save(new File(nameO));
                            documentS.get(i).setNameFromAbsolutePath(nameO);
                            l.setText(l.getText() + "" + documentS.get(i).getName() + " , ");
                        }
                    }
                    l.setText(l.getText() + " , ]saved ");
                } else {
                    String key = pdfDoc.makingPDFend(SELAbsolutePath);
                    document.save(new File(key));
                    l.setText(key + " saved");
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
        restGui();
    }

    public File elegidorWork(JFileChooser elegidor) {
        String name;
        if (documentS != null) {

            name = documentS.get(0).getName();

        } else {
            name = document.getName();
        }
        File originalFile = new File(System.getProperty("user.dir"), name + ".pdf");
        // set a title for the dialog
        String naString = "";
        if (CHpartCachck.isSelected()) {
            switch (getsplittype()) {
                case orginal:
                    elegidor.setDialogTitle("In original mode saves in **original names****");
                    break;

                default:
                    elegidor.setDialogTitle("sava the file whit the  name and parts");

            }
        } else {
            elegidor.setDialogTitle("sava the file whit the  name ");
        }
        return originalFile;
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
    private static DividingPages getpagesneeds() {
        return (DividingPages) COMODividingPagesList.getSelectedItem();
    }

    private static ImageType getimgetype() {
        return (ImageType) COMOdiagnosisList.getSelectedItem();
    }

    private String makingPartEnd(String text, int part) {
        part++;
        return text.substring(0, text.indexOf('.')) + " part " + part + text.substring(text.indexOf('.'), text.length());
    }

    private File getFormTextArea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static Parts getsplittype() {
        return (Parts) COMOpartsList.getSelectedItem();
    }

    private void restGui() {
        TextPanel.setText("drop in me ");
        CHpartCachck.setSelected(false);
        CHpartCachck.setEnabled(true);
        CHshrinkpdfPIC.setSelected(false);
        CHshrinkpdfnopic.setSelected(false);
        showBuotoon(true);
    }

}
