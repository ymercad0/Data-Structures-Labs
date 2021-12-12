public class Lab02P4Wrapper {

    /**
     * Returns the value of the function x^n RECURSIVELY using fast exponentiation,
     * where x is the base and n is the exponent.
     *
     * Non-recursive implementations will be given half the given credit,
     * WARNING: You CANNOT use the pow(double a, double b) method from the Math class.
     * @param x - base
     * @param n - exponent
     * @return value of x^n
     */
    public static void main(String[] args) {
        System.out.println(pow(5, -2));
    }
    public static double pow(double x, int n) {
        if(n < 0)
            return pow(1/x, -n);

        if(n == 0)
            return 1;

        if(n%2 == 0)
            return pow(x*x, n/2);

        if(n%2 != 0)
            return x*pow(x*x, (n-1)/2);

        return x*pow(x, n-1);
    }

}
