
package file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.UUID;

/**
 * Utility class to do action on the file (count lines, split file into three
 * subfiles, ...)
 *
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public abstract class FileUtility {

    /**
     * Get the path to the folder where the application is executed
     */
    public static final String dir = System.getProperty("user.dir");
    /**
     * Path to the temporary directory
     */
    public static final String tmp = dir + "/tmp/";
    /**
     * Extension for temporary text files
     */
    public static final String extension_tmp = ".tmp";
    /**
     * Extension for the output file
     */
    public static final String extension_file = ".cpt";
    /**
     * Extension for the crypted part file
     */
    public static final String extension_crypt = ".bin";
    /**
     * Get the line separator by default
     */
    public static final String separator = System.getProperty("line.separator");
    
    
    /**
     * Split a file into 3 subfiles (1: not crypted, 2: to be crypted, 3: not crypted)
     *
     * @param filePath String path to the file to be splitted
     * @param begin begin line to split the file
     * @param end end line to split the file
     * @return String prefix of the temporary subfiles
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String split(String filePath, int begin, int end) throws FileNotFoundException, IOException {
        // scan the file to be splitted
        Scanner scan = new Scanner(new FileReader(filePath));

        // create temporary directory if not exists
        if (! new File(tmp).exists())
            new File(tmp).mkdir();
        
        // create the file parts to be completed and initialize them
        String prefix = UUID.randomUUID().toString();
        FileWriter files[] = new FileWriter[3];
        for (int i = 0; i < 3; i++)
            files[i] = new FileWriter(tmp + prefix + "-" + Integer.toString(i + 1) + extension_tmp);

        // separate the file into three parts
        int actual = 0;
        int lines = lines(filePath);
        String text;
        while (scan.hasNextLine()) {
            text = scan.nextLine();
            // add newline only if this is not the end of the part file
            if (actual != begin - 1 && actual != end && actual != lines - 1)
                text += separator;

            // place text in the right part
            if (actual < begin)
                files[0].append(text);
            else if (actual > end)
                files[2].append(text);
            else
                files[1].append(text);

            actual++;
        }

        // close temporary files
        for (int i = 0; i < 3; i++)
            files[i].close();

        // close scan
        scan.close();

        // return the temporary files’ prefix
        return prefix;
    }
    
    /**
     * Split the file into two not crypted file.
     * Action done in order to recompose the entire file.
     * 
     * @param filePath String path to the file to be unaggregated
     * @param marker line where the file whould be splitted
     * @return String prefix of the temporary files
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String unaggregate(String filePath, int marker) throws FileNotFoundException, IOException {
        // scan the file to be splitted
        Scanner scan = new Scanner(new FileReader(filePath));

        // create temporary directory if not exists
        if (! new File(tmp).exists())
            new File(tmp).mkdir();
        
        // create the file parts to be completed and initialize them
        String prefix = UUID.randomUUID().toString();
        FileWriter files[] = new FileWriter[2];
        files[0] = new FileWriter(tmp + prefix + "-" + Integer.toString(1) + extension_tmp);
        files[1] = new FileWriter(tmp + prefix + "-" + Integer.toString(3) + extension_tmp);

        // separate the file into two parts
        int actual = 0;
        int end = lines(filePath) - 2;
        String text;
        while (scan.hasNextLine()) {
            text = scan.nextLine();
            // add newline only if this is not the end of the part file
            if (actual != marker-1 && actual != end-1)
                text += separator;
            
            // place text in the right part
            if (actual < marker)
                files[0].append(text);
            else if (actual < end)
                files[1].append(text);

            actual++;
        }

        // close temporary files
        for (int i = 0; i < 2; i++)
            files[i].close();

        // close scan
        scan.close();

        // return the temporary files’ prefix
        return prefix;
    }
    
    /**
     * Aggregate file from the two uncrypted parts.
     * Generate a single file, and add at the end marker (to know where split the file). 
     * 
     * @param prefix String prefix of the temporary subfiles
     * @param output String path of the output file wich will be created
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void aggregate(String prefix, String output) throws FileNotFoundException, IOException {
        Scanner scan;
        FileWriter out = new FileWriter(output);
        
        // add the first part (not crypted)
        scan = new Scanner(new FileReader(tmp + prefix + "-1" + extension_tmp));
        while (scan.hasNextLine())
            out.append(scan.nextLine() + separator);
        scan.close();
        out.append(separator);
        
        // add the third part (not crypted)
        scan = new Scanner(new FileReader(tmp + prefix + "-3" + extension_tmp));
        while (scan.hasNextLine())
            out.append(scan.nextLine() + separator);
        scan.close();
        
        // write the lines which are encrypted
        out.append(separator + lines(tmp + prefix + "-1" + extension_tmp));
        
        // close open stream
        out.close();
    }
    
    /**
     * Recompose file from the three parts of itself.
     * Need the two not crypted parts, and the part just decrypted.
     * Generate a single file, totaly decrypted.
     * 
     * @param prefix String prefix of the temporary files
     * @param output String path to the output file
     * @throws IOException 
     */
    public static void recompose(String prefix, String output) throws IOException {
        Scanner scan;
        FileWriter out = new FileWriter(output);
        
        // add the first part to recompose the initial file
        scan = new Scanner(new FileReader(tmp + prefix + "-1" + extension_tmp));
        while (scan.hasNextLine())
            out.append(scan.nextLine() + separator);
        scan.close();
        
        // add the second part to recompose the initial file
        scan = new Scanner(new FileReader(tmp + prefix + "-2" + extension_tmp));
        while (scan.hasNextLine())
            out.append(separator + scan.nextLine());
        scan.close();
        
        // add the third part to recompose the initial file
        scan = new Scanner(new FileReader(tmp + prefix + "-3" + extension_tmp));
        while (scan.hasNextLine())
            out.append(separator + scan.nextLine());
        scan.close();
        
        // close open stream
        out.close();
    }
    
    /**
     * Method to read and display a text file.
     * Return a String with the content of the readed file.
     * 
     * @param filePath String path to the text file you want to read
     * @return String content of the file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String display(String filePath) throws FileNotFoundException, IOException {
        // scan the file to be displayed
        Scanner scan = new Scanner(new FileReader(filePath));
        StringBuilder result = new StringBuilder();

        // separate the file into three parts
        int actual = 0;
        int end = lines(filePath) - 2;
        while (scan.hasNextLine()) {
            // add text (but not the two last lines, which contains the marker)
            if (actual < end - 1)
                result  .append(actual+1)
                        .append("\t")
                        .append(scan.nextLine())
                        .append(separator);
            else if (actual < end)
                result  .append(actual+1)
                        .append("\t")
                        .append(scan.nextLine());
            else
                scan.nextLine();
            actual++;
        }

        // close scanner
        scan.close();

        // result
        return result.toString();
    }
    
    /**
     * Return the number of lines in a file.
     * (If 0 is returned, it's mean that the file countain only one blank line)
     *
     * @param filePath path to the file to count lines
     * @return number of lines in the file
     * @throws IOException
     */
    public static int lines(String filePath) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n' || c[i] == '\r') {
                        count++;
                    }
                }
            }
            return (count == 0 && empty) ? 0 : count + 1;
        }
    }
    
    /**
     * Get the last line of a file.
     * Return null is an error occured.
     * 
     * @param filePath name of the file
     * @return String content of the last line of the file
     */
    public static String last(String filePath) {
        try {
            File file = new File(filePath);
            RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;
            StringBuilder sb = new StringBuilder();

            for (long filePointer=fileLength; filePointer!=-1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength)
                        continue;
                    break;
                }
                else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1)
                        continue;
                    break;
                }

                sb.append( (char) readByte );
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        }
        catch(Exception ex) {
            return null;
        }
    }
    
    /**
     * Remove temporary files and folder tmp.
     */
    public static void clean() {
        if (new File(tmp).exists()) {
            File f = new File(tmp);
            String[] files = f.list();
            // delete all files in directory
            for (int i=0; i<files.length; i++)
                new File(tmp + files[i]).delete();
            // delete directory
            new File(tmp).delete();
        }
    }
    

}
