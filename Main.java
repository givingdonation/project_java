import java.util.function.Function;
import java.util.Scanner;
import java.util.ArrayList;

public class Main{

    public static void main(String[] args) {
        final IO<?> mainDo = IO.mBind(IO.putStrLn("Reverse Polish Notation Calculator (enter quit, pop, push, divide):"),
            unUsed -> doBlock (new ArrayList<Maybe<Integer>>()));
    }

    public Maybe<Integer> divide (int a, int b){
        return Maybe.mReturn((b == 0) ? null : a / b);
    } 

    public static IO<?> doBlock (ArrayList<Maybe<Integer>> stack){
        return IO.mBind(IO.getLine(), opInputed -> {
        final IO<?> output;
        switch (opInputed.toLowerCase()){
            case "quit":
            output = IO.mReturn(null);
            break;
            case "pop":
            final ArrayList<Maybe<Integer>> new_stack = new ArrayList<Maybe<Integer>> (stack.subList(0, stack.size() - 1));
            output = IO.mBind(IO.printStack(new_stack), unUsed ->
             doBlock(new_stack));
            break;
            case "divide":
            output = IO.mReturn(null);
            break;
            case "push":
            final IO<Integer> inputNum = IO.getInt();
            output = IO.mBind(inputNum, a ->
                IO.mBind(IO.printStack(stack), unused -> {
                ArrayList<Maybe<Integer>> add_stack = stack;
                stack.add(Maybe.mReturn(a));
                return doBlock(add_stack);
            }
            ));
            break;
            default:
            output = IO.mBind(IO.putStrLn("Invalid, try again."), unUsed -> 
                doBlock(stack));
            break;
        }
        return output;   
        });
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

    final private T value;
    public IO (T itsValue){
        value = itsValue;
    }

    final static Scanner scanner = new Scanner(System.in);

    public static IO<NullPointerException> putStrLn(String a) {
        System.out.println(a);
        return IO.mReturn(null);
    }
    public static IO<String> getLine(){
        return IO.mReturn(scanner.nextLine());
    }
    public static IO<Integer> getInt(){
        return IO.mReturn(scanner.nextInt());
    }
    public static IO<NullPointerException> printStack (ArrayList<Maybe<Integer>> a) {
        String text = ">";
        for (int i = 0; i < a.size();i++){
            text += " " + (a.get(i).value.toString());
        }
        System.out.println(text);
        return IO.mReturn(null);
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



