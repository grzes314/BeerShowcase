
package beershowcase.lazyresources;

import beershowcase.utils.Box;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Grzegorz Łoś
 */
public class LazyText extends LazyResource {
    
    private static final ObjectCache CACHE = new SimpleCache();

    public LazyText(String pathOnFileSystem) {
        super(pathOnFileSystem);
    }
    
    public void setText(String text) {
        setResource(text);
    }
          
    public Box<String> getText(FileSystem fileSystem) {
        Box box =  getResource(fileSystem);
        if (box.isEmpty())
            return new Box<>();
        else
            return new Box<>((String) box.getValue());
    }

    @Override
    protected ObjectCache getCache() {
        return CACHE;
    }

    @Override
    protected Object read(FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath(getPathOnFileSystem());
        try (   BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            return sb.toString();
        }
    }

    @Override
    protected void write(Object ob, FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath(getPathOnFileSystem());
        
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir))
            Files.createDirectories(parentDir);
        
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
            writer.append(ob.toString());
        }
    }
    
}
