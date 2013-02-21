/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package activity2event;

/**
 *
 * @author flag
 */

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author flag
 */

public class FileExtensionFilter implements FilenameFilter{
   private String ext="*";
   public FileExtensionFilter(String ext){
      this.ext = ext;
   }
   public boolean accept(File dir, String name){
     if (name.endsWith(ext)) {
           return true;
       }
     return false;
   }
}