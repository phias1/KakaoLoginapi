package ad.yun.com.a0604kakaologin;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    //호우
    //인스턴스 변수 선언
    //volatile : 멀티 스레드 프로그래밍에서 한 번에 수행하도록 해주는 예약어
    //멀티스레드 프로그래밍에서 long을 사용할 때 앞에 붙여서 사용한다.
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    //안드로이드 스튜디오에서는 상속을 받고 메소드를 재정의할 때 에러 메시지를 출력하는 경우가 있는데 이 경우는
    //상위 클래스의 메소드를 반드시 호출해 주어야 한다.
    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }
    public static GlobalApplication getGlobalApplicationContext(){
        return obj;
    }
    //currentActivity의 getter 와 setter
    public static Activity getCurrentActivity(){
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity){
        GlobalApplication.currentActivity = currentActivity;
    }
}
