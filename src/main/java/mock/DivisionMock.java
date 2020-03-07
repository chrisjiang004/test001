package mock;

public class DivisionMock {
    public Integer divid2(Integer a,Integer b,Integer c,Division division){
        Integer x = division.divided(a,b);
        if(x>10){
            return null;
        }
        else {
            return x/c ;
        }
    }

}
