
package beershowcase.utils;

/**
 * Class which represents intervals of the form [a,b], [a, inf), (-inf, b],
 * (-inf, inf), empty.
 * @author Grzegorz Łoś
 */
public class Range {
    private final Type type;
    public final FixedPointReal beg, end;

    public Range(FixedPointReal beg, FixedPointReal end) {
        this.type = Type.VAL_VAL;
        this.beg = beg;
        this.end = end;
    }

    public Range(FixedPointReal val, Type type) {
        this.type = type;
        switch (type) {
            case VAL_INF:
                this.beg = val;
                this.end = null;
                break;
            case INF_VAL:
                this.end = val;
                this.beg = null;
                break;
            default:
                throw new RuntimeException("Invalid argument for Range constructor");
            
        }
    }

    public Range(Type type) {
        if (type != Type.INF_INF && type != Type.EMPTY)
            throw new RuntimeException("Invalid argument for Range constructor");
        this.type = type;
        beg = null;
        end = null;
    }
    
    /**
     * Checks whether the given value lies withing this range.
     * @param value
     * @return 
     */
    public boolean inBounds(FixedPointReal value) {
        switch(type) {
            case VAL_VAL:
                return value.greaterEq(beg) && value.smallerEq(end);
            case VAL_INF:
                return value.greaterEq(beg);
            case INF_VAL:
                return value.smallerEq(end);
            case INF_INF:
                return true;
            case EMPTY:
                return false;
            default:
                throw new AssertionError(type.name());
        }
    }
    
    public enum Type {
        VAL_VAL, VAL_INF, INF_VAL, INF_INF, EMPTY
    }
}