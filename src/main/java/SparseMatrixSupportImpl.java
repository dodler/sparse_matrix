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
        return null;
    }

    @Override
    public SparseMatrix multiply(SparseMatrix first, SparseMatrix second) {
        return first.mul(second);
    }
}
