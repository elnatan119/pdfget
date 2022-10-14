import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xslf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.main.*;

public class PowerPointChangeThemeFonts {

 enum Script {
  LATIN, //Latin script
  EA, //east Asia script
  CS //complex script
 }

 static void setMajorFont(XSLFTheme theme, Script script, String typeFace) {
  CTOfficeStyleSheet styleSheet = theme.getXmlObject();
  CTBaseStyles themeElements = styleSheet.getThemeElements();
  CTFontScheme fontScheme = themeElements.getFontScheme();
  CTFontCollection fontCollection = fontScheme.getMajorFont();
  CTTextFont textFont = null;
  if (null != script) switch (script) 
  {
         case LATIN:
             textFont = fontCollection.getLatin();
             textFont.setTypeface(typeFace);
             break;
         case EA:
             textFont = fontCollection.getEa();
             textFont.setTypeface(typeFace);
             break;
         case CS:
             textFont = fontCollection.getCs();
             textFont.setTypeface(typeFace);
             break;
         default:
             break;
     }
 }

 static void setMinorFont(XSLFTheme theme, Script script, String typeFace) {
  CTOfficeStyleSheet styleSheet = theme.getXmlObject();
  CTBaseStyles themeElements = styleSheet.getThemeElements();
  CTFontScheme fontScheme = themeElements.getFontScheme();
  CTFontCollection fontCollection = fontScheme.getMinorFont();
  CTTextFont textFont = null;
  if (null != script) switch (script) {
         case LATIN:
             textFont = fontCollection.getLatin();
             textFont.setTypeface(typeFace);
             break;
         case EA:
             textFont = fontCollection.getEa();
             textFont.setTypeface(typeFace);
             break;
         case CS:
             textFont = fontCollection.getCs();
             textFont.setTypeface(typeFace);
             break;
         default:
             break;
     }
 }
}