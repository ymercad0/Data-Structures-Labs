public class Lab02P1Wrapper {

    /**
     * Exercise #1
     * Returns the quotient obtained by dividing two integers
     * by RECURSIVELY subtracting one integer from the other
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static void main(String[] args) {
        long result = div(18, 3);
        System.out.println(result);

    }
    public static long div(int dividend, int divisor) {
        long count = 0L;

        if(dividend < divisor)
            return count;

        if(dividend > 0)
            count++;

        return count + div(dividend-divisor, divisor);

    }

}