import java.util.function.Function;
import java.util.Scanner;
import java.util.ArrayList;

public class Main{

    public static void main(String[] args) {
        final IO<?> mainDo = IO.mBind(IO.putStrLn("Reverse Polish Notation Calculator (enter quit, pop, push, divide):"),
            unUsed -> doBlock (new ArrayList<Maybe<Integer>>()));
    }

    public static Maybe<Integer> divide (Maybe<Integer> ma, Maybe<Integer> mb){
        //return Maybe.mReturn((b == 0) ? null : a / b);
        return Maybe.mBind(ma, a -> 
               Maybe.mBind(mb, b -> 
               Maybe.mReturn((b == 0) ? null : a / b)));
    } 

    public static IO<?> doBlock (ArrayList<Maybe<Integer>> stack){
        return IO.mBind(IO.getLine(), opInputed -> {
        final IO<?> output;
        switch (opInputed.toLowerCase()){
            case "quit":
            output = IO.mReturn(null);
            break;
            case "pop":
            final ArrayList<Maybe<Integer>> newStack = new ArrayList<Maybe<Integer>> (stack.subList(0, stack.size() - 1));
            output = IO.mBind(IO.printStack(newStack), unUsed ->
             doBlock(newStack));
            break;
            case "divide":
            final Function<ArrayList<Maybe<Integer>>,ArrayList<Maybe<Integer>>> stackDivAdder = stackIn -> {
                ArrayList<Maybe<Integer>> tempStack = stackIn;
                tempStack.add(divide(stack.get(stack.size() - 2),stack.get(stack.size() - 1)));
                return tempStack;
            };
            final ArrayList<Maybe<Integer>> modStack = stackDivAdder.apply(new ArrayList<Maybe<Integer>>(stack.subList(0, stack.size() - 2)));
            output = IO.mBind(IO.printStack(modStack), unused -> doBlock(modStack));
            break;
            case "push":
            
            output = IO.mBind(IO.getInt(), a -> {
                final Function<ArrayList<Maybe<Integer>>,ArrayList<Maybe<Integer>>> stackAdder = stackIn -> {
                    ArrayList<Maybe<Integer>> tempStack = stackIn;
                    tempStack.add(Maybe.mReturn(a));
                    return tempStack;
                };
                final ArrayList<Maybe<Integer>> addedStack = stackAdder.apply(stack);
                //return IO.mBind(IO.printStack(stackAdder.apply(stack)), unused -> doBlock(stackAdder.apply(stack)));}
                return IO.mBind(IO.printStack(addedStack), unused -> doBlock(addedStack));}
            );
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
    final static Scanner intScanner = new Scanner(System.in);

    public static IO<NullPointerException> putStrLn(String a) {
        System.out.println(a);
        return IO.mReturn(null);
    }
    public static IO<String> getLine(){
        return IO.mReturn(scanner.nextLine());
    }
    public static IO<Integer> getInt(){
        return IO.mReturn(intScanner.hasNextInt() ? intScanner.nextInt() : null);
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



