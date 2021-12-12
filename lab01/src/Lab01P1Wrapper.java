public class Lab01P1Wrapper {

    private static class Arithmetic{
        /*TODO ADD THE FOLLOWING:
         * PRIVATE FIELDS,
         * CONSTRUCTOR,
         * GETTERS,
         * SETTERS,
         * MEMBER METHODS
         */

        //Data Fields
        int a;
        int b;

        //Default constructor
        public Arithmetic(int num1, int num2){
            this.a = num1;
            this.b = num2;
        }

        //Getters
        public int getA(){
            return a;
        }

        public int getB(){
            return b;
        }

        //Setters
        public void setA(int num){
            this.a = num;
        }

        public void setB(int num){
            this.b = num;
        }

        //Member methods
        public int add(){
            return a+b;
        }

        public int subtract(){
            return a-b;
        }

        public int multiply(){
            return a*b;
        }

        public double divide(){
            return a/b;
        }
    }

    public static class Calculator extends Arithmetic{

        public Calculator(int num1, int num2){
            super(num1, num2);
        }

    }

}
