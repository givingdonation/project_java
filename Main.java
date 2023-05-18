
/*interface Monad {
    static <T, V extends Monad> V mReturn(T t);
    static <T, V extends Monad, Va extends Monad> V mBind(Va ma, mOper<Monad, T> aTmb);
}*/

/*class Maybe<T> implements Monad {
    public static Maybe<T> mReturn (T t) {
        
    }
    public <A, B> Maybe<B> mBind (Maybe<A> ma, mOper<A, Maybe<B>> f){
        if (ma == )
        return 
    }
}*/

//interface mOper <T, V> {
//    public V f (T t);
//}
public class Main{

    public static void main(String[] args) {
        Mayba<Integer> maybeInt = new Mayba<Integer>(false, (Integer) 1);
        
        Mayba<Integer> maybeInt2 = Mayba.mReturn(1);
        System.out.println(maybeInt2.isNothing == maybeInt.isNothing);
    }
}

class Mayba <T> {
    public static <A> Mayba<A> mReturn (A a){
        return new Mayba<A> (false, a);
    }

    public boolean isNothing;
    public T value;
    public Mayba (boolean itIsNothing, T itsValue){
        isNothing = itIsNothing;
        value = itsValue;
    }
}


