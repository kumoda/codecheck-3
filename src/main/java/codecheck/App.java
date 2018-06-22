package codecheck;
import java.math.BigInteger;
public class App {
    static final String[] subCommand = {"encode","decode","align"};
    static final BigInteger NINE = new BigInteger("9");

    public static void main(String[] args){

        String output = "";
        if(args[0].equals(subCommand[0])){
            output = encode(args[1]);
        } else if (args[0].equals(subCommand[1])){
            output = decode(args[1]);
        } else if (args[0].equals(subCommand[2])){
            output = align(args[1]);
        }

        System.out.println(output);
    }

    private static String encode(String str){

        BigInteger num = new BigInteger(str);
        BigInteger remainder = new BigInteger("0");
        String output = "";

        while(num.compareTo(NINE) >= 0){
            remainder = num.remainder(NINE);
            num = num.divide(NINE);
            output += getChar(remainder.intValue());
        }
        output += getChar(num.intValue());

        return output.equals("") ? getChar(num.intValue())
                : new StringBuilder(output).reverse().toString();
    }

    private static String decode(String str){

        StringBuilder sb = new StringBuilder(str).reverse();

        String[] chrArr = sb.toString().split("");
        BigInteger encodedStr = new BigInteger("0");

        String chrNum = "";
        for(int i=0; i<chrArr.length;i++){
            chrNum = Integer.toString(getNumber(chrArr[i]));
            encodedStr = encodedStr.add(new BigInteger(chrNum).multiply(NINE.pow(i)));
        }

        return encodedStr.toString();
    }

    private static String align(String str){

        final String COMP_STR = "H";

        String[] chrArr = str.split("");
        String totalStr = "";
        for(int i=0; i<chrArr.length; i++){
            if(getNumber(chrArr[i]) > getNumber(COMP_STR)) {
                for(int j=0;j<chrArr.length + 1; j++){
                    totalStr += COMP_STR;
                }
                break;
            } else if(getNumber(chrArr[i]) < getNumber(COMP_STR) || i == chrArr.length - 1){
                for(int j=0;j<chrArr.length; j++){
                    totalStr += COMP_STR;
                }
                break;
            }

        }

       BigInteger totalInt = new BigInteger(decode(totalStr));
       BigInteger inputInt = new BigInteger(decode(str));
       BigInteger remainderInt = totalInt.subtract(inputInt);
       String remainderStr = encode(remainderInt.toString());

        return str + " + " + remainderStr + " = " + totalStr;

    }

    private static String getChar(int at){
        return String.valueOf("ABCDEFGHI".charAt(at));
    }

    private static int getNumber(String chr){
        return "ABCDEFGHI".indexOf(chr);
    }
}
