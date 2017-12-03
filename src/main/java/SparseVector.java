import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by lyan on 29.11.17.
 */
public class SparseVector {

    Map<Integer, Integer> values;

    int size;

    public SparseVector() {
        values = new HashMap<>();
    }

    public void setCols(int cols) {
        this.size = cols;
    }

    public void set(int col, int value) {
        if (col > size || col < 0) throw new IllegalArgumentException("Bad index " + col);

        if (value == 0) {
            return;
        } else {
            values.put(col, value);
        }
    }

    public Integer get(int col) {
        if (values.containsKey(col)) {
            return values.get(col);
        }

        return 0;
    }


    public Stream<Integer> stream() {
        List<Integer> streamValues = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            streamValues.add(get(i));
        }
        return streamValues.stream();
    }

}
