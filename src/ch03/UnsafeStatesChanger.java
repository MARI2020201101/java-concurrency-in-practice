package ch03;

import java.util.Arrays;

public class UnsafeStatesChanger {
    public static void main(String[] args) {
        UnsafeStates u = new UnsafeStates();
        String[] states = u.getStates();
        System.out.println(Arrays.toString(states));
        states[0] = "aaa";
        System.out.println(Arrays.toString(states));
        //배열이라서 그냥 바로 바꿀수있게 되버리는구나...private이라고 할지라도....ㅠㅠ
    }
}
