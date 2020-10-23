package com.example.khmer_keyboard;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class KhmerKeyboard extends InputMethodService {

    StringBuffer inputString;
    ViewGroup suggestionRow;
    boolean isAutoComplete = true;
    InputConnection ic;
    int curPos;



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
            "ាំ","ា","ៃ","ស","ឌ","ដ","ធ","ថ","អ","ង","ះ","ហ","ញ","្","គ","ក","ឡ","ល","ោះ","ើ","៉","់","ឭ","ឮ",
            "ឍ","ឋ","ឃ","ខ","ជ","ច","េះ","វ","ព","ប","ណ","ន","ំ","ម","ុះ","ុំ","។","៕","?","៊"};



    //display text into suggestion row
    private void setSuggestionText(StringBuffer inputString1, ArrayList<TextView> sugTextView){
        if (inputString1.length() == 0){
            suggestionRow.setVisibility(View.INVISIBLE);
        }
        else {
            suggestionRow.setVisibility(View.VISIBLE);
            List<String> suggest = query(inputString1);
            for (int a = 0; a < suggest.size(); a++){
                sugTextView.get(a).setText(suggest.get(a));
            }
        }

    }


    //query top 3 suggestion word from database
    private List<String> query (StringBuffer word){
        DatabaseAccess dbAccess = DatabaseAccess.getInstance(getApplicationContext());
        dbAccess.open();
        List<String> suggestion = dbAccess.getSuggestion(word, isAutoComplete);
        dbAccess.close();
        return suggestion;
    }

    //get all the children inside the viewgroup (last children of the tree)
    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }


    //update priority
    void updatePriority(String word){
        DatabaseAccess dbAccess = DatabaseAccess.getInstance(getApplicationContext());
        dbAccess.open();
        dbAccess.updatePrio(word);
        dbAccess.close();
    }

    @Override
    public View onCreateInputView() {

        inputString = new StringBuffer();

        Log.d("PIUKeyboard", "Keyboard started");

        ic = getCurrentInputConnection();
        ViewGroup keyboardView = (ViewGroup)getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        ViewGroup charSets = keyboardView.findViewById(R.id.char_sets);
        suggestionRow = keyboardView.findViewById(R.id.suggestions);
        View keySpace = keyboardView.findViewById(R.id.keySpace);
        View keyBackspace = keyboardView.findViewById(R.id.backspace);
        View  keyReturn = keyboardView.findViewById(R.id.returnKey);
        final View setting = keyboardView.findViewById(R.id.setting);

        //get all view from layout exclude suggestion row
        ArrayList<View> allView = (ArrayList<View>) getAllChildren(charSets);

        //suggestion row

        //store suggestion textView
        final ArrayList<TextView> sugTextView = new ArrayList<>();

        //store suggestion key
        final ArrayList<View> suggestionKey = new ArrayList<>();

        //get view from suggestion row
        final ArrayList<View> suggestionsView = (ArrayList<View>) getAllChildren(suggestionRow);

        //get only TextView of the suggestion row
        for (int i = 0; i < suggestionsView.size(); i++)
        {
            if (suggestionsView.get(i) instanceof TextView)
                sugTextView.add((TextView)suggestionsView.get(i));

        }

        //get key of each suggestion
        for (int i = 0; i<suggestionsView.size(); i++){
            suggestionKey.add((View) suggestionsView.get(i).getParent());
        }

        final ArrayList<TextView> allTextView = new ArrayList<>(); // store only the TextView (the characters)
        final ArrayList<View> allFrameLayout = new ArrayList<>(); //store key of the keyboard

        for (int i = 0; i < allView.size(); i++) //get TextView from the layout {total 85 need only 82}
        {
            if (allView.get(i) instanceof TextView)
            allTextView.add((TextView)allView.get(i));

        }

        for (int i = 0; i < allView.size(); i++) //get key from the layout
        {
            if (i % 2 != 0)
            {
                allFrameLayout.add((View) allView.get(i).getParent());
            }
        }


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
                    isAutoComplete = true;
                    ic.commitText(allTextView.get(j).getText(), 1);
                    inputString.append(allTextView.get(j).getText());
                    setSuggestionText(inputString, sugTextView);
                }
            });
            k += 2;
            allFrameLayout.get(i).setOnTouchListener(new OnSwipeTouchListener(){
                public boolean onSwipeTop() { // swipeUp listener
                    isAutoComplete = true;
                    ic.commitText(allTextView.get(j-1).getText(), 1);
                    inputString.append(allTextView.get(j).getText());
                    setSuggestionText(inputString, sugTextView);
                    return true;
                }
            });
        }

        //submit key event (Enter | Return | Done)
        keyReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                inputString.delete(0, inputString.length()-1);
            }
        });

        keyBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAutoComplete = true;
                ic.deleteSurroundingText(1,0);
                curPos = ic.getTextBeforeCursor(300,0).length();
                Log.d("PIUKeyboard", "CurrentPosition: "+curPos);

                if (inputString.length() > 0){
                    if (curPos>inputString.length()){
                        curPos = inputString.length()-1;
                    }
                    inputString.deleteCharAt(curPos);
//                    Log.d("PIUKeyboard", "inputString Value: " + inputString);

                }
                setSuggestionText(inputString, sugTextView);
                Log.d("PIUKeyboard", "inputString Value: " + inputString);

            }
        });

        keySpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence textBeforeCursor = ic.getTextBeforeCursor(1, 0);
                Log.d("PIUKeyboard", "text before cursor: "+textBeforeCursor);
                if (textBeforeCursor.toString().equals(" ")){
//                    Log.d("PIUKeyboard", "input String value:"+inputString);

                    inputString = new StringBuffer();
                    Log.d("PIUKeyboard", "input String has been renewed");
                }
                else {
                    ic.commitText(" ", 1);
                    inputString.append(" ");
                }
                Log.d("PIUKeyboard", "input String value:"+inputString);

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

        int l=0;

        //select Text from Suggestion row into Text area
        for (int i = 0; i < suggestionsView.size(); i++){
            final int b = l;
            suggestionKey.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //auto complete
                    if (isAutoComplete){
                        ic.deleteSurroundingText(inputString.length(),0);
                        inputString = new StringBuffer(sugTextView.get(b).getText());
                        ic.commitText(inputString, 1);
                        Log.d("PIUKeyboard", "inputString value:" +inputString.length()+" ");
                        updatePriority((String) sugTextView.get(b).getText());
                        isAutoComplete = false;
                    }
                    //next word
                    else{
                        ic.commitText(sugTextView.get(b).getText(), 1);
                        inputString.append(sugTextView.get(b).getText());
                        Log.d("PIUKeyboard", "inputString value:" +inputString.length()+" ");
                    }
                    setSuggestionText( new StringBuffer(sugTextView.get(b).getText().toString()) , sugTextView);
                    updatePriority((String) sugTextView.get(b).getText());
                }
            });
            l++;
        }

        return keyboardView;
    }
}
