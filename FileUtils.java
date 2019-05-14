import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads input text file and creates
 * input char matrix corresponding to input map.
 *
 * Displays exception in case of incorrect path or empty file.
 */
 class FileUtils {
    public char[][] readFile(String filepath) {
        FileReader fr=null;
        StringBuffer bf=null;
        try {
            fr = new FileReader(filepath);
            bf = new StringBuffer();
            int i = 0;
            while ((i = fr.read()) != -1) {
                bf.append((char) i);
                System.out.print((char) i);
            }
            if(bf.toString().length()==0)
                throw new Exception("Input file is empty.");
            String[] fieldmap = (bf.toString().split("\\r\\n"));

            char[][] field = new char[fieldmap.length][fieldmap[0].length()];
            for (int j = 0; j < field.length; j++) {
                field[j] = fieldmap[j].toCharArray();
            }
            return field;
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            if(fr!=null)
            {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
