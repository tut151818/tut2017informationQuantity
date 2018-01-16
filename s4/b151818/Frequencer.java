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
 */


public class Frequencer implements FrequencerInterface{
    // Code to Test, *warning: This code  contains intentional problem*
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady =false;
    boolean spaceReady = false;

    int[] suffixArray;

    private void printSuffixArray() {
      if(spaceReady) {
        for(int i=0; i< mySpace.length; i++) {
          int s = suffixArray[i];
          for(int j=s;j<mySpace.length;j++) {
            System.out.write(mySpace[j]);
          }
          System.out.write('\n');
        }
      }
    }

    private int suffixCompare(int i,int j){
      if(mySpace[i]>mySpace[j]){
        return 1;
      }
      else if(mySpace[i]<mySpace[j]){
        return -1;
      }
      else{
        if((i==mySpace.length-1)&&(j==mySpace.length-1)){
          return 0;
        }
        else if(i==mySpace.length-1){
            return -1;
        }
        else if(j==mySpace.length-1){
          return 1;
        }
        return(suffixCompare(i+1,j+1));
      }
    }
    public void setSpace(byte []space) {
      mySpace = space;
      if(mySpace.length>0) spaceReady = true;
      suffixArray = new int [space.length];
      for(int i =0;i < space.length; i++){
        suffixArray[i] = i;
      }
      //辞書順ソート
      for(int i = 0;i < suffixArray.length;i++){
        for(int j = i+1;j<suffixArray.length;j++){
          if(suffixCompare(suffixArray[i],suffixArray[j])==1){
            int a;
            a = suffixArray[i];
            suffixArray[i] = suffixArray[j];
            suffixArray[j] = a;
          }
        }
      }
      printSuffixArray();
    }
    private int targetCompare(int i, int start, int end) {
      // It is called from subBytesStarIndex, adn subBytesEndIndex.
      // "start" and "end" are same as in subByteStartIndex, and subByteEndIndex **
      // target_start_end is subBytes(start, end) of target **
      // comparing suffix_i and target_start_end by dictonary order with limitationof length; ***
      int si = suffixArray[i];
      for(int j=start;j<end;j++){
        if(si+j==mySpace.length){//Targetより短い場合
          return -1;
        }
        if(mySpace[si+j] > myTarget[j]){
           return 1;
        }
        else if(mySpace[si+j] < myTarget[j]){
           return -1;
        }
      }
      return 0;
    }
    private int subByteStartIndex(int start, int end){
      // It returns the index of the first suffix which is equal or greater than subBytes;
      // not implemented yet;
      for(int i = 0;i < suffixArray.length;i++){
        if(targetCompare(i,start,end)==0){
          return i;
        }
      }
      // For "Ho", it will return 5 for "Hi Ho Hi Ho".
      // For "Ho ", it will return 6 for "Hi Ho Hi Ho".
      return suffixArray.length;
    }
    private int subByteEndIndex(int start, int end) {
      // It returns the next index of the first suffix which is greater than subBytes;
      // not implemented yet
      for(int i = 0;i < suffixArray.length;i++){
        if(targetCompare(i,start,end)==1){
          return i;
        }
      }
      // For "Ho", it will return 7 for "Hi Ho Hi Ho".
      // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
      return suffixArray.length;
    }

    public int subByteFrequency(int start, int end) {
       //This method could be defined as follows though it is slow.

       int spaceLength = mySpace.length;
       int count = 0;
       //end -start = Targetの長さ
       for(int offset = 0; offset< spaceLength - (end - start); offset++) {
         boolean abort = false;
         for(int i = 0; i< (end - start); i++) {
           if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
         }
         if(abort == false) { count++; }
      }
      int first = subByteStartIndex(start,end);
      int last1 = subByteEndIndex(start, end);

      //inspection code
      //for(int k=start;k<end;k++) { System.out.print(myTarget[k]); }
      //System.out.printf(": first=%d last1=%d\n", first, last1);

      return last1 - first;
    }
    public void setTarget(byte [] target) {
      myTarget = target;
      if(myTarget.length>0){
        targetReady = true;
      }
    }
    public int frequency() {
        if(targetReady == false) return -1;
        if(spaceReady == false) return 0;
        return subByteFrequency(0,myTarget.length);
    }

    public static void main(String[] args) {
        Frequencer myObject;
        int freq;
        try {
            System.out.println("checking my Frequencer");
            myObject = new Frequencer();
            myObject.setSpace("Hi Ho Hi Ho".getBytes());
            myObject.setTarget("H".getBytes());
            freq = myObject.frequency();
            System.out.print("\"H\" in \"Hi Ho Hi Ho\" appears "+freq+" times. ");
            if(4 == freq) { System.out.println("OK"); } else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
        }
    }
}
