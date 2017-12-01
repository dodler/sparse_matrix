import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyan on 29.11.17.
 */
public class SparseMatrixTest {

    public static final SparseMatrixSupportImpl SPARSE_MATRIX_SUPPORT = new SparseMatrixSupportImpl();
    public static final SparseMatrix TEST_A = new SparseMatrix(4, 3);
    public static final SparseMatrix TEST_B = new SparseMatrix(3, 2);
    public static final SparseMatrix TEST_C = new SparseMatrix(4, 2);

    @Test
    public void mul() throws Exception {
        SparseMatrix sparseMatrix = new SparseMatrix(4, 3);
        SparseMatrix sparseMatrix1 = new SparseMatrix(3, 1);
        SparseMatrix mul = sparseMatrix.mul(sparseMatrix1);
    }

    @Before
    public void before(){
        TEST_A.setValueAt(0,0,2);
        TEST_A.setValueAt(0,1,3);
        TEST_A.setValueAt(0,2,4);
        TEST_A.setValueAt(1,0,4);
        TEST_A.setValueAt(1,1,5);
        TEST_A.setValueAt(1,2,6);
        TEST_A.setValueAt(2,0,4);
        TEST_A.setValueAt(2,1,-1);
        TEST_A.setValueAt(2,2,22);
        TEST_A.setValueAt(3,0,0);
        TEST_A.setValueAt(3,1,9);
        TEST_A.setValueAt(3,2,3);


        TEST_B.setValueAt(0,0,2);
        TEST_B.setValueAt(0,1,6);
        TEST_B.setValueAt(1,0,3);
        TEST_B.setValueAt(1,1,77);
        TEST_B.setValueAt(2,0,4);
        TEST_B.setValueAt(2,1,3);


        TEST_C.setValueAt(0, 0, 29);
        TEST_C.setValueAt(0, 1, 255);
        TEST_C.setValueAt(1, 0, 47);
        TEST_C.setValueAt(1, 1, 427);
        TEST_C.setValueAt(2, 0, 93);
        TEST_C.setValueAt(2, 1, 13);
        TEST_C.setValueAt(3, 0, 39);
        TEST_C.setValueAt(3, 1, 702);

    }

    /**
     * C = A·B =
     2	3	4
     4	5	6
     4	-1	22
     0	9	3
     ·
     2	6
     3	77
     4	3
     =
     29	255
     47	427
     93	13
     39	702

     * @throws Exception
     */

    @Test
    public void mulConcrete() throws Exception{


        SparseMatrix mul = TEST_A.mul(TEST_B);

        Stream<Integer> integerStream = SPARSE_MATRIX_SUPPORT.toStream(mul);


        String collect = integerStream.map(e -> e.toString()).collect(Collectors.joining(" "));
        String collect1 = TEST_C.stream().map(e -> e.toString()).collect(Collectors.joining(" "));

        System.out.println(collect);
        System.out.println(collect1);

        assertTrue(mul.equals(TEST_C));
    }

    @Test
    public void test100x100(){
        SparseMatrix a = getRandomSparse(100, 100);
        SparseMatrix b = getRandomSparse(100, 100);

        double v = avgTime(() -> a.mul(b));

        System.out.println("time:" + v);
    }

    @Test
    public void test1000x1000(){
        SparseMatrix a = getRandomSparse(1000, 1000);
        SparseMatrix b = getRandomSparse(1000, 1000);

        double v = avgTime(() -> a.mul(b));

        System.out.println("time:" + v);
    }

    @Test
    public void test10000x10000() {
        SparseMatrix a = getRandomSparse(10000, 10000);
        SparseMatrix b = getRandomSparse(10000, 10000);

        double v = avgTime(() -> a.mul(b));

        System.out.println("avg time:" + v);
    }

    @Test
    public void test1x07_1x07(){
        SparseMatrix a = getRandomSparse(1_000_000, 1_000_000);
        SparseMatrix b = getRandomSparse(1_000_000, 1_000_000);

        System.out.println("generation done");

        double v = avgTime(() -> a.mul(b));
        System.out.println("avt time:" + v);
    }

    @Test
    public void testStream(){
        SparseMatrixCollector collector = new SparseMatrixCollector(TEST_A.getRows(), TEST_A.getCols());

        Stream<Integer> stream = TEST_A.stream();

        SparseMatrix collect = stream.collect(collector);

        String collect1 = collect.stream().map(e -> e.toString()).collect(Collectors.joining(" "));
        String collect2 = TEST_A.stream().map(e -> e.toString()).collect(Collectors.joining(" "));


        System.out.println("got:" + collect1);
        System.out.println("should be:" + collect2);

        assertTrue(collect.equals(TEST_A));
    }

    @Test
    public void testCollect(){
        List<Integer> collect = TEST_A.stream().collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    public static final double SPARSE_FACTOR = 0.001;
    public static final int ITERS = 1;
    public static final int MAX_VAL = 20;


    public static final double avgTime(Runnable runnable) {
        long start = 0;
        long sumTime = 0;
        for (int i = 0; i < ITERS; i++) {

            if (i % 10 == 0) {
                System.out.println("iteration " + i);
            }

            start = System.currentTimeMillis();
            runnable.run();
            sumTime += (System.currentTimeMillis() - start);
        }

        return 1.0 * sumTime / ITERS;
    }


    /**
     * takes at least 10 elements
     * @param rows
     * @param cols
     * @return
     */
    public static final SparseMatrix getRandomSparse(int rows, int cols){
        SparseMatrix sparseMatrix = new SparseMatrix(rows, cols);
        int rows2fill = (int) Math.round(rows * SPARSE_FACTOR);
        int cols2fill = (int) Math.round(cols  * SPARSE_FACTOR);
        int elmNum = rows2fill * cols2fill;

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < elmNum; i++) {
            int row = abs(random.nextInt() % rows);
            int col = abs(random.nextInt() % cols);

            sparseMatrix.setValueAt(row, col, random.nextInt() % MAX_VAL);
        }
        return sparseMatrix;
    }


}