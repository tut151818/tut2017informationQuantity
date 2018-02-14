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
      /*if(mySpace[i]>mySpace[j]){
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
      }*/
      int si = suffixArray[i];
      int sj = suffixArray[j];
      int s =0;
      if(si > s) s = si;
      if(sj > s) s = sj;
      int n = mySpace.length -s;
      for(int k =0;k<n;k++){
        if(mySpace[si+k]>mySpace[sj+k]) return 1;
        if(mySpace[si+k]<mySpace[sj+k]) return -1;
      }
      if(si < sj) return 1;
      if(si > sj) return -1;
      return 0;
    }
    private int pivot(int[] a,int i,int j){
      int k=i+1;
      while(k<=j && suffixCompare(i,k)==0) k++;
      if(k>j) return -1;
      if(suffixCompare(i,k)==1) return i;
      return k;
    }
    private int partition(int[] a,int i,int j,int x){//i=0,j=9,x=0
      int l=i,r=j;//l=0,r=9
      // 検索が交差するまで繰り返します
      while(l<=r){
        // 軸要素以上のデータを探します
        while(l<=j && suffixCompare(x,l)==1)  l++;
        // 軸要素未満のデータを探します
        while(r>=i && suffixCompare(r,x)==1) r--;
        if(l>=r) break;
        //System.out.print("swap("+l+","+r+")");
        int t=a[l];
        a[l]=a[r];
        a[r]=t;
        if(x==r) x=l;
        if(x==l) x=r;
        l++; r--;
      }
      return l;
    }
    public void quickSort(int[] a,int i,int j){
      //System.out.print("i="+i+",j="+j);
      if(i==j) return;
      int p=pivot(a,i,j);
      //System.out.print(",p="+p);
      if(p!=-1){
        int k=partition(a,i,j,p);
        //System.out.println(",k="+k);
        quickSort(a,i,k-1);
        quickSort(a,k,j);
      }
    }

    public void setSpace(byte []space) {
      mySpace = space;
      //System.out.println("mySpace.length:"+mySpace.length);
      if(mySpace.length>0){
        spaceReady = true;
      }
      else{
        spaceReady = false;
        return;
      }
      suffixArray = new int [space.length];
      for(int i =0;i < space.length; i++){
        suffixArray[i] = i;
      }
      //辞書順ソート
      /*for(int i = 0;i < suffixArray.length;i++){
        for(int j = i+1;j<suffixArray.length;j++){
          //if(suffixCompare(suffixArray[i],suffixArray[j])==1){
          if(suffixCompare(i,j)==1){
            int a;
            a = suffixArray[i];
            suffixArray[i] = suffixArray[j];
            suffixArray[j] = a;
          }
        }
      }*/
      //クイックソート
      quickSort(suffixArray,0,suffixArray.length-1);
      //printSuffixArray();
    }
    private int targetCompare(int i, int start, int end) {
      // It is called from subBytesStarIndex, adn subBytesEndIndex.
      // "start" and "end" are same as in subByteStartIndex, and subByteEndIndex **
      // target_start_end is subBytes(start, end) of target **
      // comparing suffix_i and target_start_end by dictonary order with limitationof length; ***
      int s = suffixArray[i];
      for(int j = 0;j<myTarget.length;j++){
        if(s+j==mySpace.length){//Targetより短い場合
          return -1;
        }
        if(mySpace[s+j] > myTarget[j]){
           return 1;
        }
        else if(mySpace[s+j] < myTarget[j]){
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

      /* int spaceLength = mySpace.length;
       int count = 0;
       //end -start = Targetの長さ
       for(int offset = 0; offset< spaceLength - (end - start); offset++) {
         boolean abort = false;
         for(int i = 0; i< (end - start); i++) {
           if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
         }
         if(abort == false) { count++; }
      }*/
      int first = subByteStartIndex(start,end);
      int last1 = subByteEndIndex(start, end);

      //inspection code
      //for(int k=start;k<end;k++) { System.out.print(myTarget[k]); }
      //System.out.printf(": first=%d last1=%d\n", first, last1);
      if(first == mySpace.length){
        return 0;
      }
      return last1 - first;
    }
    public void setTarget(byte [] target) {
      myTarget = target;
      if(myTarget.length>0){
        targetReady = true;
      }
      else{
        targetReady = false;
      }
    }
    public int frequency() {
      //System.out.println("mySpace.length:"+mySpace.length);
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
            myObject.setTarget("Hi ".getBytes());
            //myObject.setSpace("32 05834132".getBytes());
            //myObject.setTarget("3".getBytes());
            freq = myObject.frequency();
            System.out.print("\"H\" in \"Hi Ho Hi Ho \" appears "+freq+" times. ");
            if(4 == freq) { System.out.println("OK"); } else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
        }
    }
}
