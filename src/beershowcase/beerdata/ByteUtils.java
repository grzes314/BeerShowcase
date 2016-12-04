
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author grzes
 */
public class ByteUtils {

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);  
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES); 
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }
    
    public static byte[] intToBytes(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);  
        buffer.putInt(0, x);
        return buffer.array();
    }

    public static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES); 
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getInt();
    }

    private static String bytesToString(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < chars.length; ++i)
            chars[i] = (char) bytes[i];
        return new String(chars);
    }
    
    
    
    
    
    public static void addIntToByteStream(int val, ByteArrayOutputStream out) {
        byte[] bytes = intToBytes(val);
        out.write(bytes, 0, bytes.length);
    }
    
    public static int readIntFromByteStream(ByteArrayInputStream in) {
        byte[] bytes = new byte[Integer.BYTES];
        in.read(bytes, 0, bytes.length);
        return bytesToInt(bytes);
    }
    
    public static void addLongToByteStream(long val, ByteArrayOutputStream out) {
        byte[] bytes = longToBytes(val);
        out.write(bytes, 0, bytes.length);
    }
    
    public static long readLongFromByteStream(ByteArrayInputStream in) {
        byte[] bytes = new byte[Long.BYTES];
        in.read(bytes, 0, bytes.length);
        return bytesToLong(bytes);
    }
    
    public static void addStringToByteStream(String str, ByteArrayOutputStream out) {
        byte[] strBytes = str.getBytes();
        addIntToByteStream(strBytes.length, out);
        out.write(strBytes, 0, strBytes.length);
    }
    
    public static String readStringFromByteStream(ByteArrayInputStream in) {
        int size = readIntFromByteStream(in);
        byte[] bytes = new byte[size];
        in.read(bytes, 0, bytes.length);
        return bytesToString(bytes);
    }

    public static void addCollectionToByteStream(Collection<? extends ByteEntity> coll, ByteArrayOutputStream out) {
        addIntToByteStream(coll.size(), out);
        for (ByteEntity e: coll)
            e.addToByteStream(out);
    }
    
    public static <T extends ByteEntity> void readCollectionFromByteStream(
            ByteArrayInputStream in, Collection<T> coll, Class<T> entityClass){
        int n = readIntFromByteStream(in);
        for (int i = 0; i < n; ++i) {
            try {
                T t = entityClass.newInstance();
                t.readFromByteStream(in);
                coll.add(t);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ByteUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Error while reading collection", ex);
            }
        }
    }

    static void addImageToByteStream(BufferedImage logo, ByteArrayOutputStream out) {
        try {
            ByteArrayOutputStream aux = new ByteArrayOutputStream();
            ImageIO.write(logo, "png", aux);
            byte[] bytes = aux.toByteArray();
            addIntToByteStream(bytes.length, out);
            out.write(bytes, 0, bytes.length);
            //ImageIO.write(logo, "png", out);
        } catch (IOException ex) {
            Logger.getLogger(ByteUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error while adding image to stream");
        }
    }

    static BufferedImage readImageFromByteStream(ByteArrayInputStream in) {
        try {
            int size = readIntFromByteStream(in);
            byte[] bytes = new byte[size];
            in.read(bytes, 0, size);
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException ex) {
            Logger.getLogger(ByteUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error while reading image from stream");
        }
    }
}