package com.pangff.horizontallinearlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HorizontalLinearLayout extends LinearLayout{

  int widthMeasureSpec;
  int heightMeasureSpec;
  float leftMinWidth = 150;
  float rightMinWidth = 150;
  View textLeft;
  View textRight;
  LayoutParams lp;
  LayoutParams rp;
  
  public HorizontalLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalLinearLayout);
    leftMinWidth = a.getDimension(R.styleable.HorizontalLinearLayout_leftMinWidth, 150);
    rightMinWidth = a.getDimension(R.styleable.HorizontalLinearLayout_rightMinWidth, 150);
    this.setOrientation(HORIZONTAL);
    //注意回首
    if(a!=null){
        a.recycle();
    }
  }
  

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      this.widthMeasureSpec = widthMeasureSpec;
      this.heightMeasureSpec = heightMeasureSpec;
  }
  
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if(this.getChildCount()==2){
      if(textLeft==null){
        textLeft = this.getChildAt(0);
        textRight = this.getChildAt(1);
        lp = (LayoutParams) textLeft.getLayoutParams();
        rp = (LayoutParams) textRight.getLayoutParams();
      }
      float totalWidth = this.getMeasuredWidth()-lp.leftMargin-rp.leftMargin-lp.rightMargin-rp.rightMargin-this.getPaddingLeft()-this.getPaddingRight();
      
      float leftMeasureWidth = textLeft.getMeasuredWidth();
      float rightMeasureWidth = textRight.getMeasuredWidth();
      
      textLeft.measure(0, 0);
      float textLeftWidth = textLeft.getMeasuredWidth();
      textRight.measure(0, 0);
      float textRightWidth = textRight.getMeasuredWidth();
      
      //this.measure(widthMeasureSpec,heightMeasureSpec);
      //textLeft.measure(widthMeasureSpec, heightMeasureSpec);
      this.measure(widthMeasureSpec,heightMeasureSpec);
      //textRight.measure(widthMeasureSpec, heightMeasureSpec);
        if(leftMeasureWidth>textLeftWidth){//左侧分配的宽度大于了内容宽度
          /** 让左侧宽度为内容宽度 **/
         lp = (LayoutParams) textLeft.getLayoutParams();
         lp.width = (int) Math.ceil(textLeftWidth);
         textLeft.setLayoutParams(lp);
         /** 让右侧占据剩余空间 **/
         rp = (LayoutParams) textRight.getLayoutParams();
         rp.width=(int) (totalWidth-(int) Math.ceil(textLeftWidth));
         textRight.setLayoutParams(rp);
         this.measure(widthMeasureSpec,heightMeasureSpec);
        }else if(rightMeasureWidth>textRightWidth){//右侧分配宽度大于了内容宽度
          /** 让左侧占据剩余空间 **/
          lp.width = (int) (totalWidth-(int) Math.ceil(textRightWidth));
          textLeft.setLayoutParams(lp);
          
          /** 让右侧宽度为内容宽度 **/
          rp.width = (int) Math.ceil(textRightWidth);
          textRight.setLayoutParams(rp);
          this.measure(widthMeasureSpec,heightMeasureSpec);
        }else{
           if(leftMeasureWidth<leftMinWidth){
             lp = (LayoutParams) textLeft.getLayoutParams();
             lp.width = (int) Math.ceil(leftMinWidth);
             textLeft.setLayoutParams(lp);
             
             rp.width =(int) (totalWidth-(int) Math.ceil(leftMinWidth));
             textRight.setLayoutParams(rp);
             this.measure(widthMeasureSpec,heightMeasureSpec);
           }
           
           if(rightMeasureWidth<rightMinWidth){
             lp = (LayoutParams) textLeft.getLayoutParams();
             lp.width =(int) (totalWidth-(int) Math.ceil(rightMinWidth));
             textLeft.setLayoutParams(lp);
             
             rp.width = (int) Math.ceil(rightMinWidth);
             textRight.setLayoutParams(rp);
             this.measure(widthMeasureSpec,heightMeasureSpec);
           }
        }
      }
    super.onLayout(changed, l, t, r, b);
  }

}
