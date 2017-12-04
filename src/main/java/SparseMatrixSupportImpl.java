import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lyan on 29.11.17.
 */
public class SparseMatrixSupportImpl implements SparseMatrixSupport<SparseMatrix> {



    @Override
    public Stream<Integer> toStream(SparseMatrix matrix) {
        return matrix.stream();
    }

    @Override
    public SparseMatrix fromStream(Stream<Integer> stream) {
        List<Integer> collect = stream.collect(Collectors.toList());
        int rows = collect.get(0);
        int cols = collect.get(1);

        SparseMatrix result = new SparseMatrix(rows, cols);

        int k = 0;
        int j = 0;
        for (int i = 0; i < collect.size()-2; i++) {
            result.setValueAt(k, j, collect.get(2+i));
            if (j == cols - 1) {
                k++;
                j = 0;
            } else {
                j++;
            }
        }
        return result;
    }

    @Override
    public SparseMatrix multiply(SparseMatrix first, SparseMatrix second) {
        return first.mul(second);
    }
}
