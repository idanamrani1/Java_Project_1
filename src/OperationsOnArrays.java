// עוד לא ממשתי את הפונקציות הנ"ל- רק העתקתי אותן

public class OperationsOnArrays {
    public static boolean isFullArray(Object[] Array){
        for ( int i = 0 ; i < Array.length ; i++){
            if (Array[i] == null){
                return false;
            }
        }
        return true;
    }
}
