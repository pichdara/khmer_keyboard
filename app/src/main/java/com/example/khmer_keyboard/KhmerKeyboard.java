package com.example.khmer_keyboard;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class KhmerKeyboard extends InputMethodService {

    int emoLayoutNum = 0;
    StringBuffer inputString = new StringBuffer();



    public KhmerKeyboard() {
    }

    String[][] emojis = {
            {"😀","😃","😄","😁","😆","😅","😂","🤣","😭","😗",
                    "😙","😚","😘","🥰","😍","🤩","🥳","🤗","🙃",
                    "🙂","☺️","😊","😏","😌","😉","🤭","😶","😐","😑",
                    "😔","😋","😛","😝","😜","🤪","🤔","🤨","🧐","🙄","😒","😤",
                    "😠","😡","🤬","☹️","🙁","😕","😟","🥺","😬","😳","🤐","🤫","😰",
                    "😨","😧","😦","😮","😯","😲"
            },
            {
                "😱","🤯","😢","😥","😓","😞","😖","😣","😩","😫","😵","🤤","🥱","😴","😪","🌛","🌜","🌚","🌝","🌞","🤢","🤮",
            "🤧","😷","🤒","🤕","🥴","🥵","🥶","😈","👿","😇","🤠","🤑","😎","🤓","🤥","🤡","👻","💩","👽","🤖","🎃","👹",
            "☠️","😺","😸","😹","😻","😼","😽","🙀","😿","😾","❤️","🧡","💛","💚","💙","💜"
            },
            {
                "🤎","🖤","🤍","♥️","💘","💝","💖","💗","💓","💞","💕","💌","💟","❣️","💔","💋",
                    "🔥","💫","⭐️","🌟","✨","⚡️","💥","💯","💢","💨","💦","💤","🕳","👥","👤",
                    "🗣","🧠","🩸","🦠","🦷","🦴","💀","👀","👁","👄","👅","👃","👂","🦻","🦶","🦵",
                    "🦿","🦾","💪","👍","👎","👏","🙌","👐","🤲","🤝","🤜","🤛","✊"
            },
            {
                "👊","🖐","✋","🤚","👋","🤏","👌","✌️","🤘","🤟","🤙","🤞","🖕","🖖","☝️","👆","👇","👉","👈","✍️","🤳","🙏","💅",
                    "🛑","🚧","🚥","🚦","🚨","⛽️","🛢️","🧭","⚓️","🏍️","🛵","🚲","🦼","🦽","🛴","🛹","🚇","🚏","🚙","🚗","🚐","🚚",
                    "🚛","🚜","🏎️","🚒","🚑","🚓","🚕","🛺","🚌","🚈","🚝","🚅","🚄","🚂","🚘"
            },
            {
                "🚔","🚍","🚉","🚊","🚞","🚎","🚋","🚃","🚖","🚆","🚢","🛳️","🛥️","🚤","⛴️","⛵️",
                    "🛶","🛫","✈️","🛩️","🚀","🛸","🚁","🚡","🚠","🚟","🛬","🎢","🎡","🎠","🎪",
                    "🗼","🗽","🗿","💈","💒","⛪️","🛕","🕋","🕌","🕍","⛩️","⛲️","🏛️","🏩",
                    "🏯","🏰","🏗️","🏢","🏭","🏬","🏪","🏟️","🏡","🏠","🏚️","🏥","🏤","🏣","🏨"
            },
            {
                "🏫","🏦","🏘️","⛺️","🏕️","🌅","🌄","🌇","🌁","🏙️","🌆","🏜️","🏞️","🗻","🌋","⛰️"
                    ,"🏔️","🌉","🌌","🌃","🏖️","⛱️","🏝️","🛤️","🛣️","🗺️","🗾","🌐","💺","🧳","🎉"
                    ,"🎊","🎈","🎂","🎀","🎁","🎇","🎆","🧨","🎄","🎋","🎍","🎑","🎎","🎏","🎐","🪔"
                    ,"🧧","🎃","🎗","🥇","🥈","🥉","🏅","🎖","🏆","📢","🥅","⚽️","⚾️"
            },
            {
                "🥎","🏀","🏐","🏈","🏉","🎾","🏸","🥍","🏏","🏑","🏒","🥌","🛷","🎿","⛸","🩰","⛳️","🎯","🏹","🥏",
                    "🪁","🎣","🤿","🩱","🎽","🥋","🥊","🎱","🏓","🎳","♟","🪀","🧩","🎮","🕹","👾","🔫","🎲","🎰","🎴"
                    ,"🀄️","🃏","🎩","📷","📸","🖼","🎨","🖌","🖍","🧵","🧶","🎼","🎵","🎶","🎹","🎷","🎺","🎸","🪕","🎻"
            },
            {
                    "🥁","🎤","🎧","🎚","🎛","🎙","📻","📺","📼","📹","📽","🎥","🎞","🎬","🎭","🎫","🎟","❤️",
                    "🧡","💛","💚","💙","💜","🤎","🖤","🤍","🔴","🟠","🟡","🟢","🔵","🟣","🟤","⚫️",
                    "⚪️","🟥","🟧","🟨","🟩","🟦","🟪","🟫","⬛️","♈️","♉️","♊️","♋️",
                    "⭕️","❌","🚫","🚳","🚭","🚯","🚱","🚷","📵","🔞","🔕","🔇","🅰️"
            },
            {
              "🆎","🅱️","🆑","🅾️","🆘","🛑","⛔️","📛","♨️","🉐","㊙️","㊗️","🈴","🈵","🈹","🈲"
                    ,"🉑","🈶","🈚️","🈸","🈺","🈷️","🔶","🔸","✴️","🆚","🎦","📶","🔁","🔂","🔀","▶️",
                    "⏩","⏭️","⏯️","◀️","⏪","⏮️","🔼","⏫","🔽","⏬","⏸️","⏹️","⏺️","⏏️","🔆","🔅","📲","📳","📴"
                    ,"🔈","🔉","🔊","🎵","🎶","🎼","☢️","☣️","⚠️"
            },
            {
                    "🚸","⚜️","🔱","〽️","🔰","✳️","❇️","♻️","💱","💲","💹","🈯️","❎","✅","✔️",
                    "☑️","⬆️","↗️","➡️","↘️","⬇️","↙️","⬅️","↖️","↕️","↔️","↩️",
                    "↪️","⤴️","⤵️","🔃","🔄","🔙","🔛","🔝","🔚","🔜","🆕","🆓","🆙","🆗","🆒","🆖","🈁","🈂️",
                    "🈳","🔣","🔤","🔠","🔡","🔢","#️⃣","*️⃣","0️⃣","1️⃣","2️⃣","3️⃣","4️⃣","5️⃣","6️⃣"
            },
            {
                    "7️⃣","8️⃣","9️⃣","🔟","🏧","⚕️","💠","🔷","🔹","🌐","Ⓜ️","ℹ️","🅿️","🚾","🗣️",
                    "👤","👥","👣","🐾","🚻","🚹","♿️","🚼","🚮","🚰","🛂","🛃","🛄","🛅","👁️‍🗨️","💟","🛐",
                    "🕉️","☸️","☮️","☯️","✝️","✝️","☦️","✡️","🔯","🕎","♾️","🆔","©️",
                    "®️","™️","✖️","➕","➖","➗","➰","➿","〰️","♥️","♦️","♣️","♠️","🔳",
                    "◼️"
            },
            {
                    "◾️","▪️","🔲","◻️","◽️","▫️","💭","🗯️","💬","🗨️","🔘","📱","📲","☎️",
                    "📞","📟","📠","🔌","🔋","🖲️","💽","💾","💿","📀","🖥️","💻","⌨️","🖨️","🖱️","🏧",
                    "💸","💵","💴","💶","💷","💳","💰","🧾","🧮","⚖️","🛒","🛍️","🕯️","💡","🔦","🏮",
                    "🧱","🚪","🪑","🛏️","🛋️","🚿","🛁","🚽","🧻","🧸","🧷","🧹","🧴","🧽"
            },
            {
                    "🧼","🪒","🧺","🧦","🧤","🧣","👖","👕","🎽","👚","👔","👗","👘","🥻","🩱","👙","🩳",
                    "🩲","🧥","🥼","👛","⛑️","🎓","🎩","👒","🧢","👑","🎒","👝","👛","👜","💼","🧳","☂️"
                    ,"🌂","💍","💎","💄","👠","👟","👞","🥿","👡","👢","🥾","👓","🕶️","🦯","🥽","⚗️","🧫","🧪",
                    "🌡️","🧬","💉","💊","🩹","🩺","🔬","🔭"
            },


            {
                    "📡","🛰️","🧯","🪓","🧲","🧰","🗜️","🔩","🔧","🔨","⚒️","🛠️","⛏️","⚙️","🔗","⛓️",
                    "📎","🖇️","📏","📐","✂️","📌","📍","🗑️","🖌️","🖍️","🖊️","🖋️","✒️","✏️","📝","📒","📔",
                    "📕","📓","📗","📘","📙","📚","📖","🔖","🗒️","📄","📃","📋","📇","📑","🗃️","🗄️","🗂️","📂","📁","📰",
                    "🗞️","📊","📈","📉","📦","📫","📪"

            },
            {
                    "📬","📭","📮","✉️","📧","📩","📨","💌","📤","📥","🗳️","🏷️","⌛️","⏳","🕰️","🕛","🕧","🕐","🕜",
                    "🕑","🕝","🕒","🕞","🕓","🕟","🕔","🕠","🕕","🕡","🕖","🕢","🕗","🕣","🕘","🕤","🕙","🕥","🕚","🕦","⏱️",
                    "⌚️","⏲️","⏰","🗓️","📅","🛎️","🛎️","🔔","📯","📢","📣","🔍","🔎","🔮","🧿","📿","🏺","⚱️","⚰️","🚬"
            },
    };


    String[] charAll = {"1","១","2","២","3","៣","១","៤","5","៥","6","៦","7","៧","8","៨","9","៩","១","០","ឥ","ឦ","ឲ","ឪ",
            "ឈ","ឆ","ឺ","ឹ","ែ","េ","ឬ","ឬ","ទ","ត","ួ","យ","ូ","ុ","ី","ិ","ៅ","ោ","ភ","ផ","ឿ","ៀ","ឧ","ឪ",
            "ាំ","ា","ៃ","ស","ឌ","ដ","ធ","ថ","អ","ង","ះ","ហ","ញ","្","គ","ក","ឡ","ល","ើ","ោះ","៉","់","ឭ","ឮ",
            "ឍ","ឋ","ឃ","ខ","ជ","ច","េះ","វ","ព","ប","ណ","ន","ំ","ម","ុះ","ុំ","។","៕","?","៊"};


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

        System.out.println("started");

        ViewGroup keyboardView = (ViewGroup)getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        ViewGroup charSets = (ViewGroup) keyboardView.findViewById(R.id.char_sets);

//        final TextView key123 = keyboardView.findViewById(R.id.key123);
//        final TextView keyKorKhor = keyboardView.findViewById(R.id.keyKorKhor);
        View keySpace = keyboardView.findViewById(R.id.keySpace);
        View keyBackspace = keyboardView.findViewById(R.id.backspace);
        View  keyReturn = keyboardView.findViewById(R.id.returnKey);
//        View keyEmoji = keyboardView.findViewById(R.id.emoji);
        final View setting = keyboardView.findViewById(R.id.setting);


        //get all view from layout
        ArrayList<View> allView = (ArrayList<View>) getAllChildren(charSets);




        final ArrayList<TextView> allTextView = new ArrayList<>(); // store only the TextView (the characters)
        final ArrayList<View> allFrameLayout = new ArrayList<>(); //store key of the keybaord

        for (int i = 0; i < allView.size(); i++) //get TextView from the layout {total 85 need only 82}
        {
            if (allView.get(i) instanceof TextView)
            allTextView.add((TextView)allView.get(i));

        }

        System.out.println(allTextView.size());
        System.out.println(charAll.length);


        for (int i = 0; i < allView.size(); i++) //get key from the layout
        {
            if (i % 2 != 0)
            {
                allFrameLayout.add((View) allView.get(i).getParent());
            }
        }

        System.out.println(allFrameLayout.size());


        //default layout
        for (int i = 0; i<92; i++)
        {
            allTextView.get(i).setText(charAll[i]);
        }


        int k = 1;

        //get text from the key to the text area
        for (int i = 0; i < allFrameLayout.size(); i++)
        {

            final int j = k;
            allFrameLayout.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //on click listener
                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText((CharSequence)allTextView.get(j).getText(), 1);
                    inputString.append((CharSequence)allTextView.get(j).getText());
                }
            });
            k += 2;
            allFrameLayout.get(i).setOnTouchListener(new OnSwipeTouchListener(){
                public boolean onSwipeTop() { // swipeUp listener
                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText((CharSequence)allTextView.get(j-1).getText(), 1);
                    inputString.append((CharSequence)allTextView.get(j).getText());

                    return true;
                }
            });
        }



        //change layout to the second layout
