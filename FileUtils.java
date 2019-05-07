import java.io.FileReader;
import java.io.IOException;

/**
 * Reads input text file and creates
 * input char matrix corresponding to input map.
 */
public class FileUtils {
    public char[][] readFile(String filepath) {
        FileReader fr=null;
        StringBuffer bf=null;
        int i=0;
        try {
            fr = new FileReader(filepath);
            bf = new StringBuffer();
            while ((i = fr.read()) != -1) {
                bf.append((char) i);
                System.out.print((char) i);
            }
            String[] fieldmap = (bf.toString().split("\\r\\n"));
            if(fieldmap.length<=0)
                throw new Exception("Input file is empty");
            char[][] field = new char[fieldmap.length][fieldmap[0].length()];
            for (int j = 0; j < field.length; j++) {
                field[j] = fieldmap[j].toCharArray();
            }
            return field;
        } catch (Exception e) {
            System.out.println("Enter valid file!!");
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
