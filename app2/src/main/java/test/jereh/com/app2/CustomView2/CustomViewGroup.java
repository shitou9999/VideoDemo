package test.jereh.com.app2.CustomView2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/6/26.
 */
public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount=getChildCount();
        if(childCount==0){//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(widthSize,heightSize);
        }else{
            //如果宽高都是包裹内容
           if(widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
               //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
               int width=getMaxChildWidth();
               int heigth=getTotleHeight();
               setMeasuredDimension(width,heigth);
           }
        }
    }
    //获取子View中宽度最大的值
    private int getMaxChildWidth() {
        int childCount=getChildCount();
        int maxWidth=0;
        for(int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            if(childView.getMeasuredWidth()>maxWidth){
                maxWidth=childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }
    //将所有子View的高度相加
    private int getTotleHeight(){
        int childCount=getChildCount();
        int heigth=0;
        for(int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            heigth+=childView.getMeasuredHeight();//子view的高度相加的高度
        }
        return heigth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count=getChildCount();
        //记录当前的高度位置
        int curHeigth=t;
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            int heigth=child.getMeasuredHeight();
            int width=child.getMeasuredWidth();
            child.layout(l,curHeigth,l+width,curHeigth+heigth);//摆放子View，参数分别是子View矩形区域的左、上、右、下边
            curHeigth+=heigth;
        }
    }
}