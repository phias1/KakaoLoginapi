package ad.yun.com.a0604kakaologin;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;

//안드로이드에서 카카오 로그인을 사용합니다

public class MainActivity extends AppCompatActivity {
    //안드로이드 키 해시를 로그로 출력하는 메소드
    //키해시 : 안드로이드 앱 마다 별도로 생성되는 ID 같은 개념
    //한 번만 호출하면 된다.
    private void getHashKey() {
                try{
                    PackageInfo info = getPackageManager().getPackageInfo("ad.yun.com.a0604kakaologin", PackageManager.GET_SIGNATURES);
                    for(Signature signature : info.signatures){
                        //SHA는 암호화 알고리즘의 종류이고 MessageDigest는 암호화 관렬 모듈이다.
                        //이 단어의 약자는 md
                        MessageDigest md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        //키 해시 출력
                        Log.e("키 해시", Base64.encodeToString(md.digest(),Base64.DEFAULT));
                    }
                }catch (Exception e){
                    Log.e("해시 가져오기 실패", e.getMessage());
                }

    }

    class SessionCallback implements ISessionCallback{
        @Override
        public void onSessionOpened() {
            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {

                }

                @Override
                public void onNotSignedUp() {

                }

                //로그인에 성공한 경우
                @Override
                public void onSuccess(UserProfile result) {
                    Log.e("사용자 정보", result.toString());
                    Log.e("사용자 이름", result.getNickname());
                }
                //로그인에 실패한 경우
                @Override
                public void onFailure(ErrorResult result){
                    finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("세션 연결 실패", exception.getMessage());
        }
    }

    SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //키해시를 출력하기 위한 메소드 호출
        //getHashKey();

        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
    }
}
