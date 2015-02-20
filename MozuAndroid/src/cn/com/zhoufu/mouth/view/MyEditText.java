package cn.com.zhoufu.mouth.view;

import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class MyEditText extends EditText{

    public MyEditText(Context context) {
            super(context);
    }
    
    private OnFinishComposingListener mFinishComposingListener;
    
    public void setOnFinishComposingListener(OnFinishComposingListener listener){
            this.mFinishComposingListener =listener;
    }
    
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            return new MyInputConnection(super.onCreateInputConnection(outAttrs), false);
    }
    
    public class MyInputConnection extends InputConnectionWrapper {

            public MyInputConnection(InputConnection target, boolean mutable) {
                    super(target, mutable);
            }

            @Override
            public boolean finishComposingText() {
                    boolean finishComposing = super.finishComposingText();
                    if(mFinishComposingListener != null){
                            mFinishComposingListener.finishComposing();
                    }
                    return finishComposing;
            }
    }
    
    public interface OnFinishComposingListener{
            public void finishComposing();
    }
}