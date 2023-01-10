package com.example.b3tempoLanzafame;

import com.google.gson.annotations.SerializedName;

public enum TempoColor {

    @SerializedName("TEMPO_ROUGE")
    RED(R.color.tempo_red_day_bg, R.string.color_red),

    @SerializedName("TEMPO_BLANC")
    WHITE(R.color.tempo_white_day_bg, R.string.color_white),

    @SerializedName("TEMPO_BLEU")
    BLUE(R.color.tempo_blue_day_bg, R.string.color_blue),

    @SerializedName("NON_DEFINI")
    UNKNOWN(R.color.tempo_undecided_day_bg, R.string.color_unknown);

    private int colorResId;
    private int stringResId;

    // Ctor
    TempoColor(int colorResId, int stringResId) {
        this.colorResId = colorResId;
        this.stringResId = stringResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public int getStringResId() {
        return stringResId;
    }
}
