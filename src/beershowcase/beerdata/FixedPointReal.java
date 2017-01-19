
package beershowcase.beerdata;

/**
 *
 * @author Grzegorz Łoś
 */
public class FixedPointReal implements Comparable<FixedPointReal> {
    public static final int MAX_POINT_POS = 10;
    
    public final long units;
    public final int pointPos;

    public FixedPointReal(String repr) {
        String[] words = repr.split("\\.");
        if (words.length > 2 || words.length == 0) {
            throw new NumberFormatException("Invalid FixedPointReal representation: " + repr);
        } else if (words.length == 1) {
            units = Long.parseLong(repr);
            pointPos = 0;
        } else {
            long a = Long.parseLong(words[0]);
            long b = Long.parseLong(words[1]);
            pointPos = words[1].length();
            if (pointPos > MAX_POINT_POS)
                throw new NumberFormatException("Invalid FixedPointReal representation: " + repr);
            units = a * pow10(pointPos) + b;
        }
    }

    public FixedPointReal(long units, int pointPos) {
        this.units = units;
        this.pointPos = pointPos;
        if (pointPos > MAX_POINT_POS)
            throw new NumberFormatException("Invalid FixedPointReal representation: " + units + "." + pointPos);
    }

    public FixedPointReal(double d, int pointPos) {
        this.pointPos = pointPos;
        this.units = Math.round(d * pow10(pointPos));
    }
    

    public FixedPointReal(FixedPointReal origin) {
        units = origin.units;
        pointPos = origin.pointPos;
    }
    
    public FixedPointReal toAnotherPointPos(int newPointPos) {
        if (pointPos <= newPointPos) {
            long pow = pow10(newPointPos - pointPos);
            return new FixedPointReal(units * pow, newPointPos);
        } else {
            long pow = pow10(pointPos - newPointPos);
            long tmp = units / (pow/10);
            long maybeAddOne = tmp % 10 >= 5 ? 1 : 0;
            return new FixedPointReal(units / pow + maybeAddOne, newPointPos);
        }
    }
    
    private static long pow10(int n) {
        long res = 1;
        for (int i = 0; i < n; ++i)
            res *= 10;
        return res;
    }
    
    public double getDoubleValue() {
        return units / Math.pow(10.0, pointPos);
    }
    
    public boolean smallerThan(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        return pair.left.units < pair.right.units;
    }
    
    public boolean smallerEq(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        return pair.left.units <= pair.right.units;
    }
    
    public boolean greaterThan(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        return pair.left.units > pair.right.units;
    }
    
    public boolean greaterEq(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        return pair.left.units >= pair.right.units;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SamePointPosPair pair = new SamePointPosPair(this, (FixedPointReal) obj);
        return pair.left.units == pair.right.units;
    }
    
    @Override
    public int hashCode() {
        return new Long(toAnotherPointPos(MAX_POINT_POS).units).hashCode();
    }

    @Override
    public int compareTo(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        if (pair.left.units == pair.right.units)
            return 0;
        else
            return pair.left.units < pair.right.units ? -1 : 1;
    }
    
    @Override
    public String toString() {
        if (pointPos == 0) {
            return "" + units;
        }
        else {
            long pow = pow10(pointPos);
            StringBuilder sb = new StringBuilder();
            sb.append(units/pow);
            sb.append(".");
            String fracPart = "" + (units % pow);
            sb.append(fracPart);
            for (int i = fracPart.length(); i < pointPos; ++i)
                sb.append('0');
            return sb.toString();
        }
    }

    public FixedPointReal plus(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        return new FixedPointReal(pair.left.units + pair.right.units, pair.left.pointPos);
    }

    public FixedPointReal minus(FixedPointReal other) {
        SamePointPosPair pair = new SamePointPosPair(this, other);
        return new FixedPointReal(pair.left.units - pair.right.units, pair.left.pointPos);
    }
    
    private static class SamePointPosPair {
        final FixedPointReal left, right;

        public SamePointPosPair(FixedPointReal left, FixedPointReal right) {
            if (left.pointPos == right.pointPos) {
                this.left = left;
                this.right = right;
            }
            else if (left.pointPos < right.pointPos) {
                this.left = left.toAnotherPointPos(right.pointPos);
                this.right = right;
            } else {
                this.left = left;
                this.right = right.toAnotherPointPos(left.pointPos);
            }            
        }
    }

}
