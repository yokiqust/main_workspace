/**
 * Created by yokiqust on 16-12-30.
 */
public class Test {
    public Test(){
        System.out.println("hello world!");
    }
    String tt(){
        return "试试";
    }
    public static void main(String[] args){
        Test test=new Test();
        System.out.println(test.tt());
    }
}
