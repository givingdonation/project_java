import java.util.function.Function;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        
        final Maybe<Integer> maybeInt2 = Maybe.mReturn(1);

        final Maybe<Integer> maybeInt3 = Maybe.mBind(maybeInt2, x -> Maybe.mReturn(x + 1));
        
        final IO<?> mainDo = 
            IO.mBind(IO.getLine(), a -> 
            IO.mBind(IO.putStrLn(a), b ->
            IO.mBind(IO.putStrLn(maybeInt3.value.toString()), c ->
                     IO.putStrLn("hi")
            )));
    }

}





class IO <T>{
    public static <A> IO<A> mReturn (A a){
        return new IO<A>(a);
    }
    public static <A, B> IO<B> mBind(IO<A> ma, Function<A, IO<B>> f){
        final IO<B> newMb;
        newMb = new IO<B>(f.apply(ma.value).value);
        return newMb;
    }

    final public T value;
    public IO (T itsValue){
        value = itsValue;
    }




    public static IO<NullPointerException> putStrLn(String a) {
        System.out.println(a);
        return IO.mReturn(null);
    }
    public static IO<String> getLine(){
        final Scanner myObj = new Scanner(System.in);
        final String text = myObj.nextLine();
        myObj.close();
        return IO.mReturn(text);
    }
}


class Maybe <T>{
    public static <A> Maybe<A> mReturn(A a){
        return new Maybe<A>(a);
    }
    public static <A, B> Maybe<B> mBind(Maybe<A> ma, Function<A, Maybe<B>> f){
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
    public Maybe(T itsValue){
        value = itsValue;
    }
}



