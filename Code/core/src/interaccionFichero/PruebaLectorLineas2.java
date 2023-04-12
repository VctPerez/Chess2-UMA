package interaccionFichero;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Path.of;

public class PruebaLectorLineas2 {
    private List<String> lines;

    public PruebaLectorLineas2(String path){
        //Path p = Path.of(path);
        try{
            lines = Files.readAllLines(new File(getClass().getResource("/file.txt").toURI()).toPath());
        }catch (IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getLines() {
        return lines;
    }
    public String getLine(int index){
        return lines.get(index-1);
    }
    public int getIntLine(int index){
        return Integer.parseInt(lines.get(index-1));
    }
    public boolean getBooleanLine(int index){
        return Boolean.parseBoolean(lines.get(index-1));
    }

    public List<String> getLinesRange(int firstIndex, int lastIndex){
        return new ArrayList<>(lines.subList(firstIndex-1,lastIndex-1));
    }
}
