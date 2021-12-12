import java.util.HashSet;


public class Lab03P1Wrapper {

    public static int[] twoSum(int[] array, int targetSum) {
        HashSet<Integer> set = new HashSet<>();
        int comp = 0; //complement, target-num gives you the other pair you should have
        for(int num : array){
            comp = targetSum-num;

            if(set.contains(comp)) //found the other complement
                return new int[] {Math.min(num, comp), Math.max(num, comp)}; //junit test cases pass without O(nlogn) sorting

            set.add(num);
        }
        return new int[]{};
    }
}