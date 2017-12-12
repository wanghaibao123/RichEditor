package richeditor.zhulong.com.richeditorlibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by whb on 2017/11/28.
 */

public class RichEditorControlBar extends RelativeLayout implements View.OnClickListener {
    /**
     * 字体加粗
     */
    private ImageButton action_bold;
    /**
     * 字体斜着
     */
    private ImageButton action_italic;
    /**
     * 中线
     */
    private ImageButton action_strikethrough;
    /**
     * 北京变回
     */
    private ImageButton action_blockquote;
    /**
     * 一号字体头部
     */
    private ImageButton action_heading1;
    /**
     * 二号字体头部
     */
    private ImageButton action_heading2;
    /**
     * 三号字体头部
     */
    private ImageButton action_heading3;
    /**
     * 四号字体头部
     */
    private ImageButton action_heading4;
    /**
     * 撤销
     */
    private ImageButton action_undo;
    /**
     * 复原
     */
    private ImageButton action_redo;
    /**
     * 显示更多
     */
    private ImageButton action_add;
    /**
     * 图片
     */
    private TextView action_pic;
    /**
     * 链接
     */
    private TextView action_link;
    /**
     * 分割线
     */
    private TextView action_split;
    /**
     * 更多操作
     */
    private LinearLayout ll_morecontrol;
    /**
     * 富文本编辑器
     */
    private RichEditorWebView richEditorWebView;
    /**
     * 回调接口
     */
    private richEditorCallBack mCallBack;

    /**
     * 弹出输入框
     */
    AlertDialog alertDialog;


    public RichEditorControlBar(Context context) {
        super(context);
        init();
    }

