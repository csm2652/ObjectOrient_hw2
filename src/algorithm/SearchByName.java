package algorithm;

public class SearchByName {
    private static final char INITIAL_SOUND_BEGIN_UNICODE = 12593; // 초성 유니코드 시작 값
    private static final char INITIAL_SOUND_LAST_UNICODE = 12622;  // 초성 유니코드 마지막 값
    private static final char HANGUL_BEGIN_UNICODE = 44032;        // 한글 유니코드 시작 값
    private static final char HANGUL_LAST_UNICODE = 55203;         // 한글 유니코드 마지막 값
    private static final char NUMBER_BEGIN_UNICODE = 48;           // 숫자 유니코드 시작 값
    private static final char NUMBER_LAST_UNICODE = 57;            // 숫자 유니코드 마지막 값
    private static final char ENGLISH_ROWER_BEGIN_UNICODE = 65;    // 영문(소문자) 유니코드 시작 값
    private static final char ENGLISH_ROWER_LAST_UNICODE = 90;     // 영문(소문자) 유니코드 마지막 값
    private static final char ENGLISH_UPPER_BEGIN_UNICODE = 97;    // 영문(대문자) 유니코드 시작 값
    private static final char ENGLISH_UPPER_LAST_UNICODE = 122;    // 영문(대문자) 유니코드 마지막 값
    private static final char HANGUL_BASE_UNIT = 588;              // 자음 마다 가지는 글자수
    // 초성
    private static final char[] INITIAL_SOUND =
            {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ'
                    , 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    /**
     * 입력받은 문자가 초성인지 체크
     */
    private static boolean isInitialSound(char c){
        for(int i=0; i<INITIAL_SOUND.length; i++){
            if(INITIAL_SOUND[i] == c){
                return true;
            }
        }
        return false;
    }

    /**
     * 입력받은 문자의 자음을 추출
     */
    private static char getInitialSound(char c){
        int hanBegin = (c - HANGUL_BEGIN_UNICODE);
        int index = hanBegin / HANGUL_BASE_UNIT;
        return INITIAL_SOUND[index];
    }

    /**
     * 입력받은 문자가 한글인지 체크
     */
    private static boolean isHangul(char c){
        return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
    }

    /**
     * 숫자, 영문(대소문자), 한글, 초성 등의 유니코드를 체크
     */
    private static boolean checkUnicode(char c){
        if ((   (c >= NUMBER_BEGIN_UNICODE && c <= NUMBER_LAST_UNICODE)
                || (c >= ENGLISH_UPPER_BEGIN_UNICODE && c <= ENGLISH_UPPER_LAST_UNICODE)
                || (c >= ENGLISH_ROWER_BEGIN_UNICODE && c <= ENGLISH_ROWER_LAST_UNICODE)
                || (c >= HANGUL_BEGIN_UNICODE && c <= HANGUL_LAST_UNICODE)
                || (c >= INITIAL_SOUND_BEGIN_UNICODE && c <= INITIAL_SOUND_LAST_UNICODE))
                ){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 검색어와 검색 대상 값을 입력받아서 일치여부 확인
     */
    private static boolean matchString(String keyword, String value){
        int t = 0;
        int seof = value.length() - keyword.length();
        int slen = keyword.length();
        // 검색어가 검색대상값보다 더 길거나, 검색어가 두개 이하일때 false 리턴
        if(seof < 0 || keyword.length() < 1){
            return false;
        }

        for(int i = 0;i <= seof;i++){
            t = 0;
            while(t < slen){
                // 숫자, 초성, 한글, 영문(대소문자)를 제외한 다른 값은 false 리턴
                if(!checkUnicode(keyword.charAt(t))){
                    return false;
                }

                // 검색어가 초성이이고 한글이면 초성끼리 비교
                if(isInitialSound(keyword.charAt(t)) && isHangul(value.charAt(i+t))){
                    // 각각의 초성이 같은지 비교
                    if(getInitialSound(value.charAt(i+t)) == keyword.charAt(t)){
                        t++;
                    }else{
                        break;
                    }
                } else {
                    // 초성이 아닐경우 비교
                    if(value.charAt(i+t) == keyword.charAt(t)){
                        t++;
                    }else{
                        break;
                    }
                }
            }
            // 검색어의 길이만큼 모두 일치하면 true를 리턴
            if(t == slen){
                return true;
            }
        }
        return false; // 일치하지 않으면 false를 리턴
    }

    public static boolean search(String all, String part) {
        if (matchString(part, all)) {
            return  true;
        }
        return false;
    }
}
