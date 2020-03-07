package mock;

public class Division {
    public Integer divided(Integer a,Integer b) {
        if (b == 0) {
            return null;
        } else if (a>100){
            return  1;
        }else if (a < b){
            return 0;
        }else {
            return a/b;
        }

    }


}
