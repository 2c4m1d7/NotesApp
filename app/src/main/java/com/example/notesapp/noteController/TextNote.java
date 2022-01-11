package com.example.notesapp.noteController;

public class TextNote extends Note {


    private String text;

    public TextNote(int id, String text, String date) {
        super(id, date);
        this.text = text;
    }

    public TextNote(TextNote textNote) {
        super(textNote);
        text = textNote.text;
    }

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public String getShortText() {
        StringBuilder text = new StringBuilder();

        int d = 0;

        while (d < this.text.length()) {

            if (text.length() == 10 || "\n".equals(Character.toString(this.text.charAt(d)))) {
                text.append("...");
                break;
            }
            text.append(this.text.charAt(d));
            d++;
        }
        return text.toString();
    }


}
