package mock;

public class Division2 {
    public Integer divid(Integer a,Integer b) {
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
//调用本来其他方法，测试mock的部分mock方法
    public Integer divid2(Integer a,Integer b,Integer c){
        Integer x = divid(a,b);
        if(x>10){
            return null;
        }
        else {
            return x/c ;
        }
    }
}
