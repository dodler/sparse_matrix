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
        int size = collect.size();

        int cols = (int) Math.sqrt(size);

        if (cols * cols != size) {
            throw new IllegalArgumentException("Supported only for quad matrices now");
        }

        SparseMatrix result = new SparseMatrix(cols, cols);

        int k = 0;
        int j = 0;
        for (int i = 0; i < size; i++) {
            result.setValueAt(k, j, collect.get(i));
            if (j == cols-1) {
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