//        key123.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i< allTextView.size(); i++)
//                {
//                    allTextView.get(i).setText(secondLayoutChars[i]);
//                }
//                key123.setVisibility(View.GONE);
//                keyKorKhor.setVisibility(View.VISIBLE);
//                keyFullStop.setVisibility(View.GONE);
//                keyQuestionMark.setVisibility(View.VISIBLE);
//                keyLanguage.setVisibility(View.VISIBLE);
//                prviousEmoji.setVisibility(View.GONE);
//                nextEmoji.setVisibility(View.GONE);
//            }
//        });



        //change layout to the first layout
//        keyKorKhor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i<allTextView.size(); i++)
//                {
//                    allTextView.get(i).setText(firstLayoutChars[i]);
//                }
//                keyKorKhor.setVisibility(View.GONE);
//                key123.setVisibility(View.VISIBLE);
//                keyQuestionMark.setVisibility(View.GONE);
//                keyFullStop.setVisibility(View.VISIBLE);
//                keyLanguage.setVisibility(View.VISIBLE);
//                prviousEmoji.setVisibility(View.GONE);
//                nextEmoji.setVisibility(View.GONE);
//
//            }
//        });

        //submit key event (Enter | Return | Done)
        keyReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                inputString.delete(0, inputString.length()-1);
            }
        });

        keyBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection ic = getCurrentInputConnection();
                ic.deleteSurroundingText(1,0);
                int curPos = ic.getTextBeforeCursor(300,0).length();
                inputString.deleteCharAt(curPos);
//                inputString.deleteCharAt(inputString.length()-1);
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
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
            }
        });





        //change to default emoji layout
//        keyEmoji.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                keyLanguage.setVisibility(View.GONE);
//                prviousEmoji.setVisibility(View.VISIBLE);
//                nextEmoji.setVisibility(View.VISIBLE);
//                for (int i = 0; i<allTextView.size(); i++)
//                {
//                    allTextView.get(i).setText(emojis[emoLayoutNum][i]);
//                }
//
//            }
//        });




        return keyboardView;
    }
}
