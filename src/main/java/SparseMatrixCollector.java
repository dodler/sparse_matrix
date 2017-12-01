import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by lyan on 30.11.17.
 */
public class SparseMatrixCollector implements Collector<Integer, SparseMatrix, SparseMatrix> {

    private int cols;
    private int rows;

    public SparseMatrixCollector(int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
    }

    @Override
    public Supplier<SparseMatrix> supplier() {
        return () -> new SparseMatrix(rows, cols);
    }

    @Override
    public BiConsumer<SparseMatrix, Integer> accumulator() {
        return (sparseMatrix, integer) -> sparseMatrix.addFromStream(integer);
    }

    @Override
    public BinaryOperator<SparseMatrix> combiner() {
        return null;
    }

    @Override
    public Function<SparseMatrix, SparseMatrix> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        HashSet<Characteristics> objects = new HashSet<>();
        objects.add(Characteristics.IDENTITY_FINISH);
        return objects;
    }
}
