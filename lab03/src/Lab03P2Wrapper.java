import java.util.HashSet;

public class Lab03P2Wrapper {

    public static void main(String[] args) {
        int arr[] =  {2, 1, 5, 2, 3, 3, 4};
        System.out.println(firstDuplicate(arr));
    }
    public static int firstDuplicate(int[] array) {
        HashSet<Integer> set = new HashSet<>();
        for(int num : array){
            if(set.contains(num))
                return num;
            set.add(num);
        }
        return -1; //Dummy Return
    }
}