    public RichEditorControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditorControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        initView();
        initEvent();
    }

    public void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_controlbar, null);
        addView(view);
        action_bold = view.findViewById(R.id.action_bold);
        action_italic = view.findViewById(R.id.action_italic);
        action_strikethrough = view.findViewById(R.id.action_strikethrough);
        action_blockquote = view.findViewById(R.id.action_blockquote);
        action_heading1 = view.findViewById(R.id.action_heading1);
        action_heading2 = view.findViewById(R.id.action_heading2);
        action_heading3 = view.findViewById(R.id.action_heading3);
        action_heading4 = view.findViewById(R.id.action_heading4);
        action_undo = view.findViewById(R.id.action_undo);
        action_redo = view.findViewById(R.id.action_redo);
        action_add = view.findViewById(R.id.action_add);
        action_pic = view.findViewById(R.id.action_pic);
        action_link = view.findViewById(R.id.action_link);
        action_split = view.findViewById(R.id.action_split);
        ll_morecontrol = view.findViewById(R.id.ll_morecontrol);

    }

    public void initEvent() {
        action_bold.setOnClickListener(this);
        action_italic.setOnClickListener(this);
        action_strikethrough.setOnClickListener(this);
        action_blockquote.setOnClickListener(this);
        action_heading1.setOnClickListener(this);
        action_heading2.setOnClickListener(this);
        action_heading3.setOnClickListener(this);
        action_heading4.setOnClickListener(this);
        action_undo.setOnClickListener(this);
        action_redo.setOnClickListener(this);
        action_add.setOnClickListener(this);
        action_pic.setOnClickListener(this);
        action_link.setOnClickListener(this);
        action_split.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.action_bold) {
            if (action_bold.getTag() == null) {//保存状态
                action_bold.setTag(true);
                action_bold.setImageResource(R.mipmap.bold_l);
            } else {
                action_bold.setTag(null);
                action_bold.setImageResource(R.mipmap.bold_d);
            }
            richEditorWebView.setBold();
        } else if (i == R.id.action_italic) {
            if (action_italic.getTag() == null) {
                action_italic.setImageResource(R.mipmap.italic_l);
                action_italic.setTag(true);
            } else {
                action_italic.setImageResource(R.mipmap.italic_d);
                action_italic.setTag(null);
            }
            richEditorWebView.setItalic();
        } else if (i == R.id.action_strikethrough) {
            if (action_strikethrough.getTag() == null) {
                action_strikethrough.setImageResource(R.mipmap.strikethrough_l);
                action_strikethrough.setTag(true);
            } else {
                action_strikethrough.setImageResource(R.mipmap.strikethrough_d);
                action_strikethrough.setTag(null);
            }
            richEditorWebView.setStrikeThrough();
        } else if (i == R.id.action_blockquote) {
            if (action_blockquote.getTag() == null) {
                changeTextColorAndTag(R.id.action_blockquote);
                action_blockquote.setTag(true);
            } else {
                action_blockquote.setImageResource(R.mipmap.blockquote_d);
                action_blockquote.setTag(null);
            }
            richEditorWebView.setBlockquote(action_blockquote.getTag() != null, false, false, false);
        } else if (i == R.id.action_heading1) {
            onClickTextChange(action_heading1, R.id.action_heading1, R.mipmap.h1_d, 1);
        } else if (i == R.id.action_heading2) {
            onClickTextChange(action_heading2, R.id.action_heading2, R.mipmap.h2_d, 2);
        } else if (i == R.id.action_heading3) {
            onClickTextChange(action_heading3, R.id.action_heading3, R.mipmap.h3_d, 3);
        } else if (i == R.id.action_heading4) {
            onClickTextChange(action_heading4, R.id.action_heading4, R.mipmap.h4_d, 4);
        } else if (i == R.id.action_redo) {
            richEditorWebView.redo();
        } else if (i == R.id.action_add) {
            if (ll_morecontrol.getVisibility() == VISIBLE) {
                ll_morecontrol.setVisibility(GONE);
            } else {
                ll_morecontrol.setVisibility(VISIBLE);
            }
        } else if (i == R.id.action_undo) {
            richEditorWebView.undo();
        } else if (i == R.id.action_link) {
            showinsertLinkDilag();
        } else if (i == R.id.action_split) {
            richEditorWebView.insertHr();
        } else if (i == R.id.action_pic) {
            if (mCallBack != null) {
                mCallBack.onClickImg();
            }
        }
    }

    /**
     * 不能共存选项 改变所有的字体选择
     *
     * @param id
     */
    public void changeTextColorAndTag(int id) {
        action_blockquote.setImageResource(id == R.id.action_blockquote ? R.mipmap.blockquote_l : R.mipmap.blockquote_d);
        action_heading1.setImageResource(id == R.id.action_heading1 ? R.mipmap.h1_l : R.mipmap.h1_d);
        action_heading2.setImageResource(id == R.id.action_heading2 ? R.mipmap.h2_l : R.mipmap.h2_d);
        action_heading3.setImageResource(id == R.id.action_heading3 ? R.mipmap.h3_l : R.mipmap.h3_d);
        action_heading4.setImageResource(id == R.id.action_heading4 ? R.mipmap.h4_l : R.mipmap.h4_d);

        action_blockquote.setTag(id == R.id.action_blockquote ? true : null);
        action_heading1.setTag(id == R.id.action_heading1 ? true : null);
        action_heading2.setTag(id == R.id.action_heading2 ? true : null);
        action_heading3.setTag(id == R.id.action_heading3 ? true : null);
        action_heading4.setTag(id == R.id.action_heading4 ? true : null);
    }

    /**
     * 点击不同文本选择
     *
     * @param imageView
     * @param buttonid
     * @param imgId
     */
    public void onClickTextChange(ImageView imageView, int buttonid, int imgId, int headtype) {
        if (imageView.getTag() == null) {
            changeTextColorAndTag(buttonid);
        } else {
            imageView.setImageResource(imgId);
            imageView.setTag(null);
        }
        richEditorWebView.setHeading(headtype, imageView.getTag() != null, false, false, false);
    }


    public void setRichEditorWebView(RichEditorWebView richEditorWebView) {
        this.richEditorWebView = richEditorWebView;
        /**
         * 监听获取点击出文本的标签类型
         */
        richEditorWebView.setOnDecorationChangeListener(new RichEditorWebView.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditorWebView.Type> types) {

                if (types.contains(RichEditorWebView.Type.BOLD)) {
                    action_bold.setImageResource(R.mipmap.bold_l);
                    action_bold.setTag(true);
                } else {
                    action_bold.setImageResource(R.mipmap.bold_d);
                    action_bold.setTag(null);
                }

                if (types.contains(RichEditorWebView.Type.ITALIC)) {
                    action_italic.setImageResource(R.mipmap.italic_l);
                    action_italic.setTag(true);
                } else {
                    action_italic.setImageResource(R.mipmap.italic_d);
                    action_italic.setTag(null);
                }

                if (types.contains(RichEditorWebView.Type.STRIKETHROUGH)) {
                    action_strikethrough.setImageResource(R.mipmap.strikethrough_l);
                    action_strikethrough.setTag(true);
                } else {
                    action_strikethrough.setImageResource(R.mipmap.strikethrough_d);
                    action_strikethrough.setTag(null);
                }

                //块引用
                if (types.contains(RichEditorWebView.Type.BLOCKQUOTE)) {
                    changeTextColorAndTag(R.id.action_blockquote);
                    action_blockquote.setTag(true);
                } else {
                    action_blockquote.setImageResource(R.mipmap.blockquote_d);
                    action_blockquote.setTag(null);
                }


                if (types.contains(RichEditorWebView.Type.H1)) {
                    changeTextColorAndTag(R.id.action_heading1);
                } else {
                    action_heading1.setImageResource(R.mipmap.h1_d);
                    action_heading1.setTag(null);
                }


                if (types.contains(RichEditorWebView.Type.H2)) {
                    changeTextColorAndTag(R.id.action_heading2);
                } else {
                    action_heading2.setImageResource(R.mipmap.h2_d);
                    action_heading2.setTag(null);
                }

                if (types.contains(RichEditorWebView.Type.H3)) {
                    changeTextColorAndTag(R.id.action_heading3);
                } else {
                    action_heading3.setImageResource(R.mipmap.h3_d);
                    action_heading3.setTag(null);
                }

                if (types.contains(RichEditorWebView.Type.H4)) {
                    changeTextColorAndTag(R.id.action_heading4);
                } else {
                    action_heading4.setImageResource(R.mipmap.h4_d);
                    action_heading4.setTag(null);
                }
            }
        });
    }

    /**
     * 插入图片
     */
    public void insertImg(String picturePath) {
        //richEditorWebView.insertImage("file://"+picturePath, "图片");
        richEditorWebView.insertImage("http://localhost"+picturePath, "图片");
    }

    /**
     * 批量插入文件
     *
     * @param list
     */
    public void insertImgList(List<String> list) {
        for (String picturepath : list) {
            insertImg(picturepath);
        }
    }

    public void setCallBack(richEditorCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * 接口
     */
    public interface richEditorCallBack {
        void onClickImg();
    }


    public void showinsertLinkDilag() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_insertlink, null);
        final EditText et_link_address = view.findViewById(R.id.et_link_address);
        final EditText et_link_title = view.findViewById(R.id.et_link_title);

        final AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("插入超链接")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null).create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positionButton = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String linkAddress = et_link_address.getText().toString();
                        String linkTitle = et_link_title.getText().toString();

                        if (linkAddress.endsWith("http://") || TextUtils.isEmpty(linkAddress)) {
                            Toast.makeText(getContext(), "请输入超链接地址", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(linkTitle)) {
                            Toast.makeText(getContext(), "请输入超链接标题", Toast.LENGTH_SHORT).show();
                        } else {
                            richEditorWebView.insertLink(linkAddress, linkTitle);
                            mDialog.dismiss();
                        }

                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
            }
        });

        mDialog.show();

    }

}
