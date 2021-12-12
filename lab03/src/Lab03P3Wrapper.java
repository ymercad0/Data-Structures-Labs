public class Lab03P3Wrapper{
    public static void main(String[] args) {
        int[][]matrix = {

                {1, 4, 7, 12, 15, 1000},

                {2, 5, 19, 31, 32, 1001},

                {3, 8, 24, 33, 35, 1002},

                {40, 41, 42, 44, 45, 1003},

                {99, 100, 103, 106, 128, 1004}

        };

        int[]result = searchMatrix(matrix, 41);
        System.out.println(result[0] + "\n" + result[1]);
    }

    public static int[] searchMatrix(int[][] matrix, int target){
        int row = 0;
        int col = matrix[0].length-1; //starts checking from the upper right corner downwards

        while(row < matrix.length && col >= 0){
            if(matrix[row][col] == target)
                return new int[]{row, col};

            else if(matrix[row][col] > target)
                col--;

            else if(matrix[row][col] < target)
                row++;
        }
        return new int[] {-1, -1};
    }

}