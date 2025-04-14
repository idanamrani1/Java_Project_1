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

    public static Object[] expandLecturesArray(Object[] Array){
        Object[] newArray = new String[Array.length*2];
        for ( int i = 0 ; i < Array.length ; i++){
            newArray[i] = Array[i];
        }
        Array = newArray;
        return Array;
    }
}
