import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by lyan on 29.11.17.
 */
public class SparseMatrix{
    private SparseVector[] rows;

    public int getRows() {
        return rowsNum;
    }

    public int getCols() {
        return cols;
    }

    private int rowsNum;
    private int cols;


    public SparseMatrix(int rows, int cols){
        this.rows = new SparseVector[rows];
        rowsNum = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            this.rows[i] = new SparseVector();
            this.rows[i].setCols(cols);
        }
    }

    public void setValueAt(int row, int col, int value) {
        if (row < 0 || row > rows.length) throw new IllegalArgumentException("Bad index " + row);

        rows[row].set(col, value);
    }

    public Integer getValueAt(int row, int col){
        if (row < 0 || row > rows.length) throw new IllegalArgumentException("Bad index " + row);

        return rows[row].get(col);
    }

    public SparseMatrix mul(SparseMatrix second) {

        assert cols == second.rowsNum;

        SparseMatrix result = new SparseMatrix(rows.length, second.rows[0].size);

        for (int i = 0; i < this.rows.length; i++) {
            for (int j = 0; j < second.rows[0].size; j++) {
                int v = 0;

                Set<Integer> values = this.rows[i].values.keySet();

                for (Integer k : values) {
                    v += getValueAt(i, k) * second.getValueAt(k, j);
                }
                result.setValueAt(i, j, v);
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof SparseMatrix)) {
            return false;
        }

        SparseMatrix mat = (SparseMatrix) obj;

        if (mat.rows.length != mat.rows.length) {
            return false;
        }

        if (mat.rows.length == 0) {
            return true;
        }

        if (mat.rows[0].size != mat.rows[0].size) {
            return false;
        }

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].size; j++) {
                Integer src = getValueAt(i, j);
                Integer dst = mat.getValueAt(i, j);

                if (src == null || dst == null) {
                    continue;
                }

                if (!src.equals(dst)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Stream<Integer> stream() {
        Stream<Integer> result = Stream.empty();

        for (SparseVector row : rows) {
            result = Stream.concat(result, row.stream());
        }

        return result;
    }

    private int i = 0,j = 0;

    void addFromStream(int value) {
        setValueAt(i, j, value);
        if (j == cols-1) {
            i++;
            j = 0;
        } else {
//            setValueAt(i, j, value);
            j++;
        }

//        if (i >= rowsNum) {
//            return;
//        }

    }
}
