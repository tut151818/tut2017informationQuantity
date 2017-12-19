package s4.b151818; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;

/*
 interface FrequencerInterface {     // This interface provides the design for frequency counter.
 void setTarget(byte[]  target); // set the data to search.
 void setSpace(byte[]  space);  // set the data to be searched target from.
 int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
 //Otherwise, it return 0, when SPACE is not set or Space's length is zero
 //Otherwise, get the frequency of TAGET in SPACE
 int subByteFrequency(int start, int end);
 // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
 // For the incorrect value of START or END, the behavior is undefined.
 }
 */
/*
 //package s4.specification;
 public interface InformationEstimatorInterface{
 void setTarget(byte target[]); // set the data for computing the information quantities
 void setSpace(byte space[]); // set data for sample space to computer probability
 double estimation(); // It returns 0.0 when the target is not set or Target's length is zero;
 // It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
 // The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
 // Note that this happens only when the space is unreasonably large. We will encounter other problem anyway.
 // Otherwise, estimation of information quantity,
 }
*/

public class TestCase {
    public static void main(String[] args) {
        try {
            FrequencerInterface  myObject;
            int freq;
            System.out.println("checking s4.b151818.Frequencer");
            myObject = new s4.b151818.Frequencer();
            myObject.setSpace("Hi Ho Hi ".getBytes());
            //myObject.setTarget("H".getBytes());
            //freq = myObject.frequency();
            
            //It return -1, when TARGET is not set or TARGET's length is zero
            myObject.setTarget("".getBytes());//長さ0のTargetをセット
            freq = myObject.frequency();
            if(freq==-1){
                System.out.println("TARGET is not set or TARGET's length is zero");
            }else{
                System.out.println("TARGET is not set or TARGET's length is zero but -1 not return");
            }
            myObject.setTarget("H".getBytes());//HをTargetにセット
            
            //Otherwise, it return 0, when SPACE is not set or Space's length is zero
            myObject.setSpace("".getBytes());//長さ0のSpaceをセット
            freq = myObject.frequency();
            if(freq==0){
                System.out.println("SPACE is not set or Space's length is zero");
            }
            
            //Otherwise, get the frequency of TAGET in SPACE
            myObject.setSpace("Hi Ho Hi ".getBytes());
            myObject.setTarget("H".getBytes());
            freq = myObject.frequency();
            System.out.println("\"H\" in \"Hi Ho Hi \" appears "+freq+" times. ");
        }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
        }
        
        try {
            InformationEstimatorInterface myObject;
            double value;
            System.out.println("checking s4.b151818.InformationEstimator");
            myObject = new s4.b151818.InformationEstimator();
            myObject.setSpace("3210321001230123".getBytes());
            //myObject.setTarget("0".getBytes());
            //value = myObject.estimation();
            
            // It returns 0.0 when the target is not set or Target's length is zero;
            myObject.setTarget("".getBytes());//長さ0のTaregetをセット
            value = myObject.estimation();//valueの更新
            if(value==0.0){
                System.out.println("the target is not set or Target's length is zero");
            }
            else{
                System.out.println("the target is not set or Target's length is zero but 0 not return");
            }
            myObject.setTarget("0".getBytes());//Targerに0をセット
            
            // It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
            myObject.setSpace("".getBytes());//長さ0のspaceをセット
            value = myObject.estimation();//valueの更新
            if(value==Double.MAX_VALUE){
                System.out.println("the true value is infinite, or space is not set");
            }
            myObject.setSpace("3210321001230123".getBytes());
            
            // The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
            //myObject.setSpace("0123456789123456789".getBytes());//0の情報量が大きくなる様にSpaceをセット
            value = myObject.estimation();//valueの更新
            if(value>Double.MAX_VALUE){
                System.out.println("the true value is finete but larger than Double.MAX_VALUE");
            }
            
            // Otherwise, estimation of information quantity,
            myObject.setSpace("3210321001230123".getBytes());
            myObject.setTarget("0".getBytes());
            value = myObject.estimation();
            System.out.println(">0 "+value);
            myObject.setTarget("01".getBytes());
            value = myObject.estimation();
            System.out.println(">01 "+value);
            myObject.setTarget("0123".getBytes());
            value = myObject.estimation();
            System.out.println(">0123 "+value);
            myObject.setTarget("00".getBytes());
            value = myObject.estimation();
            System.out.println(">00 "+value);
        }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
        }
        
    }
}

