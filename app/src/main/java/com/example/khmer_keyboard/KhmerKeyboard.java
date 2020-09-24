package com.example.khmer_keyboard;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class KhmerKeyboard extends InputMethodService {
    public KhmerKeyboard() {
    }


    String[] secondLayoutChars = {"!","១","ៗ","២","៖","៣","៛","៤","៎","៥","៍","៦","័","៧","៏","៨","៝","៩","៊","០"
            ,"1","ឥ","2","ឦ","3","ឧ","4","ឩ","5","ឪ","6","ឫ","7","ឬ","8","ឭ","9","ឮ","៕","ឯ","ឱ","ឰ",
            "@",".","#",",","$",":","%","+","\\","-","&","_","*","/","(","|",")","="};



    String[] firstLayoutChars = {"ឈ","ឆ","ឺ","ឹ","ែ","េ","ឬ","ឬ","ទ","ត","ួ","យ","ូ","ុ","ី","ិ","ៅ","ោ","ភ","ផ",
            "ាំ","ា","ៃ","ស","ឌ","ដ","ធ","ថ","អ","ង","ះ","ហ","ញ","្","គ","ក","ឡ","ល","៉","់",
            "ឍ","ឋ","ឃ","ខ","ជ","ច","េះ","វ","ព","ប","ណ","ន","ំ","ម","ុះ","ុំ","ឿ","ៀ","ើ","ោះ"};

    String[] firstEmoji = {"😀","😃","😄","😁","😆","😅","🤣","😂","🙂","🙃","😉","😊","😇","","😍","🤩",
            "😘","😗","☺","😚","😙","😋","😛","😜","🤪","😝","🤑","🤗","🤭","🤫","🤔","🤐","🤨","😐","😑",
            "😶","😏","😒","🙄","😬","🤥","😌","😔","😪","🤤","😴","😷","🤒","🤕","🤢","🤮","🤧","🥵","🥶",
            "🥴","😵","🤯","🤠","🥳"};

    String[] secondEmoji = {"😎","🤓","🧐","😕","😟","🙁","☹","😮","😯","😲","😳","🥺","😦","😧","😨",
            "😰","😥","😢","😭","😱","😖","😣","😞","😓","😩","😫","🥱","😤","😡","🤬","😈","👿","💀",
            "☠","💩","🤡","👹","👺","👻","👽","👾","🤖","😺","😸","😹","😻","😼","😽","🙀","😿","😾",
            "🙈","🙉","🙊","💋","💌","💘","💝","💖"};

    String[] thirdEmoji = {"💗","💓","💞","💕","💟","❣","💔","❤","🧡","💛","💚","💙","💜","🤎","🖤",
            "🤍","💯","💢","💥","💫","💦","💨","🕳","💣","💬","👁️","‍🗨️","🗨","🗯","💭","💤","👋","🤚",
            "🖐","✋","🖖","👌","🤏","✌","🤞","🤟","🤘","🤙","👈","👉","👆","🖕","👇","☝","👍","👎",
            "✊","👊","🤛","🤜","👏","🙌","👐","🤲","🤝"};

    String[] fourthEmoji = {"🙏","✍","💅","🤳","💪","🦾","🦿","🦵","🦶","👂","🦻","👃","🧠","🦷","🦴",
            "👀","👁","👅","👄","👶","🧒","👦","👧","🧑","👱","👨","🧔","👨‍🦰‍","👨‍🦱","👨‍🦳","👨‍🦲","👩",
            "👩‍🦰","🧑‍🦰","👩‍🦱","🧑‍🦱","👩‍🦳","🧑‍🦳","👩‍🦲","👱‍♀️","👱‍♂️","🧓","👴","👵","🙍","🙍‍♂️",
            "🙍‍♀️","🙎","🙎‍♂️","🙎‍♀️","🙅","🙅‍♂️","🙅‍♀️","🙆","🙆‍♂️","🙆‍♀️","💁","💁‍♂️","💁‍♀️"};;

    //get all the children inside the viewgroup (last children of the tree)
    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }


    @Override
    public View onCreateInputView() {

        ViewGroup keyboardView = (ViewGroup)getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        ViewGroup charSets = (ViewGroup) keyboardView.findViewById(R.id.char_sets);

        final TextView key123 = keyboardView.findViewById(R.id.key123);
        final TextView keyKorKhor = keyboardView.findViewById(R.id.keyKorKhor);
        final TextView keyFullStop = keyboardView.findViewById(R.id.keyFullStop);
        final TextView keyQuestionMark = keyboardView.findViewById(R.id.keyQuestionMark);
        View keySpace = keyboardView.findViewById(R.id.keySpace);
        ImageView keyBackspace = keyboardView.findViewById(R.id.backspace);
        ImageView  keyReturn = keyboardView.findViewById(R.id.returnKey);
        ImageView keyEmoji = keyboardView.findViewById(R.id.emoji);
        ImageView keyLanguage = keyboardView.findViewById(R.id.language);
        ArrayList<View> allView = (ArrayList<View>) getAllChildren(charSets);

        final ArrayList<TextView> allTextView = new ArrayList<>(); // store only the TextView (the characters)
        final ArrayList<View> allFrameLayout = new ArrayList<>(); //store key of the keybaord

        for (int i = 0; i < allView.size(); i++) //get TextView from the layout
        {
            allTextView.add((TextView) allView.get(i));

        }

        for (int i = 0; i < allView.size(); i++) //get key from the layout
        {
            if (i % 2 != 0)
            {
                allFrameLayout.add((View) allView.get(i).getParent());
            }
        }


        //default layout
        for (int i = 0; i<allTextView.size(); i++)
        {
            allTextView.get(i).setText(firstLayoutChars[i]);
        }

        int k = 1;

        //get text from the key to the text area
        for (int i = 0; i < allFrameLayout.size(); i++)
        {

            final int j = k;
//            allFrameLayout.get(i).setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent event) {
//
//                    if (event.getAction() == MotionEvent.ACTION_DOWN)
//                    {
//                        InputConnection ic = getCurrentInputConnection();
//                        ic.commitText((CharSequence)allTextView.get(j).getText(), 1);
//                        System.out.println("pressed");
//                    }
//                    else if (event.getAction() == MotionEvent.ACTION_UP)
//                        System.out.println("released");
//                    // TODO Auto-generated method stub
//                    return false;
//                }
//            });
            allFrameLayout.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //on click listener
                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText((CharSequence)allTextView.get(j).getText(), 1);
                }
            });
            k += 2;
            allFrameLayout.get(i).setOnTouchListener(new OnSwipeTouchListener(){
                public boolean onSwipeTop() { // swipeUp listener
                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText((CharSequence)allTextView.get(j-1).getText(), 1);
                    return true;
                }
            });
        }
        //change layout to the second layout
        key123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i< allTextView.size(); i++)
                {
                    allTextView.get(i).setText(secondLayoutChars[i]);
                }
                key123.setVisibility(View.GONE);
                keyKorKhor.setVisibility(View.VISIBLE);
                keyFullStop.setVisibility(View.GONE);
                keyQuestionMark.setVisibility(View.VISIBLE);

            }
        });
        //change layout to the first layout
        keyKorKhor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i<allTextView.size(); i++)
                {
                    allTextView.get(i).setText(firstLayoutChars[i]);
                }
                keyKorKhor.setVisibility(View.GONE);
                key123.setVisibility(View.VISIBLE);
                keyQuestionMark.setVisibility(View.GONE);
                keyFullStop.setVisibility(View.VISIBLE);

            }
        });

        //submit key event (Enter | Return | Done)
        keyReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        });

        keyBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.deleteSurroundingText(1,0);
            }
        });

        keySpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.commitText(" ", 1);
            }
        });

        //switch keyboard
        keyLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
            }
        });

        //change to emoji layout
        keyEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        keyFullStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.commitText("។",1);
            }
        });

        keyQuestionMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.commitText("?",1);
            }
        });



        return keyboardView;
    }
}
