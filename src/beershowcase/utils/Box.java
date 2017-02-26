
package beershowcase.utils;

/**
 *
 * @author Grzegorz Łoś
 */
public class Box<T> {
   private T value;

    public Box() {
    }

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        if (isEmpty())
            throw new NullPointerException("Should not call getValue on an empty box");
        else
            return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public void unpack() {
        value = null;
    }
    
    
    public boolean isEmpty() {
        return value == null;
    }

    public boolean valueEquals(T other) {
        if (isEmpty())
            return other == null;
        else
            return value.equals(other);
    }
    
   @Override
    public String toString() {
        if (isEmpty())
            return "?";
        else
            return value.toString();
    }
}
