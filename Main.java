import java.util.function.Function;
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

public class Main{

    public static void main(String[] args) {
        //Maybe<Integer> maybeInt = new Maybe<Integer>(false, (Integer) 1);
        
        final Maybe<Integer> maybeInt2 = Maybe.mReturn(null);

        final Maybe<Integer> maybeInt3 = Maybe.mBind(maybeInt2, x -> Maybe.mReturn(x + 1));
        
        System.out.println(maybeInt3.value);
    }
}

class Maybe <T> {
    public static <A> Maybe<A> mReturn (A a){
        return new Maybe<A> (a);
    }
    public static <A, B> Maybe<B> mBind (Maybe<A> ma, Function<A, Maybe<B>> f){
        final Maybe<B> newMb;
        if (ma.value == null){
            newMb = new Maybe<B>(null);
        } else {
            newMb = new Maybe<B>(f.apply(ma.value).value);
        }
        return newMb;
    }

    //final public boolean isNothing;
    final public T value;
    public Maybe (T itsValue){
        //isNothing = itIsNothing;
        value = itsValue;
    }
}


